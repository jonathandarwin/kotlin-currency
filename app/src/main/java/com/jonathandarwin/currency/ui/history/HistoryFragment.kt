package com.jonathandarwin.currency.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.databinding.HistoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryViewModel, HistoryFragmentBinding>(), View.OnClickListener {

    private val viewModel: HistoryViewModel by viewModels()
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }
        viewModel.getHistory()
    }

    override fun initListener() {
        super.initListener()
        binding.icBack.setOnClickListener(this)
        binding.tvDeleteAll.setOnClickListener(this)
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.state.observe(this, Observer {
            when(it) {
                HistoryViewModelState.GET_HISTORY -> {
                    historyAdapter.updateData(viewModel.historyList)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it) {
                binding.icBack -> finish()
                binding.tvDeleteAll -> {

                }
            }
        }
    }

    override fun getVM(): HistoryViewModel = viewModel
    override fun getLayout(): Int = R.layout.history_fragment
}