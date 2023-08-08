package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.pojo.MealsByCategory
import kotlinx.android.synthetic.main.popular_items.view.*

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory)->Unit)
    private var mealsList = ArrayList<MealsByCategory>()
    var onLongItemClick: ((MealsByCategory)->Unit)?=null
    fun setMeals(mealsList: ArrayList<MealsByCategory>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    inner class PopularMealViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.popular_items,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.itemView.img_popular_meal_item)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }
}