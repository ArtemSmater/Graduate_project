package com.example.myclassworkapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.coloredText);
        text.setTextColor(getColor(R.color.green));
        Button button = findViewById(R.id.buttonToChange);
        button.setOnClickListener(v -> {
            RadioGroup group = findViewById(R.id.radioGroup);
            RadioButton rb = findViewById(group.getCheckedRadioButtonId());
            if (rb.getId() == R.id.radioGreen) text.setTextColor(getColor(R.color.green));
            if (rb.getId() == R.id.radioRed) text.setTextColor(getColor(R.color.red));
        });

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(v -> {
            if (toggleButton.isChecked()) {
                toggleButton.setBackgroundColor(getColor(R.color.blue));
            } else {
                toggleButton.setBackgroundColor(getColor(R.color.orange));
            }
        });

        TextView sampleText = findViewById(R.id.textExample);
        CheckBox checkBold = findViewById(R.id.checkBoxBold);
        CheckBox checkItalic = findViewById(R.id.checkBoxItalic);
        checkBold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                if (checkItalic.isChecked()) sampleText.setTypeface(null, Typeface.BOLD_ITALIC);
                else sampleText.setTypeface(null, Typeface.BOLD);
            else if (checkItalic.isChecked()) sampleText.setTypeface(null, Typeface.ITALIC);
            else sampleText.setTypeface(null, Typeface.NORMAL);
        });

        checkItalic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                if (checkBold.isChecked()) sampleText.setTypeface(null, Typeface.BOLD_ITALIC);
                else sampleText.setTypeface(null, Typeface.ITALIC);
            else if (checkBold.isChecked()) sampleText.setTypeface(null, Typeface.BOLD);
            else sampleText.setTypeface(null, Typeface.NORMAL);
        });
    }
}