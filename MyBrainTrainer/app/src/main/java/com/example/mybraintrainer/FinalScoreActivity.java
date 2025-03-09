package com.example.mybraintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class FinalScoreActivity extends AppCompatActivity {

    private TextView textViewScore;
    private String text = "Your score is: %s\nBest score ever is: %s";
    private int max;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        max = preferences.getInt("max", 0);
        textViewScore = findViewById(R.id.textViewScore);
        Intent intent = getIntent();
        if(intent.hasExtra("score")){
            score = intent.getIntExtra("score",0);
        }else{
            score = 0;
        }
        textViewScore.setText(String.format(Locale.getDefault(), text, score, max));
    }

    public void onClickReplay(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onClickClean(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().clear().apply();
        max = 0;
        textViewScore.setText(String.format(Locale.getDefault(), text, score, max));
    }
}