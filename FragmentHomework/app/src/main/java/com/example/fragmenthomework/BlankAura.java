package com.example.fragmenthomework;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class BlankAura extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_aura, container, false);

        ArrayList<Car> carsBmw = new ArrayList<>();
        carsBmw.add(new Car(getString(R.string.G30), getString(R.string.G30_descr), R.drawable.g30));
        carsBmw.add(new Car(getString(R.string.F11), getString(R.string.F11_descr), R.drawable.f11));
        carsBmw.add(new Car(getString(R.string.F10), getString(R.string.F10_descr), R.drawable.f10));
        carsBmw.add(new Car(getString(R.string.E61), getString(R.string.E61_descr), R.drawable.e61));
        carsBmw.add(new Car(getString(R.string.E60), getString(R.string.E60_descr), R.drawable.e60));
        carsBmw.add(new Car(getString(R.string.E39), getString(R.string.E39_descr), R.drawable.e39));

        RecyclerView recyclerView = view.findViewById(R.id.rvBmw);
        CarAdapter adapter = new CarAdapter(carsBmw);
        adapter.setOnClickListener(position -> {
            Car car = carsBmw.get(position);
            Intent intent = new Intent(getContext(), CarDetailActivity.class);
            intent.putExtra("title", car.getTitle());
            intent.putExtra("description", car.getDescription());
            intent.putExtra("resId", car.getImgRes());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}