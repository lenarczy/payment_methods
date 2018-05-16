package com.mobica.paymentsmethod.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobica.paymentsmethod.R

class PaymentMethodsAdapter(private val methods: List<PaymentItem>, private val itemClick: (PaymentItem) -> Unit) : RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.method_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = methods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(methods[position])
    }

    class ViewHolder(private val containerView: View, private val itemClick: (PaymentItem) -> Unit): RecyclerView.ViewHolder(containerView) {

        fun bind(item: PaymentItem) {
            val titleTw = containerView.findViewById<TextView>(R.id.titleTw)
            titleTw.text = item.title
            val subTitle = containerView.findViewById<TextView>(R.id.subtitleTw)
            subTitle.text = item.subtitle
            containerView.setOnClickListener{ itemClick(item) }
        }
    }
}