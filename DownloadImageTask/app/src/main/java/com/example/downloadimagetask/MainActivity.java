package com.example.downloadimagetask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void onClickDownloadImage(View view) throws ExecutionException, InterruptedException {
        DownloadImageTask task = new DownloadImageTask();
        String url = "https://n1s2.hsmedia.ru/23/f4/55/23f455010cc1fe15c19eb0f8aa5a9424/1200x1082_0xac120003_5523762921620203529.jpg";
        Bitmap bitmap = task.execute(url).get();
        imageView.setImageBitmap(bitmap);
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
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }
    }

}