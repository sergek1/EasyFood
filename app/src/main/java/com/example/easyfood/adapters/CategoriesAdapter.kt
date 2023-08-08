package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.pojo.Category
import com.example.easyfood.pojo.CategoryList
import kotlinx.android.synthetic.main.category_item.view.*

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit) ?= null

    fun setCategoryList(categoriesList: List<Category>) {
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.itemView.img_category)
        holder.itemView.tv_category_name.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}