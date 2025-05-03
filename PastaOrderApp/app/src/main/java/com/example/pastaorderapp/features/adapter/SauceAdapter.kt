package com.example.pastaorderapp.features.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.databinding.OrderItemBinding
import com.example.pastaorderapp.features.model.Sauce

class SauceAdapter(
    private var items: List<Sauce>,
    private val singleSelect: Boolean = false,
    private val onClick: (Sauce) -> Unit,
    private val isSelected: (Sauce) -> Boolean,
    private val setSelected: (Sauce, Boolean) -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<SauceAdapter.SauceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SauceViewHolder {
        val view = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SauceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SauceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Sauce>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class SauceViewHolder(val view: OrderItemBinding) : RecyclerView.ViewHolder(view.root) {
        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bind(item: Sauce) {
            view.apply {
                title.text = item.name
                itemImg.setImageResource(item.img)
                isTrend.isVisible = false
                val formatted = String.format("%.2f", item.price)
                price.text = "$formatted ₾"
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
