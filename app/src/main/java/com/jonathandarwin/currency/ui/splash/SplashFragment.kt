package com.jonathandarwin.currency.ui.splash

import androidx.fragment.app.viewModels
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, SplashFragmentBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun getVM(): SplashViewModel = viewModel

    override fun getLayout(): Int = R.layout.splash_fragment
}