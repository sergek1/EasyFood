package com.example.easyfood.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.MealItemBinding
import com.example.easyfood.pojo.Meal
import com.example.easyfood.pojo.MealsByCategory
import kotlinx.android.synthetic.main.meal_item.view.*

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    inner class CategoryMealsViewModel(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.meal_item,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.itemView.img_meal)
        holder.itemView.tv_meal_name.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }
}