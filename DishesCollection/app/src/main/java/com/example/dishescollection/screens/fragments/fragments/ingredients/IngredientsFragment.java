package com.example.dishescollection.screens.fragments.fragments.ingredients;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dishescollection.screens.meals.MealsListActivity;
import com.example.dishescollection.R;
import com.example.dishescollection.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment implements IngredientsView {

    private List<String> ingredients;
    private ListAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init views
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView rvIngredients = view.findViewById(R.id.rvIngredients);
        searchView = view.findViewById(R.id.searchView);

        // set variables
        ingredients = new ArrayList<>();
        adapter = new ListAdapter(ingredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIngredients.setAdapter(adapter);

        // get web data
        IngredientsPresenter ingredientsPresenter = new IngredientsPresenter(this);
        ingredientsPresenter.loadData();

        setListeners();
        return view;
    }

    private void setListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> result = new ArrayList<>();
                for (String line : ingredients)
                    if (line.toLowerCase().contains(newText.toLowerCase())) result.add(line);
                adapter.setItems(result);
                return false;
            }
        });
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MealsListActivity.class);
            intent.putExtra("tag", 3);
            intent.putExtra("ingredient", adapter.getItems().get(position));
            startActivity(intent);
        });
    }

    @Override
    public void showInfo(List<String> items) {
        ingredients.addAll(items);
        adapter.setItems(ingredients);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Check the internet!", Toast.LENGTH_SHORT).show();
    }
}