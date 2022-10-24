package com.jonathandarwin.currency.base.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jonathandarwin.currency.R
import com.jonathandarwin.currency.databinding.ListBottomSheetBinding

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class ListBottomSheetDialog {
    private var listener: ((ListBottomSheet) -> Unit)? = null
    private lateinit var listAdapter: ListBottomSheetAdapter
    private val list = ArrayList<ListBottomSheet>()
    private var context: Context? = null
    private var selectedValue = ""
    private var title = ""

    fun setTitle(title: String): ListBottomSheetDialog {
        this.title = title
        return this
    }

    fun setContext(context: Context): ListBottomSheetDialog {
        this.context = context
        return this
    }

    fun setData(list: List<ListBottomSheet>): ListBottomSheetDialog {
        this.list.clear()
        this.list.addAll(list)
        return this
    }

    fun setOnClickListener(listener: (ListBottomSheet) -> Unit): ListBottomSheetDialog {
        this.listener = listener
        return this
    }

    fun setSelectedValue(selectedValue: String): ListBottomSheetDialog {
        this.selectedValue = selectedValue
        return this
    }

    fun build() {
        context?.let {
            listAdapter = ListBottomSheetAdapter()

            val dialog  = BottomSheetDialog(it, R.style.BottomSheetDialogTheme)
            val binding = ListBottomSheetBinding.inflate(LayoutInflater.from(it), null, false)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = listAdapter
            }
            binding.tvTitle.text = title

            dialog.setContentView(binding.root)
            dialog.show()

            listAdapter.apply {
                updateData(list)
                setOnClickListener { item ->
                    listener?.invoke(item)
                    dialog.dismiss()
                }
                setSelectedValue(selectedValue)
            }
        }
    }
}