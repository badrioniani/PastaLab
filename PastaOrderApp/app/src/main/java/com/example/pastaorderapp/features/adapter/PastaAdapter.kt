package com.example.pastaorderapp.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.databinding.OrderItemBinding
import com.example.pastaorderapp.features.model.PastaType

class PastaAdapter(
    private val items: List<PastaType>,
    private val singleSelect: Boolean = false,
    private val onClick: (PastaType) -> Unit,
    private val isSelected: (PastaType) -> Boolean,
    private val setSelected: (PastaType, Boolean) -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<PastaAdapter.PastaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastaViewHolder {
        val view = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class PastaViewHolder(val view: OrderItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: PastaType) {
            view.apply {
                title.text = item.name
                itemImg.setImageResource(item.img)
                isTrend.isVisible = false
                fire.isVisible = false
                plus.isVisible = false
                count.isVisible = false
            }

            if (isSelected(item))
                view.container.isSelected = true
            else
                view.container.isSelected = false
            itemView.setOnClickListener {
                if (singleSelect) {
                    items.forEach { setSelected(it, false) }
                    setSelected(item, true)
                } else {
                    setSelected(item, !isSelected(item))
                }
                notifyDataSetChanged()
                onSelectionChanged()
                onClick(item)
            }
        }
    }
}
