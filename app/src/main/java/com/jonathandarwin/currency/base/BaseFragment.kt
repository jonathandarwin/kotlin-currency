package com.jonathandarwin.currency.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.jonathandarwin.currency.databinding.LoadingBinding

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
abstract class BaseFragment<VM : BaseViewModel, Binding : ViewDataBinding> : Fragment() {

    private lateinit var vm : VM
    protected lateinit var binding : Binding
    private lateinit var root : ViewGroup
    private lateinit var loading : LoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.lifecycleOwner = this
        vm = getVM()

        root = binding.root as ViewGroup
        loading = LoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    protected open fun initListener() {
        vm.loading.observe(viewLifecycleOwner, Observer {
            removeAllView()
            if(it) root.addView(loading.root)
        })
    }

    private fun removeAllView() {
        root.removeView(loading.root)
    }

    protected fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    protected fun navigate(id: Int) {
        findNavController().navigate(id)
    }

    protected fun finish() {
        findNavController().popBackStack()
    }

    abstract fun getVM() : VM
    abstract fun getLayout() : Int
}