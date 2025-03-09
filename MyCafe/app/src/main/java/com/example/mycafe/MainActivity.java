package com.example.mycafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void makeOrder(View view) {
        String userName = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(!userName.isEmpty() && !password.isEmpty()){
            Intent intent = new Intent(this,MakeOrderActivity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("password", password);
            startActivity(intent);
        }else {
            Toast.makeText(this, R.string.toast_text,Toast.LENGTH_SHORT).show();
        }

    }
}