package com.example.gamewithpc;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;

public class EasyPlayGame extends AppCompatActivity {

    private int activePlayer;
    private int scoreX = 0, scoreO = 0;
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private ImageView[] images;
    private final int[][] combinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 4, 8}, {2, 4, 6}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}};
    private LinearLayout firstLinear, secondLinear;
    private TextView firstName, secondName, firstScore, secondScore;
    private Handler handler;
    private boolean playSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_play_game);

        // filling array
        images = new ImageView[]{findViewById(R.id.image1), findViewById(R.id.image2), findViewById(R.id.image3),
                findViewById(R.id.image4), findViewById(R.id.image5), findViewById(R.id.image6),
                findViewById(R.id.image7), findViewById(R.id.image8), findViewById(R.id.image9)};

        // create views
        firstName = findViewById(R.id.firstPlayerName);
        secondName = findViewById(R.id.secondPlayerName);
        firstScore = findViewById(R.id.textScore1);
        secondScore = findViewById(R.id.textScore2);
        firstLinear = findViewById(R.id.linearFirst);
        secondLinear = findViewById(R.id.linearSecond);
        firstScore.setText(String.format(Locale.getDefault(), "%d", scoreO));
        secondScore.setText(String.format(Locale.getDefault(), "%d", scoreX));

        // get intent
        String playerName = getIntent().getStringExtra("name");
        String computer = "PC";
        playSide = getIntent().getBooleanExtra("playSide", false);

        // set names
        if (playSide) {
            secondName.setText(playerName);
            firstName.setText(computer);
            secondLinear.setBackgroundResource(R.drawable.checked_player);
            activePlayer = 1;
        } else {
            secondName.setText(computer);
            firstName.setText(playerName);
            firstLinear.setBackgroundResource(R.drawable.checked_player);
            activePlayer = 2;
            boxPositions[4] = 1;
            fillXBox(images[4]);
        }
    }

    // checking winner combinations
    private boolean checkResult() {
        boolean result = false;
        for (int[] array : combinations) {
            if (boxPositions[array[0]] == activePlayer &&
                    boxPositions[array[1]] == activePlayer &&
                    boxPositions[array[2]] == activePlayer) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void onClick(View view) {
        performAction((ImageView) view);
    }

    // checking pressed button
    private void performAction(ImageView image) {
        int resId = image.getId();
        if (resId == R.id.image1 && isExisted(0)) {
            boxPositions[0] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image2 && isExisted(1)) {
            boxPositions[1] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image3 && isExisted(2)) {
            boxPositions[2] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image4 && isExisted(3)) {
            boxPositions[3] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image5 && isExisted(4)) {
            boxPositions[4] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image6 && isExisted(5)) {
            boxPositions[5] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image7 && isExisted(6)) {
            boxPositions[6] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image8 && isExisted(7)) {
            boxPositions[7] = activePlayer;
            checkPlayer(image);
        } else if (resId == R.id.image9 && isExisted(8)) {
            boxPositions[8] = activePlayer;
            checkPlayer(image);
        }
    }

    // setting drawable resources
    private void checkPlayer(ImageView image) {
        if (activePlayer == 1) {
            fillXBox(image);
            if (checkResult()) {
                endGame();
            } else {
                activePlayer = 2;
                computerTurnO();
                if (checkResult()) {
                    endGame();
                }
                activePlayer = 1;
            }
        } else if (activePlayer == 2) {
            fillOBox(image);
            if (checkResult()) {
                endGame();
            } else {
                activePlayer = 1;
                computerTurnX();
                if (checkResult()) {
                    endGame();
                }
                activePlayer = 2;
            }
        }
        if (!Arrays.toString(boxPositions).contains("0") && !checkResult()) {
            ResultDialog dialog = new ResultDialog(EasyPlayGame.this,
                    "Draw!", EasyPlayGame.this);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private int getRandomPosition() {
        int position;
        do {
            position = (int) (Math.random() * 9);
        } while (boxPositions[position] != 0);
        return position;
    }

    private void computerTurnX() {
        int position = -1;
        for (int[] array : combinations) {
            if (boxPositions[array[0]] == 0 && boxPositions[array[1]] == activePlayer && boxPositions[array[2]] == activePlayer) {
                position = array[0];
                boxPositions[position] = activePlayer;
                fillXBox(images[position]);
                break;
            } else if (boxPositions[array[0]] == activePlayer && boxPositions[array[1]] == 0 && boxPositions[array[2]] == activePlayer) {
                position = array[1];
                boxPositions[position] = activePlayer;
                fillXBox(images[position]);
                break;
            } else if (boxPositions[array[0]] == activePlayer && boxPositions[array[1]] == activePlayer && boxPositions[array[2]] == 0) {
                position = array[2];
                boxPositions[position] = activePlayer;
                fillXBox(images[position]);
                break;
            }
        }
        if (position == -1 && Arrays.toString(boxPositions).contains("0")) {
            position = getRandomPosition();
            boxPositions[position] = activePlayer;
            fillXBox(images[position]);
        }
    }

    private void computerTurnO() {
        int position = -1;
        for (int[] array : combinations) {
            if (boxPositions[array[0]] == 0 && boxPositions[array[1]] == activePlayer && boxPositions[array[2]] == activePlayer) {
                position = array[0];
                boxPositions[position] = activePlayer;
                fillOBox(images[position]);
                break;
            } else if (boxPositions[array[0]] == activePlayer && boxPositions[array[1]] == 0 && boxPositions[array[2]] == activePlayer) {
                position = array[1];
                boxPositions[position] = activePlayer;
                fillOBox(images[position]);
                break;
            } else if (boxPositions[array[0]] == activePlayer && boxPositions[array[1]] == activePlayer && boxPositions[array[2]] == 0) {
                position = array[2];
                boxPositions[position] = activePlayer;
                fillOBox(images[position]);
                break;
            }
        }
        if (position == -1 && Arrays.toString(boxPositions).contains("0")) {
            position = getRandomPosition();
            boxPositions[position] = activePlayer;
            fillOBox(images[position]);
        }
    }


    private void fillOBox(ImageView image) {
        if (!playSide) {
            image.setBackgroundResource(R.drawable.score_field_background);
            image.setScaleType(ImageView.ScaleType.CENTER);
            image.setImageResource(R.drawable.o_big_icon);
            firstLinear.setBackgroundResource(R.drawable.score_field_background);
            secondLinear.setBackgroundResource(R.drawable.checked_player);
        } else {
            frozeButtons();
            handler = new Handler();
            handler.postDelayed(() -> {
                image.setBackgroundResource(R.drawable.score_field_background);
                image.setScaleType(ImageView.ScaleType.CENTER);
                image.setImageResource(R.drawable.o_big_icon);
                firstLinear.setBackgroundResource(R.drawable.score_field_background);
                secondLinear.setBackgroundResource(R.drawable.checked_player);
                unfrozeButtons();
            }, 500);
        }
    }


    private void fillXBox(ImageView image) {
        if (playSide) {
            image.setBackgroundResource(R.drawable.score_field_background);
            image.setScaleType(ImageView.ScaleType.CENTER);
            image.setImageResource(R.drawable.x_big_icon);
            firstLinear.setBackgroundResource(R.drawable.checked_player);
            secondLinear.setBackgroundResource(R.drawable.score_field_background);
        } else {
            frozeButtons();
            handler = new Handler();
            handler.postDelayed(() -> {
                image.setBackgroundResource(R.drawable.score_field_background);
                image.setScaleType(ImageView.ScaleType.CENTER);
                image.setImageResource(R.drawable.x_big_icon);
                firstLinear.setBackgroundResource(R.drawable.checked_player);
                secondLinear.setBackgroundResource(R.drawable.score_field_background);
                unfrozeButtons();
            }, 500);
        }
    }

    private void frozeButtons() {
        for (ImageView image : images) {
            image.setClickable(false);
        }
    }

    private void unfrozeButtons() {
        for (ImageView image : images) {
            image.setClickable(true);
        }
    }

    private void endGame() {
        if (activePlayer == 1) {
            ResultDialog dialog = new ResultDialog(EasyPlayGame.this,
                    secondName.getText().toString() + " is a winner!", EasyPlayGame.this);
            dialog.setCancelable(false);
            dialog.show();
            scoreX++;
            secondScore.setText(String.format(Locale.getDefault(), "%d", scoreX));
        } else {
            ResultDialog dialog = new ResultDialog(EasyPlayGame.this,
                    firstName.getText().toString() + " is a winner!", EasyPlayGame.this);
            dialog.setCancelable(false);
            dialog.show();
            scoreO++;
            firstScore.setText(String.format(Locale.getDefault(), "%d", scoreO));
        }
    }

    // checking if existed
    private boolean isExisted(int position) {
        return boxPositions[position] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (ImageView image : images) {
            image.setImageResource(R.drawable.score_field_background);
        }
        if (getIntent().getBooleanExtra("playSide", false)) {
            secondLinear.setBackgroundResource(R.drawable.checked_player);
            firstLinear.setBackgroundResource(R.drawable.score_field_background);
            activePlayer = 1;
        } else {
            secondLinear.setBackgroundResource(R.drawable.score_field_background);
            firstLinear.setBackgroundResource(R.drawable.checked_player);
            activePlayer = 2;
        }
    }
}