package com.example.discountcalculate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText price;
    private TextView percent;
    private SharedPreferences preferences;
    private LinkedHashMap<String, String> linked;
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linked = new LinkedHashMap<>();
        price = findViewById(R.id.editTextText);
        percent = findViewById(R.id.tvPercent);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        preferences = getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE);
        if (!preferences.getAll().isEmpty()) {
            for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
                linked.put(entry.getKey(), (String) entry.getValue());
            }
        }
        adapter = new NoteAdapter(linked);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void onClickSolve(View view) {
        if (!price.getText().toString().isEmpty()) {
            int number = Integer.parseInt(price.getText().toString().trim());
            double discount = 0;
            if (number >= 500 && number < 1000) {
                discount = number * 0.03;
                percent.setText(String.format(Locale.getDefault(), "Your discount is %d percent", 3));
                percent.setTextColor(getColor(R.color.light_green));
            } else if (number >= 1000) {
                discount = number * 0.05;
                percent.setText(String.format(Locale.getDefault(), "Your discount is %d percent", 5));
                percent.setTextColor(getColor(R.color.green));
            } else {
                percent.setText(String.format(Locale.getDefault(), "Your discount is %d percent", 0));
                percent.setTextColor(getColor(R.color.violet));
            }
            double finalPrice = number - discount;
            String key = String.format(Locale.getDefault(), "%.1f", finalPrice);
            String value = String.format(Locale.getDefault(), "%.1f", discount);
            linked.put(key, value);
            boolean isWrote = preferences.edit().putString(key, value).commit();
            if (isWrote) {
                Toast.makeText(this, "Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
            adapter.setValues(linked);
        } else {
            Toast.makeText(this, "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickClearList(View view) {
        boolean isDeleted = preferences.edit().clear().commit();
        if(isDeleted) {
            linked = new LinkedHashMap<>();
            adapter.setValues(linked);
            Toast.makeText(this, "List is cleared!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "List is not cleared!", Toast.LENGTH_SHORT).show();
        }
    }
}