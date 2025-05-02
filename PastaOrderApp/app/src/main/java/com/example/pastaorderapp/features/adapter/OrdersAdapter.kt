package com.example.pastaorderapp.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.databinding.OrderItemBinding
import com.example.pastaorderapp.features.model.OrderModel

class OrdersAdapter(
    private val itemList: List<OrderModel>,
    private val onItemClick: (OrderModel, Boolean) -> Unit,
    private val onItemCountChange: (OrderModel, Boolean) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val orderItem = itemList[position]
        holder.bind(orderItem)
    }

    override fun getItemCount(): Int = itemList.size

    inner class GridViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var count = 0

        fun bind(item: OrderModel) {
            binding.isTrend.isVisible = item.isTrend
            binding.fire.isVisible = item.isFire
            binding.fire.visibility = if (item.isFire) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            binding.itemImg.setImageResource(item.img)
            binding.title.text = item.name
            binding.price.text = item.price
            binding.count.text = count.toString()
            binding.plus.setOnClickListener {
                count += 1
                binding.count.text = count.toString()
                if (count > 0) {
                    binding.minus.isEnabled = true
                    binding.container.isSelected = true
                    binding.minus.visibility = View.VISIBLE
                }
                onItemCountChange(item, true) // ✅ დაემატოს
            }

            binding.minus.setOnClickListener {
                if (count > 0) {
                    count -= 1
                    binding.count.text = count.toString()
                    if (count == 0) {
                        binding.minus.isEnabled = false
                        binding.minus.visibility = View.INVISIBLE
                        binding.container.isSelected = false
                    }
                    onItemCountChange(item, false) // ❌ წაიშალოს
                }
            }

            binding.root.setOnClickListener {
                onItemClick(item,false)
            }
        }
    }
}