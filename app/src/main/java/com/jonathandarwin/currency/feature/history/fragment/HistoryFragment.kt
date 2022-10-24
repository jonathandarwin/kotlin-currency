package com.jonathandarwin.currency.feature.history.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.base.BaseFragment
import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.currency.databinding.HistoryFragmentBinding
import com.jonathandarwin.currency.feature.history.adapter.HistoryAdapter
import com.jonathandarwin.currency.feature.history.model.HistoryUiEvent
import com.jonathandarwin.currency.feature.history.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryViewModel, HistoryFragmentBinding>(), View.OnClickListener {

    private val viewModel: HistoryViewModel by viewModels()
    private val historyAdapter: HistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }
    }

    override fun initListener() {
        super.initListener()
        binding.icBack.setOnClickListener(this)
        binding.tvDeleteAll.setOnClickListener(this)
    }

    override fun initObserver() {
        super.initObserver()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.histories.collectLatest {
                when(val result = it.data) {
                    NetworkResult.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success -> {
                        binding.loading.visibility = View.GONE
                        historyAdapter.updateData(result.data)
                    }
                    is NetworkResult.Error -> {
                        binding.loading.visibility = View.GONE
                        showErrorDialog(result.throwable.localizedMessage)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                when(it) {
                    HistoryUiEvent.HistoryDeleted -> finish()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it) {
                binding.icBack -> finish()
                binding.tvDeleteAll -> {
                    val confirmation = AlertDialog.Builder(requireContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure want to delete all your history?")
                        .setPositiveButton("Yes") {
                            _, _ -> viewModel.deleteAllHistory()
                        }
                        .setNegativeButton("No") {
                            dialog, _ -> dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    override fun getVM(): HistoryViewModel = viewModel

    override fun getLayout(): Int = R.layout.history_fragment
}