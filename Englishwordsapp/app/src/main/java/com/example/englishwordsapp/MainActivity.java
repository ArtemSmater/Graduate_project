package com.example.englishwordsapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnMenu;
    private Animation animDown, animUp;
    private boolean gameMode;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initialize views
        btnStart = findViewById(R.id.btnToStart);
        btnMenu = findViewById(R.id.btnToMenu);

        // initialize animations
        animDown = AnimationUtils.loadAnimation(this, R.anim.anim_in);
        animUp = AnimationUtils.loadAnimation(this, R.anim.anim_out);

        // set animations and actions to buttons
        btnStart.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(@SuppressLint("ClickableViewAccessibility") View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnStart.startAnimation(animDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnStart.startAnimation(animUp);
                    gameMode = true;
                    Intent intent = new Intent(getApplicationContext(), FirstLevel.class);
                    intent.putExtra("gameMode", gameMode);
                    startActivity(intent);
                }
                return true;
            }
        });

        btnMenu.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(@SuppressLint("ClickableViewAccessibility") View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnMenu.startAnimation(animDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnMenu.startAnimation(animUp);
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

}