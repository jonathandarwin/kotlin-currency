package com.jonathandarwin.currency.ui.history

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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
                HistoryViewModelState.DELETE_ALL_HISTORY -> {
                    finish()
                }
            }
        })
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