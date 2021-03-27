package com.jonathandarwin.currency.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.base.dialog.ListBottomSheetDialog
import com.jonathandarwin.currency.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(), View.OnClickListener {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.tvFrom.text = viewModel.from
        binding.tvTo.text = viewModel.to

        viewModel.getCurrency()
    }

    override fun initListener() {
        super.initListener()
        binding.tvFrom.setOnClickListener(this)
        binding.tvTo.setOnClickListener(this)
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.state.observe(this, Observer {
            when(it) {
                HomeViewModelState.SUCCES_GET_CURRENCY -> {

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
            }
        }
    }

    override fun getVM(): HomeViewModel = viewModel
    override fun getLayout(): Int = R.layout.home_fragment
}