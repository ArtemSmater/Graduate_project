package com.example.studyappactivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ImageView firstHand = findViewById(R.id.ivFirstHan);
        ImageView secondHand = findViewById(R.id.ivSecondHand);
        Animation animLeft = AnimationUtils.loadAnimation(this, R.anim.anim_left_hand);
        Animation animRight = AnimationUtils.loadAnimation(this, R.anim.anim_right_hand);

        // button to previous
        findViewById(R.id.button).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent);
        });

        // buttons to make image moving
        findViewById(R.id.buttonOfLeftHand).setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) firstHand.getDrawable();
            if(animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
            animationDrawable.start();
            firstHand.startAnimation(animLeft);
        });
        findViewById(R.id.buttonOfRightHand).setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) secondHand.getDrawable();
            if(animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
            animationDrawable.start();
            secondHand.startAnimation(animRight);
        });

        // pictures actions
        firstHand.setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) firstHand.getDrawable();
            if (!isRunning) {
                animationDrawable.start();
                animationDrawable.setOneShot(false);
                isRunning = true;
            } else {
                animationDrawable.stop();
                animationDrawable.setOneShot(true);
                isRunning = false;
            }
        });
        secondHand.setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) secondHand.getDrawable();
            if (!isRunning) {
                animationDrawable.start();
                animationDrawable.setOneShot(false);
                isRunning = true;
            } else {
                animationDrawable.stop();
                animationDrawable.setOneShot(true);
                isRunning = false;
            }
        });
    }
}