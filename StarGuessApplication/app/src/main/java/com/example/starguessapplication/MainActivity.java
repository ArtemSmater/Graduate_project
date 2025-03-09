package com.example.starguessapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private int numberOfQuestion;
    private int numberOfRightAnswer;
    private int score = 0;
    private int mistakes = 0;
    private ArrayList<String> namesList;
    private ArrayList<String> imageList;
    private ArrayList<Button> buttons;
    private ImageView imageView;
    private TextView textViewRight;
    private TextView textViewWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        imageView = findViewById(R.id.imageView);
        textViewRight = findViewById(R.id.textViewRight);
        textViewWrong = findViewById(R.id.textViewWrong);
        namesList = new ArrayList<>();
        imageList = new ArrayList<>();
        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        textViewRight.setText(String.format(Locale.getDefault(), getString(R.string.score), 0));
        textViewWrong.setText(String.format(Locale.getDefault(), getString(R.string.mistakes), 0));
        getWebInfo();
        playGame();
    }

    private void playGame(){
        generateQuestion();
        DownloadImageTask imageTask = new DownloadImageTask();
        try {
            Bitmap bitmap = imageTask.execute(imageList.get(numberOfQuestion)).get();
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            for(int i = 0; i < buttons.size(); i++){
                if(i == numberOfRightAnswer){
                    buttons.get(i).setText(namesList.get(numberOfQuestion));
                }else{
                    int wrongNumber;
                    do{
                        wrongNumber = generateWrongQuestion();
                    }while (wrongNumber == numberOfQuestion);
                    buttons.get(i).setText(namesList.get(wrongNumber));
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateQuestion(){
        numberOfQuestion = (int) (Math.random() * namesList.size());
        numberOfRightAnswer = (int) (Math.random() * buttons.size());
    }

    private int generateWrongQuestion(){
        return (int) (Math.random() * namesList.size());
    }

    private void getWebInfo(){
        DownloadWebTask webTask = new DownloadWebTask();
        try {
            String url = "https://toplists.ru/samye-krasivye-aktrisy-gollivuda";
            String result = webTask.execute(url).get();
            String start = "<div class=\"list-items\">";
            String stop = "<div class=\"row item add-item-wrapper\">";
            Pattern pattern = Pattern.compile(start + "(.*?)" + stop);
            Matcher matcher = pattern.matcher(result);
            String finalResult = "";
            while(matcher.find()){
                finalResult = matcher.group(1);
            }
            String start1 = "src=\"/";
            String stop1 = "\"";
            Pattern pattern1 = Pattern.compile(start1 + "(.*?)" + stop1);
            Matcher matcher1 = pattern1.matcher(finalResult);
            while (matcher1.find()){
                imageList.add("https://toplists.ru/" + matcher1.group(1));
            }
            String start2 = "alt=\"";
            String stop2 = "\"";
            Pattern pattern2 = Pattern.compile(start2 + "(.*?)" + stop2);
            Matcher matcher2 = pattern2.matcher(finalResult);
            while (matcher2.find()){
                namesList.add(matcher2.group(1));
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickAnswer(View view) {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        if(Integer.parseInt(tag) == numberOfRightAnswer){
            score++;
            textViewRight.setText(String.format(Locale.getDefault(), getString(R.string.score), score));
            Toast.makeText(this, "Right answer!", Toast.LENGTH_SHORT).show();
        }else{
            mistakes++;
            textViewWrong.setText(String.format(Locale.getDefault(), getString(R.string.mistakes), mistakes));
            Toast.makeText(this, "Wrong answer. The right answer is " + namesList.get(numberOfQuestion), Toast.LENGTH_SHORT).show();
        }
        playGame();
    }

    public void onClickClearScore(View view) {
        textViewRight.setText(String.format(Locale.getDefault(), getString(R.string.score), 0));
        textViewWrong.setText(String.format(Locale.getDefault(), getString(R.string.mistakes), 0));
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }
    }

    private static class DownloadWebTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                String line = reader.readLine();
                while (line != null){
                    result.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }
    }
}