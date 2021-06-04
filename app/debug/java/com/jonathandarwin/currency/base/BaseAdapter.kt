package com.jonathandarwin.currency.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created By : Jonathan Darwin on June 01, 2021
 */
abstract class BaseAdapter<Data, VH: ViewDataBinding> : RecyclerView.Adapter<BaseAdapter<Data, VH>.BaseViewHolder>() {

    protected var listener: ((Data) -> Unit)? = null
    protected var data = arrayListOf<Data>()

    override fun getItemCount(): Int = data.size

    fun updateData(newList: List<Data>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (Data) -> Unit) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.getBinding()?.let {
            it.root.setOnClickListener { listener?.invoke(data[position]) }
        }
    }

    inner class BaseViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun getBinding(): VH? {
            return DataBindingUtil.getBinding(v)
        }
    }
}