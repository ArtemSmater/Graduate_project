package com.example.dishescollection.screens.meals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishescollection.R;
import com.example.dishescollection.adapters.CategoriesAdapter;
import com.example.dishescollection.pojo.Category;
import com.example.dishescollection.pojo.Meal;
import com.example.dishescollection.screens.details.MealDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class MealsListActivity extends AppCompatActivity implements MealsListView {

    private CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_meals_list);

        // init views
        RecyclerView recyclerViewDishes = findViewById(R.id.recyclerViewDishes);
        TextView tvTitle = findViewById(R.id.tvDishTitle);

        // set values
        adapter = new CategoriesAdapter();
        recyclerViewDishes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDishes.setAdapter(adapter);
        String title = getString();
        tvTitle.setText(title);
    }

    @Nullable
    private String getString() {
        MealsListPresenter mealsListPresenter = new MealsListPresenter(this);

        // get intent
        Intent getIntent = getIntent();
        int tag = getIntent.getIntExtra("tag", -1);
        String title = "";
        if (tag == 1) {
            title = getIntent.getStringExtra("category");
            mealsListPresenter.loadData(tag, title);
        } else if (tag == 2) {
            title = getIntent.getStringExtra("area");
            mealsListPresenter.loadData(tag, title);
        } else if (tag == 3) {
            title = getIntent.getStringExtra("ingredient");
            mealsListPresenter.loadData(tag, title);
        }
        return title;
    }

    @Override
    public void showInfo(List<Meal> meals) {
        List<Category> adapterList = new ArrayList<>();
        for (Meal meal : meals) adapterList.add(new Category(meal));
        adapter.setCategories(adapterList);
        adapter.setOnClickListener(position -> {
            Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
            intent.putExtra("mealId", meals.get(position).getIdMeal());
            startActivity(intent);
        });
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Check internet connection!", Toast.LENGTH_SHORT).show();
    }
}