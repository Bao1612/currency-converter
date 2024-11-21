package com.example.currency_converter.model.get_latest_rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_converter.R

class LatestRateAdapter(private val latestRateList: Map<String, String>) : RecyclerView.Adapter<LatestRateAdapter.MyViewHolder>() {

    private val rateEntries = latestRateList.entries.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return latestRateList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (currency, rate) = rateEntries[position]
        holder.currencySymbol.text = currency
        holder.latestRate.text = rate
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val currencySymbol : TextView = itemView.findViewById(R.id.currencySymbol)
        val latestRate : TextView = itemView.findViewById(R.id.latestRate)

    }


}