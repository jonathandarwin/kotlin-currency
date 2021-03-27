package com.jonathandarwin.currency.base.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jonathandarwin.currency.databinding.ListBottomSheetBinding

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class ListBottomSheetDialog {
    private var listener: ((ListBottomSheet) -> Unit)? = null
    private val listAdapter = ListBottomSheetAdapter()
    private val list = ArrayList<ListBottomSheet>()
    private var context: Context? = null

    fun setContext(context: Context): ListBottomSheetDialog {
        this.context = context
        return this
    }

    fun setData(list: ArrayList<ListBottomSheet>): ListBottomSheetDialog {
        this.list.clear()
        this.list.addAll(list)
        return this
    }

    fun setOnClickListener(listener: (ListBottomSheet) -> Unit): ListBottomSheetDialog {
        this.listener = listener
        return this
    }

    fun build() {
        context?.let {
            val binding = ListBottomSheetBinding.inflate(LayoutInflater.from(it), null, false)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = listAdapter
            }

            listAdapter.apply {
                updateData(list)
                setOnClickListener { item -> listener?.invoke(item) }
            }

            val dialog  = BottomSheetDialog(it)
            dialog.setContentView(binding.root)
            dialog.show()
        }
    }
}