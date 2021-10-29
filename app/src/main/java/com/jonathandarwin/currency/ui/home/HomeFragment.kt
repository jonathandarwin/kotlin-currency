package com.jonathandarwin.currency.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.base.dialog.ListBottomSheetDialog
import com.jonathandarwin.currency.databinding.HomeFragmentBinding
import com.jonathandarwin.currency.ui.history.HistoryAdapter
import com.jonathandarwin.currency.util.ThemeUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(), View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.tvFrom.text = viewModel.from
        binding.tvTo.text = viewModel.to

        binding.etAmount.setText(viewModel.convertResult)
        binding.etResult.setText(viewModel.convertResult)
        binding.tvRate.text = viewModel.rate

        binding.rateGroup.visibility = if (viewModel.rate != "") View.VISIBLE else View.INVISIBLE

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        viewModel.getCurrency()
        viewModel.getPreviewHistory()
        setCurrentTheme()
    }

    override fun initListener() {
        super.initListener()
        binding.tvFrom.setOnClickListener(this)
        binding.tvTo.setOnClickListener(this)
        binding.btnConvert.setOnClickListener(this)
        binding.tvSeeAll.setOnClickListener(this)
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            switchTheme(isChecked)
        }
    }

    private fun switchTheme(isDarkTheme: Boolean) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            requireContext().setTheme(R.style.Theme_CurrencyDark)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            requireContext().setTheme(R.style.Theme_Currency)
        }
        ThemeUtil.saveTheme(
            requireContext(),
            if (isDarkTheme) ThemeUtil.DARK_THEME else ThemeUtil.LIGHT_THEME
        )
    }

    private fun setCurrentTheme() {
        binding.switchTheme.isChecked = this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.state.observe(this, Observer {
            when (it) {
                HomeViewModelState.SUCCESS_GET_CURRENCY -> {

                }
                HomeViewModelState.SUCCESS_CONVERT -> {
                    binding.etResult.setText(viewModel.convertResult)
                    binding.tvRate.text = viewModel.rate

                    binding.rateGroup.visibility = View.VISIBLE

                    viewModel.getPreviewHistory()
                }
                HomeViewModelState.GET_HISTORY -> {
                    historyAdapter.updateData(viewModel.history)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it) {
                binding.tvFrom -> {
                    ListBottomSheetDialog()
                        .setContext(requireContext())
                        .setTitle("Currencies")
                        .setData(viewModel.currencies)
                        .setSelectedValue(viewModel.from)
                        .setOnClickListener { item ->
                            viewModel.from = item.value ?: ""
                            binding.tvFrom.text = item.value
                        }
                        .build()
                }
                binding.tvTo -> {
                    ListBottomSheetDialog()
                        .setContext(requireContext())
                        .setTitle("Currencies")
                        .setData(viewModel.currencies)
                        .setSelectedValue(viewModel.to)
                        .setOnClickListener { item ->
                            viewModel.to = item.value ?: ""
                            binding.tvTo.text = item.value
                        }
                        .build()
                }
                binding.btnConvert -> {
                    val isLoading = viewModel.loading.value ?: false
                    if(!isLoading) {
                        if (!binding.etAmount.text.isNullOrEmpty()) {
                            hideSoftKeyboard(requireActivity())
                            binding.rateGroup.visibility = View.INVISIBLE
                            viewModel.convert(binding.etAmount.text.toString())
                        } else {
                            showErrorDialog("You haven't input the amount")
                        }
                    }
                }
                binding.tvSeeAll -> {
                    val direction = HomeFragmentDirections.actionHomeFragmentToHistoryFragment()
                    navigate(direction)
                }
            }
        }
    }

    override fun getVM(): HomeViewModel = viewModel
    override fun getLayout(): Int = R.layout.home_fragment

}