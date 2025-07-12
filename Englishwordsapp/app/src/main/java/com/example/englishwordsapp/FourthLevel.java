package com.example.englishwordsapp;

import static android.view.View.INVISIBLE;

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

public class FourthLevel extends AppCompatActivity {

    private TextView tvTime, tvPoints;
    private int seconds = 0, points = 0, pointsDef;
    private boolean isRunning = false;
    private Animation animAlpha;
    private Dialog prevDialog, endDialog;
    private ImageView ivTask;
    private ProgressBar progressBar;
    private int rightAns, rightPos, counter = 0;
    private Random random;
    private final Button[] buttons = new Button[4];
    private final String[] familyTxt = {
            "aunt", "bride", "brother",
            "brothers", "children", "daughter",
            "family", "father", "father-in-law",
            "grandchildren", "granddaughter", "grandma",
            "grandpa", "grandparents", "grandson",
            "groom", "husband", "mother",
            "mother-in-law", "nephew", "newborn",
            "niece", "pair", "parents",
            "pregnant", "sister", "son",
            "twins", "uncle", "wife"};

    private final int[] familyImg = {
            R.drawable.aunt, R.drawable.bride, R.drawable.brother,
            R.drawable.brothers, R.drawable.children, R.drawable.daughter,
            R.drawable.family, R.drawable.father, R.drawable.father_in_law,
            R.drawable.grandchildren, R.drawable.granddaughter, R.drawable.grandma,
            R.drawable.grandpa, R.drawable.grandparents, R.drawable.grandson,
            R.drawable.groom, R.drawable.husband, R.drawable.mother,
            R.drawable.mother_in_law, R.drawable.nephew, R.drawable.newborn,
            R.drawable.niece, R.drawable.pair, R.drawable.parents,
            R.drawable.pregnant, R.drawable.sister, R.drawable.son,
            R.drawable.twins, R.drawable.uncle, R.drawable.wife};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init views
        ivTask = findViewById(R.id.ivTask);
        tvPoints = findViewById(R.id.tvPoints);
        tvTime = findViewById(R.id.tvTimer);
        Button btnOne = findViewById(R.id.btnOne);
        Button btnTwo = findViewById(R.id.btnTwo);
        Button btnThree = findViewById(R.id.btnThree);
        Button btnFour = findViewById(R.id.btnFour);
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
        progressBar.setMax(35);
        progressBar.setProgress(counter);
        title.setText(getString(R.string.family));

        // init start dialog windows
        prevDialog.setContentView(R.layout.preview_dialog);
        prevDialog.setCancelable(false);
        // set preview image and text
        ImageView ivPrev = prevDialog.findViewById(R.id.ivPreview);
        ivPrev.setImageResource(R.drawable.family_prev);
        TextView tvPrev = prevDialog.findViewById(R.id.tvPreview);
        tvPrev.setText(R.string.family_prev);

        // copy-------------------------------------------------------------------------------------

        // set listeners to buttons
        Button btnToBack = prevDialog.findViewById(R.id.btnLeft);
        btnToBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        });
        prevDialog.findViewById(R.id.btnRight).setOnClickListener(v -> {
            prevDialog.dismiss();
            isRunning = true;
        });
        prevDialog.show();

        // set time and points
        Intent intentMode = getIntent();
        boolean gameMode = intentMode.getBooleanExtra("gameMode", false);
        if (gameMode) {
            btnToBack.setClickable(false);
            if (intentMode.hasExtra("seconds") && intentMode.hasExtra("points")) {
                seconds = intentMode.getIntExtra("seconds", 0);
                points = intentMode.getIntExtra("points", 0);
                pointsDef = points;
            }
            tvTime.setText(String.format(Locale.getDefault(), "%d points", seconds));
            tvPoints.setText(String.format(Locale.getDefault(), "%d points", points));
        } else {
            tvTime.setVisibility(INVISIBLE);
            tvPoints.setVisibility(INVISIBLE);
            isRunning = false;
        }

        // -----------------------------------------------------------------------------------------

        // init end dialog window
        endDialog.setContentView(R.layout.dialog_end);
        ImageView ivEnd = endDialog.findViewById(R.id.ivEnd);
        ivEnd.setImageResource(R.drawable.firework);
        TextView tvEnd = endDialog.findViewById(R.id.tvEnd);
        tvEnd.setText(R.string.family_end);
        // set listeners to buttons
        endDialog.findViewById(R.id.btnLeft).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        });
        endDialog.findViewById(R.id.btnRight).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FifthLevel.class);
            intent.putExtra("points", points);
            intent.putExtra("seconds", seconds);
            intent.putExtra("gameMode", gameMode);
            startActivity(intent);
        });

        // generate question
        runTimer();
        createQuestion();
    }

    private void createQuestion() {
        // generate right answer position
        rightPos = random.nextInt(4);

        // generate right and wrong answers
        rightAns = random.nextInt(30);
        Set<Integer> wrongSet = new TreeSet<>();
        wrongSet.add(rightAns);
        while (wrongSet.size() < 4) {
            wrongSet.add(random.nextInt(30));
        }
        List<Integer> wrongPositions = new ArrayList<>(wrongSet);
        wrongPositions.remove(Integer.valueOf(rightAns));

        // set image resource and animation to image view
        ivTask.setImageResource(familyImg[rightAns]);
        ivTask.startAnimation(animAlpha);

        // set text to buttons
        for (int i = 0, j = 0; i < buttons.length; i++) {
            if (i == rightPos) {
                buttons[i].setText(familyTxt[rightAns]);
                buttons[i].startAnimation(animAlpha);
            } else {
                buttons[i].setText(familyTxt[wrongPositions.get(j)]);
                buttons[i].startAnimation(animAlpha);
                j++;
            }
        }
    }

    public void onClickAnswer(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == rightPos) {
            points++;
            counter++;
            progressBar.setProgress(counter);
            Toast.makeText(getApplicationContext(), "Right answer!", Toast.LENGTH_SHORT).show();
        } else {
            points = pointsDef;
            if (counter > 0) {
                if (counter == 1) counter = 0;
                else counter -= 2;
            }
            progressBar.setProgress(counter);
            Toast.makeText(getApplicationContext(), "Wrong answer!\nRight answer is " +
                    familyTxt[rightAns], Toast.LENGTH_SHORT).show();
        }
        tvPoints.setText(String.format(Locale.getDefault(), "%d points", points));  // copy
        checkWon();
        createQuestion();
    }

    private void checkWon() {
        if (counter == 35) {
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
