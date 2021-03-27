package com.jonathandarwin.currency.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonathandarwin.currency.databinding.ListConvertCurrencyHistoryItemBinding
import com.jonathandarwin.currency.util.DateTimeUtil
import com.jonathandarwin.currency.util.DateTimeUtil.ddMMMyyyyHHmm
import com.jonathandarwin.domain.model.ConvertCurrency

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val list = arrayListOf<ConvertCurrency>()

    inner class HistoryViewHolder(private val binding: ListConvertCurrencyHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConvertCurrency) {
            binding.data = item
            binding.displayDateTime = DateTimeUtil.convertToDate(item.datetime).ddMMMyyyyHHmm()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(ListConvertCurrencyHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateData(newList: List<ConvertCurrency>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}