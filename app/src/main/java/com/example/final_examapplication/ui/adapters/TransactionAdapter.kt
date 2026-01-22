package com.example.final_examapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_examapplication.data.local.entities.Transaction
import com.example.final_examapplication.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(
    private val onDeleteClick: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
    }

    inner class VH(val b: ItemTransactionBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = getItem(position)

        holder.b.textFromCurrency.text = t.fromCurrency
        holder.b.textToCurrency.text = t.toCurrency
        holder.b.textRate.text = "Rate: %.4f".format(t.rate)

        val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.b.textDate.text = df.format(Date(t.timestamp))

        holder.b.btnDelete.setOnClickListener { onDeleteClick(t) }
    }
}
