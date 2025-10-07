package com.example.dishescollection.screens.fragments.fragments.categories;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.dishescollection.R;
import com.example.dishescollection.adapters.CategoriesAdapter;

import com.example.dishescollection.pojo.Category;

import com.example.dishescollection.screens.meals.MealsListActivity;


import java.util.List;

public class CategoriesFragment extends Fragment implements CategoriesView {

    private CategoriesAdapter categoriesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init views
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerViewCategories = view.findViewById(R.id.recyclerCategories);

        // init variables
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesAdapter = new CategoriesAdapter();
        recyclerViewCategories.setAdapter(categoriesAdapter);

        // get data object
        CategoriesPresenter categoriesPresenter = new CategoriesPresenter(this);
        categoriesPresenter.loadData();

        return view;
    }

    @Override
    public void showInfo(List<Category> categories) {
        categoriesAdapter.setCategories(categories);
        categoriesAdapter.setOnClickListener(position -> {

            // call the next activity
            Intent intent = new Intent(getContext(), MealsListActivity.class);
            intent.putExtra("tag", 1);
            intent.putExtra("category", categories.get(position).getStrCategory());
            startActivity(intent);
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Check internet connection!", Toast.LENGTH_SHORT).show();
    }
}