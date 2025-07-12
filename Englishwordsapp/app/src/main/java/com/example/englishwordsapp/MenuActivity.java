package com.example.englishwordsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // hide top info panel
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @SuppressLint("ClickableViewAccessibility")
    public void onClickSelect(View view) {
        Button button = (Button) view;
        int level = Integer.parseInt(button.getText().toString().trim());
        switch (level) {
            case 1 :
                Intent intent1 = new Intent(getApplicationContext(), FirstLevel.class);
                startActivity(intent1);
                break;
            case 2 :
                Intent intent2 = new Intent(getApplicationContext(), SecondLevel.class);
                startActivity(intent2);
                break;
            case 3 :
                Intent intent3 = new Intent(getApplicationContext(),ThirdLevel.class);
                startActivity(intent3);
                break;
            case 4 :
                Intent intent4 = new Intent(getApplicationContext(), FourthLevel.class);
                startActivity(intent4);
                break;
            case 5 :
                Intent intent5 = new Intent(getApplicationContext(), FifthLevel.class);
                startActivity(intent5);
                break;
            case 6 :
                Intent intent6 = new Intent(getApplicationContext(), SixthLevel.class);
                startActivity(intent6);
                break;
            case 7 :
                Intent intent7 = new Intent(getApplicationContext(), SeventhLevel.class);
                startActivity(intent7);
                break;
            case 8 :
                Intent intent8 = new Intent(getApplicationContext(), EighthLevel.class);
                startActivity(intent8);
                break;
            default:
                Intent intent9 = new Intent(getApplicationContext(), NinthLevel.class);
                startActivity(intent9);
                break;
        }
    }

    public void toMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}