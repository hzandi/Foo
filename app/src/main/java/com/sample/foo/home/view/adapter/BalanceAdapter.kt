package com.sample.foo.home.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.data.entity.BalanceEntity
import com.sample.foo.R
import java.text.NumberFormat
import java.util.*

class BalanceAdapter constructor(
    private val balances: List<BalanceEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BalanceHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.balance_item_layut, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val balanceEntity = balances[position]
        val balanceHolder = holder as BalanceHolder

        balanceHolder.symbolTextView.text = balanceEntity.symbol
        balanceEntity.value?.let { number ->
            balanceHolder.valueTextView.text =
                NumberFormat.getCurrencyInstance(
                    Locale("en", "US")
                ).format(number).replace("$", "")
        } ?: run {
            balanceHolder.valueTextView.text = holder.itemView.context.getString(R.string.zero_currency)
        }

    }

    override fun getItemCount(): Int {
        return balances.size
    }

    private class BalanceHolder(
        private val itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val valueTextView: TextView = itemView.findViewById(R.id.valueTextView)
        val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
    }
}

