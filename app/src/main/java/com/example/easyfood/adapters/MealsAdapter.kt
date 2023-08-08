package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.pojo.Meal
import kotlinx.android.synthetic.main.meal_item.view.*

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesMealsAdapterViewHolder>() {

    lateinit var onItemClick:((Meal)->Unit)
    inner class FavoritesMealsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem ==newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        return FavoritesMealsAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.meal_item,
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.itemView.img_meal)
        holder.itemView.tv_meal_name.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }
    }
}