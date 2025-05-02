package com.example.pastaorderapp.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.databinding.OrderItemBinding
import com.example.pastaorderapp.features.model.Ingredient
import com.example.pastaorderapp.features.model.Sauce

class IngredientAdapter(
    private var items: List<Ingredient>,
    private val singleSelect: Boolean = false,
    private val onClick: (Ingredient) -> Unit,
    private val isSelected: (Ingredient) -> Boolean,
    private val setSelected: (Ingredient, Boolean) -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private val selectedIngredients = mutableSetOf<Ingredient>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Ingredient>) {
        items = newItems
        selectedIngredients.clear()
        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(val view: OrderItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Ingredient) {
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
