package com.example.mycafe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowOrderActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        TextView textViewOrder = findViewById(R.id.textViewOrder);
        Intent intent = getIntent();
        if(intent.hasExtra("order")){
            textViewOrder.setText(intent.getStringExtra("order"));
        }else{
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }
    }
}