package com.example.russianstarguess;

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
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String url = "https://yurayakunin.livejournal.com/13798025.html";
    private int numberOfQuestion;
    private int numberOfRightAnswer;
    private String result;
    private ArrayList<String> names;
    private ArrayList<String> imgLinks;
    private ArrayList<Button> buttons;
    private ImageView imageView;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);
        names = new ArrayList<>();
        imgLinks = new ArrayList<>();
        buttons = new ArrayList<>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        getWebContent();
        playGame();
    }

    private void playGame() {
        generateQuestions();
        DownloadImageTask imageTask = new DownloadImageTask();
        Bitmap bitmap = null;
        try {
            bitmap = imageTask.execute(imgLinks.get(numberOfQuestion)).get();
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }
        catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0; i < buttons.size(); i++){
            if(i == numberOfRightAnswer){
                buttons.get(i).setText(names.get(numberOfQuestion).trim());
            }else{
                int wrong;
                do{
                    wrong = generateWrongAnswer();
                }while (wrong == numberOfQuestion);
                buttons.get(i).setText(names.get(wrong).trim());
            }
        }
    }

    private void generateQuestions(){
        numberOfQuestion = (int) (Math.random() * names.size());
        numberOfRightAnswer = (int) (Math.random() * buttons.size());
    }

    private int generateWrongAnswer(){
        return (int) (Math.random() * names.size());
    }

    private void getWebContent(){
        DownloadWebTask task = new DownloadWebTask();
        try {
            String htmlCode = task.execute(url).get();
            String start = "<a href=\"https://yurayakunin.livejournal.com/13796392.html\"";
            String end = "<a href";
            Pattern pattern = Pattern.compile(start + "(.*?)" + end);
            Matcher matcher = pattern.matcher(htmlCode);
            while(matcher.find()){
                result = matcher.group(1);
            }
            String start1 = "<b>";
            String end1 = "</b>";
            Pattern pattern1 = Pattern.compile(start1 + "(.*?)" + end1);
            Matcher matcher1 = pattern1.matcher(result);
            while (matcher1.find()){
                names.add((Objects.requireNonNull(matcher1.group(1))).substring(3));
            }
            String start2 = "src=\"";
            String end2 = "\"";
            Pattern pattern2 = Pattern.compile(start2 + "(.*?)" + end2);
            Matcher matcher2 = pattern2.matcher(result);
            while (matcher2.find()){
                imgLinks.add(matcher2.group(1));
            }
            Log.i("result", "Name: " + names.get(48) + " Link: " + imgLinks.get(48));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImage(View view) {
        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString()) ;
        if (numberOfRightAnswer == tag){
            Toast.makeText(this, "Right answer!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong answer!\nRight answer is " + names.get(numberOfQuestion), Toast.LENGTH_SHORT).show();
        }
        playGame();
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
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String line = bufferedReader.readLine();
                while(line != null){
                    result.append(line);
                    line = bufferedReader.readLine();
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
}