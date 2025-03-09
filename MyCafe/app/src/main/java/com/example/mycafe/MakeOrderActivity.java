package com.example.mycafe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MakeOrderActivity extends AppCompatActivity {

    private TextView textViewAdditions;
    private CheckBox cbSugar;
    private CheckBox cbMilk;
    private CheckBox cbLemon;
    private Spinner spinnerTeaTypes;
    private Spinner spinnerCoffeeTypes;
    private String name;
    private String password;
    private String drink;
    private StringBuilder builder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        TextView textViewHello = findViewById(R.id.textViewHello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        cbSugar = findViewById(R.id.cbSugar);
        cbMilk = findViewById(R.id.cbMilk);
        cbLemon = findViewById(R.id.cbLemon);
        spinnerTeaTypes = findViewById(R.id.spinnerTeaTypes);
        spinnerCoffeeTypes = findViewById(R.id.spinnerCoffeeTypes);
        Intent intent = getIntent();
        if(intent.hasExtra("userName") && intent.hasExtra("password")){
            name = intent.getStringExtra("userName");
            password = intent.getStringExtra("password");
        }else{
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        textViewHello.setText(String.format(getString(R.string.hello_order_text),name));
        textViewAdditions.setText(String.format(getString(R.string.drink_additions), drink = getString(R.string.tea)));
        builder = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id == R.id.radioButtonTea){
            drink = getString(R.string.tea);
            cbLemon.setVisibility(View.VISIBLE);
            spinnerTeaTypes.setVisibility(View.VISIBLE);
            spinnerCoffeeTypes.setVisibility(View.INVISIBLE);
        }else{
            drink = getString(R.string.coffee);
            cbLemon.setVisibility(View.INVISIBLE);
            spinnerTeaTypes.setVisibility(View.INVISIBLE);
            spinnerCoffeeTypes.setVisibility(View.VISIBLE);
        }
        textViewAdditions.setText(String.format(getString(R.string.drink_additions), drink));
    }

    public void onClickOrderDrink(View view) {
        builder.setLength(0);
        if(cbSugar.isChecked()){
            builder.append(getString(R.string.sugar)).append(", ");
        }
        if(cbMilk.isChecked()){
            builder.append(getString(R.string.milk)).append(", ");
        }
        if(cbLemon.isChecked()){
            builder.append(getString(R.string.lemon)).append(", ");
        }
        String additions;
        if(builder.length() > 0){
            additions = builder.substring(0,builder.length() - 2) + ".";

        }else {
            additions = " ";
        }
        String drinkType;
        if(drink.equals(getString(R.string.tea))){
            drinkType = spinnerTeaTypes.getSelectedItem().toString();
        }else{
            drinkType = spinnerCoffeeTypes.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.full_order), name, password, drink, drinkType, additions);
        Intent intent = new Intent(this, ShowOrderActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }
}