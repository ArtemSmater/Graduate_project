package com.example.loginproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends BottomSheetDialogFragment {

    private EditText etEmail, etName, etPassword, etPhone, etSecondPass;
    private DBHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
        view.setBackgroundResource(R.drawable.bottom_sheet_background);

        // init views
        Button btnToDismiss = view.findViewById(R.id.btnToDismiss);
        Button btnToCreate = view.findViewById(R.id.btnToCreate);
        etEmail = view.findViewById(R.id.etUserInfo);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassOne);
        etSecondPass = view.findViewById(R.id.etPassTwo);

        // init variables
        helper = new DBHelper(getContext());

        // set listeners to buttons
        btnToDismiss.setOnClickListener(v -> dismiss());
        btnToCreate.setOnClickListener(v -> {
            if (getData()) {
                dismiss();
            }
        });
        return view;
    }

    private boolean getData() {
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String secPass = etSecondPass.getText().toString().trim();
        if (!email.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
            if (checkEmail(email) && checkPhone(phone)) {
                if (!helper.checkExisted(email)) {
                    if (password.equals(secPass)) {
                        if (helper.addItem(new User(email, name, password, phone))) {
                            Toast.makeText(getContext(), "Successful registration!", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "This email is already existed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Wrong type of email or phone!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Do not leave empty fields!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean checkPhone(String line) {
        Pattern pattern = Pattern.compile("[+]?[0-9]{11}");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }

    private boolean checkEmail(String line) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9.]+@[a-z]+\\.[a-z]{2,10}");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
}