package com.example.pastaorderapp.features.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.data.Modifier
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
    fun getSelectedIngredients(): List<Modifier> {
        return items.filter { it.isSelected }.map {
            Modifier(
                productId = it.orderId,
                amount = 1,
                productGroupId = null,
                price = it.price,
                positionId = null,
            )
            }
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
        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bind(item: Ingredient) {
            view.apply {
                title.text = item.name
                itemImg.setImageResource(item.img)
                val formatted = String.format("%.2f", item.price)
                price.text = "$formatted ₾"
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
                    if(!isSelected(item)){
                        items.forEach { setSelected(it, false) }
                        setSelected(item, true)
                    }
                } else {
                    if(isSelected(item)){
                        setSelected(item, false)
                    }else{
                        setSelected(item, true)
                    }
                }

                notifyDataSetChanged()
                onSelectionChanged()
                onClick(item)
            }
        }
    }
}
