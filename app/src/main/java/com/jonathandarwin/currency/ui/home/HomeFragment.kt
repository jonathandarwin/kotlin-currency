package com.jonathandarwin.currency.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.currency.base.dialog.ListBottomSheetDialog
import com.jonathandarwin.currency.databinding.HomeFragmentBinding
import com.jonathandarwin.currency.ui.history.HistoryAdapter
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

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        viewModel.getCurrency()
        viewModel.getPreviewHistory()
    }

    override fun initListener() {
        super.initListener()
        binding.tvFrom.setOnClickListener(this)
        binding.tvTo.setOnClickListener(this)
        binding.btnConvert.setOnClickListener(this)
        binding.tvSeeAll.setOnClickListener(this)
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.state.observe(this, Observer {
            when(it) {
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
            when(it) {
                binding.tvFrom -> {
                    ListBottomSheetDialog()
                        .setContext(requireContext())
                        .setTitle("Currencies")
                        .setData(viewModel.currencies)
                        .setSelectedValue(viewModel.from)
                        .setOnClickListener {
                                item ->
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
                        .setOnClickListener {
                            item ->
                                viewModel.to = item.value ?: ""
                                binding.tvTo.text = item.value
                        }
                        .build()
                }
                binding.btnConvert -> {
                    val isLoading = viewModel.loading.value ?: false
                    if(!isLoading) {
                        if(!binding.etAmount.text.isNullOrEmpty()) {
                            hideSoftKeyboard(requireActivity())
                            binding.rateGroup.visibility = View.INVISIBLE
                            viewModel.convert(binding.etAmount.text.toString())
                        }
                        else {
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