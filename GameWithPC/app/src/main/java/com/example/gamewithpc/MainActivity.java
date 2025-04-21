package com.example.gamewithpc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText playerName;
    private CheckBox checkBoxLeft;
    private CheckBox checkBoxRight;
    private boolean isCheckedX = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerName = findViewById(R.id.editTextFirst);
        Spinner spinner = findViewById(R.id.spinner);
        checkBoxLeft = findViewById(R.id.cb_x);
        checkBoxRight = findViewById(R.id.cb_o);
        findViewById(R.id.button).setOnClickListener(v -> {
            String name = playerName.getText().toString().trim();
            int level = spinner.getSelectedItemPosition();
            if(!name.isEmpty() && level == 0) {
                Intent intent = new Intent(getApplicationContext(), EasyPlayGame.class);
                intent.putExtra("playSide", isCheckedX);
                intent.putExtra("name", name);
                startActivity(intent);
            } else if (!name.isEmpty() && level == 1) {
                Intent intent = new Intent(getApplicationContext(), MediumPlayGame.class);
                intent.putExtra("playSide", isCheckedX);
                intent.putExtra("name", name);
                startActivity(intent);
            } else if (!name.isEmpty() && level == 2) {
                Intent intent = new Intent(getApplicationContext(), HardPlayGame.class);
                intent.putExtra("playSide", isCheckedX);
                intent.putExtra("name", name);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkX(View view) {
        playerName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.x_small_icon, 0,0,0);
        checkBoxLeft.setChecked(true);
        checkBoxRight.setChecked(false);
        isCheckedX = true;
    }

    public void checkO(View view) {
        playerName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.o_small_icon, 0,0,0);
        checkBoxLeft.setChecked(false);
        checkBoxRight.setChecked(true);
        isCheckedX = false;
    }
}