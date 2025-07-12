package com.example.englishwordsapp;

import static android.view.View.INVISIBLE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class FirstLevel extends AppCompatActivity {

    private Animation animAlpha;
    private TextView tvPoints, tvTime;
    private ImageView ivTask;
    private ProgressBar progressBar;
    private int rightAns, rightPos, counter = 0, seconds = 0, points = 0;
    private Random random;
    private Dialog prevDialog, endDialog;
    private boolean isRunning;
    private final Button[] buttons = new Button[4];
    private final String[] animalsTxt = {"badger", "bear", "bison",
            "chipmunk", "coyote", "deer",
            "euro bison", "ferret", "fox",
            "hare", "hedgehog", "hog",
            "lynx", "marmot", "mole",
            "moose", "raccoon", "skunk",
            "sloth", "squirrel", "wolf",
            "wolverine"};

    private final int[] animalsImg = {R.drawable.badger, R.drawable.bear, R.drawable.bison,
            R.drawable.chipmunk, R.drawable.coyote, R.drawable.deer,
            R.drawable.european_bison, R.drawable.ferret, R.drawable.fox,
            R.drawable.hare, R.drawable.hedgehog, R.drawable.hog,
            R.drawable.lynx, R.drawable.marmot, R.drawable.mole,
            R.drawable.moose, R.drawable.raccoon, R.drawable.skunk,
            R.drawable.sloth, R.drawable.squirrel, R.drawable.wolf,
            R.drawable.wolverine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init views
        ivTask = findViewById(R.id.ivTask);
        Button btnOne = findViewById(R.id.btnOne);
        Button btnTwo = findViewById(R.id.btnTwo);
        Button btnThree = findViewById(R.id.btnThree);
        Button btnFour = findViewById(R.id.btnFour);
        tvPoints = findViewById(R.id.tvPoints);
        tvTime = findViewById(R.id.tvTimer);
        progressBar = findViewById(R.id.progressBar);
        TextView title = findViewById(R.id.tvLevelTitle);
        prevDialog = new Dialog(this);
        endDialog = new Dialog(this);

        // add buttons to array
        buttons[0] = btnOne;
        buttons[1] = btnTwo;
        buttons[2] = btnThree;
        buttons[3] = btnFour;

        // init variable
        random = new Random();
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        progressBar.setMax(25);
        progressBar.setProgress(counter);
        title.setText(getString(R.string.animals));
        Intent intentMode = getIntent();
        boolean gameMode = intentMode.getBooleanExtra("gameMode", false);
        tvPoints.setText(String.format(Locale.getDefault(), "%d points", points));

        // previous dialog window
        prevDialog.setContentView(R.layout.preview_dialog);
        prevDialog.setCancelable(false);
        prevDialog.show();
        prevDialog.findViewById(R.id.btnRight).setOnClickListener(v -> {
            prevDialog.dismiss();

            isRunning = true;
        });

        Button btnToBack = prevDialog.findViewById(R.id.btnLeft);
        btnToBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        });
        if (gameMode) {
            btnToBack.setClickable(false);
        } else {
            tvTime.setVisibility(INVISIBLE);
            tvPoints.setVisibility(INVISIBLE);
            isRunning = false;
        }

        // after level dialog window
        endDialog.setContentView(R.layout.dialog_end);
        endDialog.setCancelable(false);
        endDialog.findViewById(R.id.btnRight).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SecondLevel.class);
            intent.putExtra("points", points);
            intent.putExtra("seconds", seconds);
            intent.putExtra("gameMode", gameMode);
            startActivity(intent);
        });
        endDialog.findViewById(R.id.btnLeft).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        });

        // generate question
        createQuestion();
        runTimer();
    }

    private void createQuestion() {
        // generate right answer position
        rightPos = random.nextInt(4);

        // generate right and wrong answers
        rightAns = random.nextInt(22);
        Set<Integer> wrongSet = new TreeSet<>();
        wrongSet.add(rightAns);
        while (wrongSet.size() < 4) {
            wrongSet.add(random.nextInt(22));
        }
        List<Integer> wrongPositions = new ArrayList<>(wrongSet);
        wrongPositions.remove(Integer.valueOf(rightAns));

        // set image resource and animation to image view
        ivTask.setImageResource(animalsImg[rightAns]);
        ivTask.startAnimation(animAlpha);

        // set text to buttons
        for (int i = 0, j = 0; i < buttons.length; i++) {
            if (i == rightPos) {
                buttons[i].setText(animalsTxt[rightAns]);
                buttons[i].startAnimation(animAlpha);
            } else {
                buttons[i].setText(animalsTxt[wrongPositions.get(j)]);
                buttons[i].startAnimation(animAlpha);
                j++;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onClickAnswer(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == rightPos) {
            points++;
            counter++;
            progressBar.setProgress(counter);
            Toast.makeText(getApplicationContext(), "Right answer!", Toast.LENGTH_SHORT).show();
        } else {
            points = 0;
            if (counter > 0) {
                if (counter == 1) counter = 0;
                else counter -= 2;
            }
            progressBar.setProgress(counter);
            Toast.makeText(getApplicationContext(), "Wrong answer!\nRight answer is " +
                    animalsTxt[rightAns], Toast.LENGTH_SHORT).show();
        }
        tvPoints.setText(String.format(Locale.getDefault(), "%d points", points));
        checkWon();
        createQuestion();
    }

    private void checkWon() {
        if (counter == 25) {
            isRunning = false;
            endDialog.show();
        }
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                tvTime.setText(time);

                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void toMainMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}