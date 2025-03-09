package com.example.colordescriptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
    }

    public void showDescription(View view) {
        int position = spinner.getSelectedItemPosition();
        textView.setText(getDescriptionByPosition(position));
    }

    private String getDescriptionByPosition(int position){
        String[] description = getResources().getStringArray(R.array.colors_description);
        return description[position];
    }
}