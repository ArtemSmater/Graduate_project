package com.example.studyappactivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private MediaPlayer sound1, sound2;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // creating sounds
        sound1 = MediaPlayer.create(this, R.raw.sound_1);
        sound2 = MediaPlayer.create(this, R.raw.sound_2);

        // creating views
        ImageView imageSecond = findViewById(R.id.imageViewSecondBoy);
        ImageView image = findViewById(R.id.imageViewBoy);
        Button btnToBack = findViewById(R.id.buttonSecond);
        Button btnToNext = findViewById(R.id.buttonToThird);
        findViewById(R.id.imageButtonBurger).setOnClickListener(v -> playSound(sound1, sound2));
        findViewById(R.id.imageButtonHotDog).setOnClickListener(v -> playSound(sound2, sound1));

        // buttons to next activity
        btnToBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if (playSound(sound1, sound2)) {
                sound1.stop();
            }
            startActivity(intent);
        });
        btnToNext.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
            if (playSound(sound1, sound2)) {
                sound1.stop();
            }
            startActivity(intent);
        });

        // creating animations
        Animation rotateCenter = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rotate_center);
        Animation rotateCorner = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rotate_corner);
        Animation animScale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shrink);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate);
        Animation animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);

        // set animations to images
        image.setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) image.getDrawable();
            if (!isRunning) {
                image.startAnimation(animScale);
                animationDrawable.start();
                isRunning = true;
            } else {
                image.clearAnimation();
                animationDrawable.stop();
                isRunning = false;
            }
            image.startAnimation(animAlpha);
        });
        imageSecond.setOnClickListener(v -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) imageSecond.getDrawable();
            if (!isRunning) {
                imageSecond.startAnimation(translate);
                animationDrawable.start();
                isRunning = true;
            } else {
                animationDrawable.stop();
                imageSecond.clearAnimation();
                isRunning = false;
            }
            imageSecond.startAnimation(animAlpha);
        });

        // buttons to rotate
        Button centerButton = findViewById(R.id.buttonLeft);
        Button cornerButton = findViewById(R.id.buttonRight);
        centerButton.setOnClickListener(v -> image.startAnimation(rotateCenter));
        cornerButton.setOnClickListener(v -> imageSecond.startAnimation(rotateCorner));
    }

    // sound pictures
    private boolean playSound(MediaPlayer sound1, MediaPlayer sound2) {
        if (sound1.isPlaying()) {
            sound1.pause();
            sound2.seekTo(0);
            sound1.setLooping(false);
        }
        if (sound2.isPlaying()) {
            sound2.pause();
            sound1.seekTo(0);
            sound2.setLooping(false);
        }
        sound1.start();
        sound1.setLooping(true);
        return sound1.isPlaying();
    }
}