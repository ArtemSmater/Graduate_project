package com.example.studyapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private ArrayList<Integer> imgList;
    private int count;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgList = new ArrayList<>();
        addImagesToList();
        Button buttonClear = findViewById(R.id.button1);
        Button buttonRandom = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                textView.setText(returnStringValue(count));
                imageView.setImageResource(R.drawable.smile);
            }
        });
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                imageView.setImageResource(imgList.get((int) ((Math.random() * (imgList.size() - 2)) + 2)));
                count = (int) (Math.random() * 1000);
                textView.setText(returnStringValue(count));
            }
        });
    }

    private String returnStringValue(int value) {
        return Integer.toString(value);
    }

    private void addImagesToList() {
        imgList.add(R.drawable.f2);
        imgList.add(R.drawable.f3);
        imgList.add(R.drawable.f4);
        imgList.add(R.drawable.f5);
        imgList.add(R.drawable.f6);
        imgList.add(R.drawable.f7);
        imgList.add(R.drawable.f8);
        imgList.add(R.drawable.f9);
        imgList.add(R.drawable.f10);
        imgList.add(R.drawable.f11);
        imgList.add(R.drawable.f12);
        imgList.add(R.drawable.f13);
        imgList.add(R.drawable.f14);
        imgList.add(R.drawable.f15);
    }

    public void onClickCount(View view) {
        textView.setText(returnStringValue(++count));
        imageView.setImageResource(imgList.get(index++));
        if (index >= imgList.size()) {
            index = 0;
        }
    }
}