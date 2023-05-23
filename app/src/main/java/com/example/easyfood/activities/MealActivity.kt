package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewModel
import com.example.easyfood.viewModel.MealViewModelFactory
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMealBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_meal)

//        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]


        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
        onFavoritClick()
    }

    private fun onFavoritClick() {
        btn_add_to_favorites.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "MEAL SAVED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        img_youtube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal?=null
    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this
        ) { meal ->
            onResponseCase()
            mealToSave = meal
            tv_category.text = "Category : ${meal!!.strCategory}"
            tv_area.text = "Area : ${meal.strArea}"
            tv_instructions_steps.text = meal.strInstructions
            youtubeLink = meal.strYoutube.toString()
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(img_meal_detail)
        collapsing_toolbar.title = mealName
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        progress_bar.visibility = View.VISIBLE
        btn_add_to_favorites.visibility = View.INVISIBLE
        tv_instructions.visibility = View.INVISIBLE
        tv_category.visibility = View.INVISIBLE
        tv_area.visibility = View.INVISIBLE
        img_youtube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        progress_bar.visibility = View.INVISIBLE
        btn_add_to_favorites.visibility = View.VISIBLE
        tv_instructions.visibility = View.VISIBLE
        tv_category.visibility = View.VISIBLE
        tv_area.visibility = View.VISIBLE
        img_youtube.visibility = View.VISIBLE
    }
}