package com.example.fileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnToSave, btnToShow, btnToNext;
    private static final String FILE_NAME = "content.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init views
        btnToSave = findViewById(R.id.btnToSave);
        btnToShow = findViewById(R.id.btnToShow);
        btnToNext = findViewById(R.id.btnToNext);

        // on click save file
        btnToSave.setOnClickListener(v -> {
            FileOutputStream outputStream = null;
            EditText editText = findViewById(R.id.etData);
            String text = editText.getText().toString().trim();

            try {
                outputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                outputStream.write(text.getBytes());
                Toast.makeText(this, "The file was saved!", Toast.LENGTH_SHORT).show();
                editText.setText("");
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // on click show file data
        btnToShow.setOnClickListener(v -> {
            FileInputStream inputStream = null;
            TextView textView = findViewById(R.id.tvShowData);

            try {
                inputStream = openFileInput(FILE_NAME);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String text = new String(bytes);
                textView.setText(text);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnToNext.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent);
        });
    }
}