package com.example.fragmentshomeworks;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CityDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_details, container, false);
        TextView tvTitle = view.findViewById(R.id.tvCityTitle);
        TextView tvDescription = view.findViewById(R.id.tvCityDescription);
        ImageView ivCity = view.findViewById(R.id.ivCity);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            tvTitle.setText(bundle.getString("city"));
            ivCity.setImageResource(bundle.getInt("resId"));
            tvDescription.setText(bundle.getString("description"));
        }
        return view;
    }
}