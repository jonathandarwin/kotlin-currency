package com.jonathandarwin.currency.feature.splash.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.databinding.SplashFragmentBinding
import com.jonathandarwin.currency.feature.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, SplashFragmentBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(SPLASH_DELAY)

            val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            navigate(direction)
        }

    }

    override fun getVM(): SplashViewModel = viewModel

    override fun getLayout(): Int = R.layout.splash_fragment

    companion object {
        private const val SPLASH_DELAY = 1000L
    }
}