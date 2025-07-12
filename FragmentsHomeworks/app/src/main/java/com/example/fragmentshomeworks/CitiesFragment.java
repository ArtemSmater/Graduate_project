package com.example.fragmentshomeworks;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class CitiesFragment extends Fragment {

    private Spinner spinCountry, spinRus, spinBel, spinChi;
    private final int[] imgRus = {R.drawable.moscow, R.drawable.saint_petersburg, R.drawable.novosibirsk};
    private final int[] imgBel = {R.drawable.minsk, R.drawable.gomel, R.drawable.vitebsk};
    private final int[] imgChi = {R.drawable.beijing, R.drawable.shanghai, R.drawable.tianjin};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        spinCountry = view.findViewById(R.id.spinnerCountries);
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    spinBel.setVisibility(INVISIBLE);
                    spinChi.setVisibility(INVISIBLE);
                    spinRus.setVisibility(VISIBLE);
                } else if (position == 1) {
                    spinBel.setVisibility(VISIBLE);
                    spinChi.setVisibility(INVISIBLE);
                    spinRus.setVisibility(INVISIBLE);
                } else if (position == 2){
                    spinBel.setVisibility(INVISIBLE);
                    spinChi.setVisibility(VISIBLE);
                    spinRus.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinRus = view.findViewById(R.id.spinnerRU);
        spinBel = view.findViewById(R.id.spinnerBL);
        spinChi = view.findViewById(R.id.spinnerUK);
        view.findViewById(R.id.buttonToShow).setOnClickListener(v -> onClickShowInfo());
        return view;
    }

    public void onClickShowInfo() {
        String[] cities;
        String[] descriptions;
        int cityPosition;
        String city;
        String description;
        int resId;
        if(spinCountry.getSelectedItemPosition() == 0) {
            cityPosition = spinRus.getSelectedItemPosition();
            cities = getResources().getStringArray(R.array.rus_cities);
            descriptions = getResources().getStringArray(R.array.rus_cities_desc);
            city = cities[cityPosition];
            description = descriptions[cityPosition];
            resId = imgRus[cityPosition];
        } else if (spinCountry.getSelectedItemPosition() == 1) {
            cityPosition = spinBel.getSelectedItemPosition();
            cities = getResources().getStringArray(R.array.bel_cities);
            descriptions = getResources().getStringArray(R.array.blr_cities_desc);
            city = cities[cityPosition];
            description = descriptions[cityPosition];
            resId = imgBel[cityPosition];
        } else {
            cityPosition = spinChi.getSelectedItemPosition();
            cities = getResources().getStringArray(R.array.china_cities);
            descriptions = getResources().getStringArray(R.array.china_cities_desc);
            city = cities[cityPosition];
            description = descriptions[cityPosition];
            resId = imgChi[cityPosition];
        }
        Bundle bundle = new Bundle();
        bundle.putString("city",city);
        bundle.putString("description", description);
        bundle.putInt("resId",resId);

        Fragment fragment2 = new CityDetails();
        fragment2.setArguments(bundle);

        assert getFragmentManager() != null;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameInfo, fragment2)
                .commit();
    }
}