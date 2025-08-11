package com.example.loginproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init views
        Button btnToOpen = findViewById(R.id.btnToLogin);
        Button btnToContinue = findViewById(R.id.btnToRegister);
        etEmail = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        helper = new DBHelper(getApplicationContext());

        // set listeners to buttons
        btnToOpen.setOnClickListener(v -> {
            RegisterFragment registerFragment = new RegisterFragment();
            registerFragment.show(getSupportFragmentManager(), registerFragment.getTag());
        });

        btnToContinue.setOnClickListener(v -> checkExistedUser());
    }

    private void checkExistedUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(!email.isEmpty() || !password.isEmpty()) {
            if (helper.checkExisted(email)) {
                if (helper.checkCorrectPassword(email, password)) {
                    Intent intent = new Intent(this, OrderActivity.class);
                    intent.putExtra("key", email);
                    startActivity(intent);
                    clearFields();
                } else {
                    etEmail.setBackgroundResource(R.drawable.et_main_background);
                    etPassword.setBackgroundResource(R.drawable.et_wrong_background);
                    Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }
            } else {
                etPassword.setBackgroundResource(R.drawable.et_main_background);
                etEmail.setBackgroundResource(R.drawable.et_wrong_background);
                Toast.makeText(this, "This email has not found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            etPassword.setBackgroundResource(R.drawable.et_wrong_background);
            etEmail.setBackgroundResource(R.drawable.et_wrong_background);
            Toast.makeText(this, "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etEmail.setText("");
        etPassword.setText("");
    }
}