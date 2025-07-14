package com.example.fileapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private static final String FILE_NAME = "document.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnToSave = findViewById(R.id.btnToSave);
        Button btnToShow = findViewById(R.id.btnToShow);
        Button btnToGet = findViewById(R.id.btnToGet);

        // button to save
        btnToSave.setOnClickListener(v -> {
            try (FileOutputStream outputStream = new FileOutputStream(getExternalPath())) {
                EditText editText = findViewById(R.id.etData);
                String text = editText.getText().toString();
                outputStream.write(text.getBytes());
                editText.setText("");
                Toast.makeText(SecondActivity.this, "The file was saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(SecondActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // button to write
        btnToShow.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.tvShowData);
            File file = getExternalPath();

            if (!file.exists()) return;

            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String text = new String(bytes);
                textView.setText(text);
            } catch (IOException e) {
                Toast.makeText(SecondActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        checkPermission();

        // button to get permission
        btnToGet.setOnClickListener(v -> {
            getPermission();
        });
    }

    private void checkPermission() {
        // permission before android 10 (API 29)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission has not been granted!\nAPI 29 or less!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission granted!\nAPI 29 or less!", Toast.LENGTH_SHORT).show();
            }
        }

        // permission for android 11 (API 30) and more
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Toast.makeText(this, "Permission has not been granted!\nAPI 30 or more!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission granted!\nAPI 30 or more!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getPermission() {

        // permission before android 10 (API 29)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SecondActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }

        // permission for android 11 (API 30) and more
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            }
        }
        checkPermission();
    }

    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME);
    }
}