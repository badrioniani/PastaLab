package com.example.pastaorderapp.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.databinding.OrderItemBinding

class SelectAdapter<T>(
    private val items: List<T>,
    private val singleSelect: Boolean = false,
    private val getName: (T) -> String,
    private val getImg: (T) -> Int,
    private val isSelected: (T) -> Boolean,
    private val setSelected: (T, Boolean) -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<SelectAdapter<T>.ViewHolder>() {

    inner class ViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.isTrend.isVisible = false
            binding.count.isVisible = false
            binding.plus.visibility = View.INVISIBLE
            binding.plus.isEnabled = false
            binding.title.text = getName(item)
            binding.itemImg.setImageResource(getImg(item))
            if (isSelected(item))
                binding.container.isSelected = true
            else
                binding.container.isSelected = false
            binding.root.setOnClickListener {
                if (singleSelect) {
                    items.forEach { setSelected(it, false) }
                    setSelected(item, true)
                } else {
                    setSelected(item, !isSelected(item))
                }
                notifyDataSetChanged()
                onSelectionChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tv = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(tv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
