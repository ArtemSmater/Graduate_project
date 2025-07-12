package com.example.fragmentshomeworks;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Notebook extends Fragment {

    private BottomSheetBehavior<View> sheetBehavior;
    private EditText etTitle, etDescription, etDate, etTime;
    private RadioGroup radioGroup;
    private Button btnCreate;
    private List<Note> notes;
    private NoteAdapter adapter;
    private Calendar calendar;
    private SharedPreferences preferences;
    private final String space = "+-=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get views
        View view = inflater.inflate(R.layout.fragment_notebook, container, false);
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etDate = view.findViewById(R.id.etCalendar);
        etTime = view.findViewById(R.id.etTime);
        radioGroup = view.findViewById(R.id.radioGroup);
        btnCreate = view.findViewById(R.id.btnToCreate);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // create the collection
        notes = new ArrayList<>();

        // get date from the database
        preferences = requireContext().getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE);
        if (!preferences.getAll().isEmpty()) {
            for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
                String[] patterns = entry.getKey().split("\\+-=");
                notes.add(new Note(patterns[0], patterns[1], patterns[2], patterns[3], Integer.parseInt(patterns[4])));
            }
        }

        adapter = new NoteAdapter(notes);
        adapter.setOnClickListener(this::deleteNote);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //bottom sheet
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    etTime.setFocusable(false);
                    etDate.setFocusable(false);

                    etTime.setOnClickListener(v -> showTimeDialog(etTime));
                    etDate.setOnClickListener(v -> showDateDialog(etDate));

                    btnCreate.setOnClickListener(v -> saveData());
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                view.findViewById(R.id.secondLayout).setAlpha(1.2f - slideOffset);
            }
        });
        return view;
    }

    private void deleteNote(int position) {
        notes.remove(position);
        preferences.edit().clear().apply();
        for (Note note : notes) {
            String key = note.getTitle() + space + note.getDescription() +
                    space + note.getTime() +
                    space + note.getDate() + space + note.getPriority();
            preferences.edit().putString(key, "1").apply();
        }
        adapter.setNotes(notes);
        Toast.makeText(getContext(), "The note was deleted!", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void saveData() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        int priority = Integer.parseInt(radioButton.getTag().toString());
        if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
            String key = title + space + description + space + time + space + date + space + priority;

            // save the note in database
            if (!preferences.contains(key)) {
                preferences.edit().putString(key, "1").apply();
                notes.add(new Note(title, description, date, time, priority));
                adapter.notifyDataSetChanged();
                clearFields();
                Toast.makeText(getContext(), "The note was saved!", Toast.LENGTH_SHORT).show();
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                Toast.makeText(getContext(), "This note is already exist!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTimeDialog(EditText time) {
        calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            time.setText(dateFormat.format(calendar.getTime()));
        };
        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }

    private void showDateDialog(EditText date) {
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormat.format(calendar.getTime()));
        };
        new DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void clearFields() {
        RadioButton rb = radioGroup.findViewById(R.id.rbFirst);
        rb.setChecked(true);
        etTitle.setText("");
        etDescription.setText("");
        etDate.setText("");
        etTime.setText("");
    }
}