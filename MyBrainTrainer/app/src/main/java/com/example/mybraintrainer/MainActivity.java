package com.example.mybraintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScore;
    private TextView textViewQuestion;
    private TextView textViewTime;
    private TextView textViewLevel;
    private int min = 5;
    private int max = 30;
    private int rightAnswer;
    private int rightAnswerPosition;
    private int score = 0;
    private int mistakes = 0;
    private int level = 1;
    private int count;
    private boolean isPositive;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private ArrayList<Button> buttons;
    int time = 500000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ArrayList<>();
        textViewScore = findViewById(R.id.textViewScore);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewTime = findViewById(R.id.textViewTimer);
        textViewLevel = findViewById(R.id.textViewLevel);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        playGame();
        CountDownTimer timer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(score % 10 == 0){
                    millisUntilFinished += 10000;
                }
                textViewTime.setText(getTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max = preferences.getInt("max", 0);
                if(score >= max){
                    preferences.edit().putInt("max",score).apply();
                }
                Intent intent = new Intent(getApplicationContext(), FinalScoreActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        };
        timer.start();
    }

    private String getTime(long mills){
        int seconds = (int) (mills / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes,seconds);
    }

    private int generateWrongAnswer(){
        int result;
        do{
            result = (int) (Math.random() * (max * 3 + 1) - (max - min));
        }while (rightAnswer == result);
        return result;
    }

    @SuppressLint("SetTextI18n")
    private void playGame(){
        textViewLevel.setText(String.format(getString(R.string.level_string), Integer.toString(level)));
        generateQuestion();
        textViewScore.setText(String.format(Locale.getDefault(), "%s / %s", score, mistakes));
        for(int i = 0; i < buttons.size(); i++){
            if(i == rightAnswerPosition){
                buttons.get(i).setText(Integer.toString(rightAnswer));
            }else{
                buttons.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
    }

    private void generateQuestion(){
        int a = (int) (Math.random() * max - min + 1) + min;
        int b = (int) (Math.random() * max - min + 1) + min;
        int mark = (int) (Math.random() * 2);
        String result;
        isPositive = mark == 1;
        if(isPositive){
            rightAnswer = a + b;
            result = String.format(Locale.getDefault(), "%s + %s", a, b);
        }else{
            rightAnswer = a - b;
            result = String.format(Locale.getDefault(), "%s - %s", a, b);
        }
        textViewQuestion.setText(result);
        rightAnswerPosition = (int) (Math.random() * buttons.size());
    }

    public void onClickAnswer(View view) {
        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString());
        if(tag == rightAnswerPosition){
            score++;
            Toast.makeText(this, "Right answer!", Toast.LENGTH_SHORT).show();
        }else{
            mistakes++;
            Toast.makeText(this, "Wrong answer!\nRight answer is: " + rightAnswer, Toast.LENGTH_SHORT).show();
        }
        playGame();
    }
}