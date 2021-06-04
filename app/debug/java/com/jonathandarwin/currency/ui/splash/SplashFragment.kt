package com.jonathandarwin.currency.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel, SplashFragmentBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)

            val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            navigate(direction)
        }
    }

    override fun getVM(): SplashViewModel = viewModel

    override fun getLayout(): Int = R.layout.splash_fragment
}