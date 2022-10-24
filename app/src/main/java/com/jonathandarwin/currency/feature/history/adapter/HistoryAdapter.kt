package com.jonathandarwin.currency.feature.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jonathandarwin.currency.base.BaseAdapter
import com.jonathandarwin.currency.databinding.ListConvertCurrencyHistoryItemBinding
import com.jonathandarwin.currency.util.DateTimeUtil
import com.jonathandarwin.currency.util.DateTimeUtil.ddMMMyyyyHHmm
import com.jonathandarwin.domain.model.ConvertCurrency

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class HistoryAdapter: BaseAdapter<ConvertCurrency, ListConvertCurrencyHistoryItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(ListConvertCurrencyHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val binding = holder.getBinding()
        binding?.let {
            val item = data[position]
            it.data = item
            it.displayDateTime = DateTimeUtil.convertToDate(item.datetime).ddMMMyyyyHHmm()
        }
    }
}