package com.example.dishescollection.screens.fragments.fragments.areas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dishescollection.screens.meals.MealsListActivity;
import com.example.dishescollection.R;
import com.example.dishescollection.adapters.ListAdapter;


import java.util.ArrayList;
import java.util.List;

public class AreaFragment extends Fragment implements AreasView {

    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init views
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        RecyclerView rvAreas = view.findViewById(R.id.recyclerViewAreas);

        // set values
        adapter = new ListAdapter(new ArrayList<>());
        rvAreas.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvAreas.setAdapter(adapter);

        // get web info
        AreasPresenter areasPresenter = new AreasPresenter(this);
        areasPresenter.loadData();

        return view;
    }


    @Override
    public void showData(List<String> areas) {
        adapter.setItems(areas);
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MealsListActivity.class);
            intent.putExtra("tag", 2);
            intent.putExtra("area", areas.get(position));
            startActivity(intent);
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Check the internet!", Toast.LENGTH_SHORT).show();
    }
}