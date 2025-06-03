package com.example.classworkgame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class PlayingActivityThird extends AppCompatActivity {

    private ImageView ivLeft, ivRight;
    private TextView tvLeft, tvRight;
    private int leftNum, rightNum, counter = 0;
    private ProgressBar pbProgress;
    private Animation animation;
    private Dialog dialog, dialogEnd;
    private final String[] numbersThree = {
            "One/Sixteen", "One/Eight", "Three/Sixteen",
            "One/Four", "Five/Sixteen", "One/Three",
            "Three/Eight", "Seven/Sixteen", "One/Two",
            "Nine/Sixteen", "Five/Eight", "Two/Three",
            "Eleven/Sixteen", "Three/Four", "Thirty/Sixteen",
            "Seven/Eight", "Fifteen/Sixteen", "One",
            "Four/Three", "Three/Two", "Seven/Four",};
    private final int[] imagesThree = {
            R.drawable.three_level_1, R.drawable.three_level_2, R.drawable.three_level_3,
            R.drawable.three_level_4, R.drawable.three_level_5, R.drawable.three_level_6,
            R.drawable.three_level_7, R.drawable.three_level_8, R.drawable.three_level_9,
            R.drawable.three_level_10, R.drawable.three_level_11, R.drawable.three_level_12,
            R.drawable.three_level_13, R.drawable.three_level_14, R.drawable.three_level_15,
            R.drawable.three_level_16, R.drawable.three_level_17, R.drawable.three_level_18,
            R.drawable.three_level_19, R.drawable.three_level_20, R.drawable.three_level_21,
    };

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
        ConstraintLayout clPlaying = findViewById(R.id.main);
        clPlaying.setBackgroundResource(R.drawable.level_3);
        TextView tvLevel = findViewById(R.id.tvLevel);
        tvLevel.setText(getString(R.string.level_three));

        // creating animation
        animation = AnimationUtils.loadAnimation(PlayingActivityThird.this, R.anim.alpha);

        // round the corners
        ivLeft.setClipToOutline(true);
        ivRight.setClipToOutline(true);

        // calling the preview dialog window
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.preview_dialog);
        dialog.setCancelable(false);
        dialog.show();

        // set on click listeners
        LinearLayout previewLayout = dialog.findViewById(R.id.dialogPreview);
        previewLayout.setBackgroundResource(R.drawable.preview_background_3);
        Button btnLeft = dialog.findViewById(R.id.btnLeft);
        btnLeft.setBackgroundResource(R.drawable.btn_third_level);
        btnLeft.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
            startActivity(intent);
        });
        Button btnRight = dialog.findViewById(R.id.btnRight);
        btnRight.setBackgroundResource(R.drawable.btn_third_level);
        btnRight.setOnClickListener(v -> dialog.dismiss());

        // set preview image
        ImageView iv = dialog.findViewById(R.id.ivPreview);
        iv.setImageResource(R.drawable.preview_img_3);

        // set preview text
        TextView tv = dialog.findViewById(R.id.tvPreview);
        tv.setText(getResources().getString(R.string.preview_third));

        // button to exit
        findViewById(R.id.btnSecondBack).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
            startActivity(intent);
        });

        // calling the end dialog window
        dialogEnd = new Dialog(this);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.setCancelable(false);

        // button to exit
        Button btnLeftEnd = dialogEnd.findViewById(R.id.btnLeft);
        btnLeftEnd.setBackgroundResource(R.drawable.btn_third_level);
        btnLeftEnd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
            startActivity(intent);
        });

        // button to next level
        Button btnRightEnd = dialogEnd.findViewById(R.id.btnRight);
        btnRightEnd.setBackgroundResource(R.drawable.btn_third_level);

        // set backgrounds
        LinearLayout layoutEnd = dialogEnd.findViewById(R.id.dialogEnd);
        layoutEnd.setBackgroundResource(R.drawable.preview_background_3);
        TextView tvEnd = dialogEnd.findViewById(R.id.tvPreview);
        tvEnd.setText(getString(R.string.end_third));

        // generate left image and text
        pbProgress.setMax(20);
        leftNum = random.nextInt(21);
        ivLeft.setImageResource(imagesThree[leftNum]);
        tvLeft.setText(numbersThree[leftNum]);


        // generate right image and text
        do {
            rightNum = random.nextInt(21);
        } while (leftNum == rightNum);
        ivRight.setImageResource(imagesThree[rightNum]);
        tvRight.setText(numbersThree[rightNum]);

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
                    leftNum = random.nextInt(21);
                    ivLeft.setImageResource(imagesThree[leftNum]);
                    ivLeft.startAnimation(animation);
                    tvLeft.setText(numbersThree[leftNum]);

                    do {
                        rightNum = random.nextInt(21);
                    } while (leftNum == rightNum);

                    ivRight.setImageResource(imagesThree[rightNum]);
                    ivRight.startAnimation(animation);
                    tvRight.setText(numbersThree[rightNum]);
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
                    leftNum = random.nextInt(21);
                    ivLeft.setImageResource(imagesThree[leftNum]);
                    ivLeft.startAnimation(animation);
                    tvLeft.setText(numbersThree[leftNum]);

                    do {
                        rightNum = random.nextInt(21);
                    } while (leftNum == rightNum);

                    ivRight.setImageResource(imagesThree[rightNum]);
                    ivRight.startAnimation(animation);
                    tvRight.setText(numbersThree[rightNum]);
                    ivLeft.setEnabled(true);
                }
            }
            return true;
        });
    }
}