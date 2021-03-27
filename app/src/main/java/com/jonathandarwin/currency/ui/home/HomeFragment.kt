package com.jonathandarwin.currency.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initListener() {
        super.initListener()

        viewModel.state.observe(this, Observer {
            when(it) {
                HomeViewModelState.SUCCES_GET_CURRENCY -> {

                }
            }
        })
    }

    override fun getVM(): HomeViewModel = viewModel
    override fun getLayout(): Int = R.layout.home_fragment
}