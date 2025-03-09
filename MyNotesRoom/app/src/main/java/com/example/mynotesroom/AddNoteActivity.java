package com.example.mynotesroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mynotesroom.roomcomponents.MainViewModel;
import com.example.mynotesroom.roomcomponents.Note;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    public void onClickAddNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int weekDayId = spinner.getSelectedItemPosition();
        int resId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(resId);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if(isFitted(title, description)){
            viewModel.insertNote(new Note(title, description, weekDayId, priority));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean isFitted(String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}