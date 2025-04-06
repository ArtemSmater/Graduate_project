package com.example.studyappactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}