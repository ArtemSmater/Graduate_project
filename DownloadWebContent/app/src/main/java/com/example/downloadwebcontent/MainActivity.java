package com.example.downloadwebcontent;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadWebTask webTask = new DownloadWebTask();
        try {
            String url = "https://dzen.ru/?clid=2411725&yredirect=true";
            String result = webTask.execute(url).get();
            Log.i("result", result);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class DownloadWebTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                String line = reader.readLine();
                while(line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return builder.toString();
        }
    }
}