package com.example.englishwordsapp;

import static android.view.View.INVISIBLE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class EndGameActivity extends AppCompatActivity {

    private TextView tvResults;
    private BottomSheetBehavior<View> sheetBehavior;
    private SharedPreferences preferences;
    private ArrayList<SavedItem> items;
    private EditText etName;
    private Button btnToSave;
    private String time;
    private int points;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init views
        tvResults = findViewById(R.id.tvResults);
        etName = findViewById(R.id.etPlayer);
        btnToSave = findViewById(R.id.btnToSave);
        RecyclerView rvResults = findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        preferences = getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE);
        if (!preferences.getAll().isEmpty()) {
            for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
                String[] patterns = entry.getValue().toString().split("\\+-=");
                items.add(new SavedItem(entry.getKey(), patterns[1], patterns[0]));
            }
        }

        // recycler view settings
        adapter = new ItemAdapter(items);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        // set on click listener
        btnToSave.setOnClickListener(v -> saveUserResult());

        // button to start page
        findViewById(R.id.btnToStart).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        // button to clear results
        findViewById(R.id.btnToClear).setOnClickListener(v -> {
            preferences.edit().clear().apply();
            items = new ArrayList<>();
            adapter.setItems(items);
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        showResults();
    }

    private void showResults() {
        Intent intent = getIntent();
        points = intent.getIntExtra("points", 0);
        time = intent.getStringExtra("time");
        String result = String.format(Locale.getDefault(), "Your score is %d\nYour time is %s", points, time);
        tvResults.setText(result);
    }

    public void saveUserResult() {
        String key = etName.getText().toString().trim();
        if (!key.isEmpty()) {
            if (!preferences.contains(key)){
                items.add(new SavedItem(key, time, points + ""));
                String value = points + "+-=" + time;
                preferences.edit().putString(key, value).apply();
                adapter.setItems(items);
                Toast.makeText(this, "Result was saved!", Toast.LENGTH_SHORT).show();
                etName.setVisibility(INVISIBLE);
                btnToSave.setVisibility(INVISIBLE);
            } else {
                Toast.makeText(this, "This name is already existed!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Do not leave empty field to save your result!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickClear(View view) {
        preferences.edit().clear().apply();
        items = new ArrayList<>();
        adapter.setItems(items);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}