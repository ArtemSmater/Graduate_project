package com.example.classworkgame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayingActivitySecond extends AppCompatActivity {

    private ImageView ivLeft, ivRight;
    private TextView tvLeft, tvRight;
    private int leftNum, rightNum, counter = 0;
    private ProgressBar pbProgress;
    private Animation animation;
    private Dialog dialog, dialogEnd;
    private final String[] numbersSecond = {
            "One", "Two", "Three",
            "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten"};
    private final int[] imagesSecond = {
            R.drawable.two_level_one,
            R.drawable.two_level_two,
            R.drawable.two_level_three,
            R.drawable.two_level_four,
            R.drawable.two_level_five,
            R.drawable.two_level_six,
            R.drawable.two_level_seven,
            R.drawable.two_level_eight,
            R.drawable.two_level_nine,
            R.drawable.two_level_ten};

    private final Random random = new Random();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // creating views
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);
        pbProgress = findViewById(R.id.pbLevels);
        TextView tvLevel = findViewById(R.id.tvLevel);
        tvLevel.setText(getString(R.string.level_two));

        // creating animation
        animation = AnimationUtils.loadAnimation(PlayingActivitySecond.this, R.anim.alpha);

        // round the corners
        ivLeft.setClipToOutline(true);
        ivRight.setClipToOutline(true);

        // calling the preview dialog window
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.preview_dialog);
        dialog.setCancelable(false);
        dialog.show();

        // set on click listeners
        dialog.findViewById(R.id.btnLeft).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PlayingActivity.class);
            startActivity(intent);
        });
        dialog.findViewById(R.id.btnRight).setOnClickListener(v -> dialog.dismiss());

        // set preview image
        ImageView iv = dialog.findViewById(R.id.ivPreview);
        iv.setImageResource(R.drawable.number_lev_two);

        // set preview text
        TextView tv = dialog.findViewById(R.id.tvPreview);
        tv.setText(getResources().getString(R.string.preview_second));

        // button to exit
        findViewById(R.id.btnSecondBack).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
            startActivity(intent);
        });

        // calling the end dialog window
        dialogEnd = new Dialog(this);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.setCancelable(false);

        // set on click listeners
        dialogEnd.findViewById(R.id.btnLeft).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
            startActivity(intent);
        });
        TextView tvEnd = dialogEnd.findViewById(R.id.tvPreview);
        tvEnd.setText(getString(R.string.end_second));

        // generate left image and text
        pbProgress.setMax(20);
        leftNum = random.nextInt(10);
        ivLeft.setImageResource(imagesSecond[leftNum]);
        tvLeft.setText(numbersSecond[leftNum]);


        // generate right image and text
        do {
            rightNum = random.nextInt(10);
        } while (leftNum == rightNum);
        ivRight.setImageResource(imagesSecond[rightNum]);
        tvRight.setText(numbersSecond[rightNum]);

        // click on the left image
        ivLeft.setOnTouchListener((v, event) -> {
            // finger push
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ivRight.setEnabled(false);
                if (leftNum > rightNum) ivLeft.setImageResource(R.drawable.img_true);
                else ivLeft.setImageResource(R.drawable.img_false);
                // finger up
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // if answer is correct
                if (leftNum > rightNum) {
                    if (counter < 20) counter++;
                    pbProgress.setProgress(counter);
                    // if answer is wrong
                } else {
                    if (counter > 0) {
                        if (counter == 1) counter = 0;
                        else counter -= 2;
                        pbProgress.setProgress(counter);
                    }
                }
                if (counter == 20) {
                    dialogEnd.show();
                } else {
                    leftNum = random.nextInt(10);
                    ivLeft.setImageResource(imagesSecond[leftNum]);
                    ivLeft.startAnimation(animation);
                    tvLeft.setText(numbersSecond[leftNum]);

                    do {
                        rightNum = random.nextInt(10);
                    } while (leftNum == rightNum);

                    ivRight.setImageResource(imagesSecond[rightNum]);
                    ivRight.startAnimation(animation);
                    tvRight.setText(numbersSecond[rightNum]);
                    ivRight.setEnabled(true);
                }
            }
            return true;
        });

        // click on the right image
        ivRight.setOnTouchListener((v, event) -> {
            // finger push
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ivLeft.setEnabled(false);
                if (leftNum < rightNum) ivRight.setImageResource(R.drawable.img_true);
                else ivRight.setImageResource(R.drawable.img_false);
                // finger up
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // if answer is correct
                if (leftNum < rightNum) {
                    if (counter < 20) counter++;
                    pbProgress.setProgress(counter);
                    // if answer is wrong
                } else {
                    if (counter > 0) {
                        if (counter == 1) counter = 0;
                        else counter -= 2;
                        pbProgress.setProgress(counter);
                    }
                }
                if (counter == 20) {
                    dialogEnd.show();
                } else {
                    leftNum = random.nextInt(10);
                    ivLeft.setImageResource(imagesSecond[leftNum]);
                    ivLeft.startAnimation(animation);
                    tvLeft.setText(numbersSecond[leftNum]);

                    do {
                        rightNum = random.nextInt(10);
                    } while (leftNum == rightNum);

                    ivRight.setImageResource(imagesSecond[rightNum]);
                    ivRight.startAnimation(animation);
                    tvRight.setText(numbersSecond[rightNum]);
                    ivLeft.setEnabled(true);
                }
            }
            return true;
        });
    }
}