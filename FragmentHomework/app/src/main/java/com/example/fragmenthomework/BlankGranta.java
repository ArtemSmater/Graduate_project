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

public class BlankGranta extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_granta, container, false);

        ArrayList<Car> carsToyota = new ArrayList<>();
        carsToyota.add(new Car(getString(R.string.jigul_1), getString(R.string.jigul_1_descr), R.drawable.vaz2101));
        carsToyota.add(new Car(getString(R.string.jigul_2), getString(R.string.jigul_2_descr), R.drawable.vaz2102));
        carsToyota.add(new Car(getString(R.string.jigul_7), getString(R.string.jigul_7_descr), R.drawable.zhiga));
        carsToyota.add(new Car(getString(R.string.niva), getString(R.string.niva_descr), R.drawable.niva));
        carsToyota.add(new Car(getString(R.string.supra), getString(R.string.supra_descr), R.drawable.supra));
        carsToyota.add(new Car(getString(R.string.vesta), getString(R.string.vesta_descr), R.drawable.vesta));
        carsToyota.add(new Car(getString(R.string.xray), getString(R.string.xray_descr), R.drawable.xray));

        RecyclerView recyclerView = view.findViewById(R.id.rvGranta);
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