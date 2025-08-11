package com.example.loginproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private DBHelper helper;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init variables
        tvInfo = findViewById(R.id.tvUserInfo);
        helper = new DBHelper(this);
        Intent intent = getIntent();
        showInfo(intent.getStringExtra("key"));

    }

    private void showInfo(String email) {
        User user = helper.getInfo(email);
        tvInfo.setText(String.format("Hello, %s\n\nYour email is: %s\nYour phone number is %s\nYour password is %s",
                user.getName(), user.getEmail(), user.getNumber(), user.getPassword()));
    }
}