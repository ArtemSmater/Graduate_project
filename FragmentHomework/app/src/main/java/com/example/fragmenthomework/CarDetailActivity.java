package com.example.fragmenthomework;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CarDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.ivDescription);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        Intent intent = getIntent();
        if(intent.hasExtra("title") && intent.hasExtra("description") && intent.hasExtra("resId")) {
            int resId = intent.getIntExtra("resId",0);
            imageView.setImageResource(resId);
            tvTitle.setText(intent.getStringExtra("title"));
            tvDescription.setText(intent.getStringExtra("description"));
        }
    }
}