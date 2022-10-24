package com.jonathandarwin.currency.feature.home.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.base.dialog.ListBottomSheetDialog
import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.currency.databinding.HomeFragmentBinding
import com.jonathandarwin.currency.feature.history.adapter.HistoryAdapter
import com.jonathandarwin.currency.feature.history.fragment.HistoryFragment
import com.jonathandarwin.currency.feature.home.model.ConversionResultUiModel
import com.jonathandarwin.currency.feature.home.viewmodel.HomeViewModel
import com.jonathandarwin.currency.feature.home.model.action.HomeUiAction
import com.jonathandarwin.currency.util.ThemeUtil
import com.jonathandarwin.domain.model.ConvertCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        setCurrentTheme()

        setFragmentResultListener(HistoryFragment.HISTORY_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            val isHistoryAllDeleted = bundle[HistoryFragment.EXTRA_HISTORY_ALL_DELETED] as Boolean
            if(isHistoryAllDeleted) viewModel.submitAction(HomeUiAction.LoadHistory)
        }

//        observeArgument<Boolean>(HistoryFragment.HISTORY_ALL_DELETED) { isAllDeleted ->
//            if(isAllDeleted) viewModel.submitAction(HomeUiAction.LoadHistory)
//        }
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                renderForm(it.from, it.to)
                renderConversionResult(it.conversionResult)
                renderHistory(it.histories)
            }
        }
    }

    private fun renderForm(from: String, to: String) {
        binding.tvFrom.text = from
        binding.tvTo.text = to
    }

    private fun renderConversionResult(conversionResult: ConversionResultUiModel) {
        when(val state = conversionResult.state) {
            is NetworkResult.Loading -> {
                binding.loading.visibility = View.VISIBLE
            }
            is NetworkResult.Success -> {
                binding.loading.visibility = View.GONE

                binding.etResult.setText(state.data.convertResult)
                binding.tvRate.text = state.data.rate

                binding.rateGroup.visibility = View.VISIBLE
            }
            is NetworkResult.Error -> {
                binding.loading.visibility = View.GONE
                showErrorDialog(state.throwable.localizedMessage)
            }
        }
    }

    private fun renderHistory(histories: List<ConvertCurrency>) {
        historyAdapter.updateData(histories)
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
                            viewModel.submitAction(HomeUiAction.SetFrom(item.value ?: ""))
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
                            viewModel.submitAction(HomeUiAction.SetTo(item.value ?: ""))
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

                            viewModel.submitAction(HomeUiAction.Convert(binding.etAmount.text.toString()))
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