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

public class BlankVesta extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_vesta, container, false);

        ArrayList<Car> carsToyota = new ArrayList<>();
        carsToyota.add(new Car(getString(R.string.avanza), getString(R.string.avanza_descr), R.drawable.avanza));
        carsToyota.add(new Car(getString(R.string.camry), getString(R.string.camry_descr), R.drawable.camry));
        carsToyota.add(new Car(getString(R.string.chr), getString(R.string.chr_descr), R.drawable.chr));
        carsToyota.add(new Car(getString(R.string.hilux), getString(R.string.hilux_descr), R.drawable.hilux));
        carsToyota.add(new Car(getString(R.string.prado), getString(R.string.prado_descr), R.drawable.prado));
        carsToyota.add(new Car(getString(R.string.rav4), getString(R.string.rav4_descr), R.drawable.rav4));
        carsToyota.add(new Car(getString(R.string.supra), getString(R.string.supra_descr), R.drawable.supra));
        carsToyota.add(new Car(getString(R.string.yaris), getString(R.string.yaris_descr), R.drawable.yaris));

        RecyclerView recyclerView = view.findViewById(R.id.rvVesta);
        CarAdapter adapter = new CarAdapter(carsToyota);
        adapter.setOnClickListener(position -> {
            Car car = carsToyota.get(position);
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