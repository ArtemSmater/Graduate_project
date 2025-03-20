package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewTable);
        arrayList = new ArrayList<>();
        arrayList.add("0");
    }

    public void onClick(View view) {
        char next = ((Button) view).getText().toString().charAt(0);
        if (arrayList.get(0).equals("0") && arrayList.size() == 1 && checkNext(next)) {
            arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(next));
        } else {
            arrayList.add(String.valueOf(next));
        }
        getLine(arrayList);
        textView.setText(String.join("", arrayList));
    }
    private void getLine(List<String> value) {
        ArrayList<String> result = new ArrayList<>(value);
        if (result.size() > 1) {
            String line = result.get(result.size() - 2);
            line += result.get(result.size() - 1);
            boolean isRight = line.matches("\\D\\D");
            if (isRight) {
                result.set(result.size() - 2, result.get(result.size() - 1));
                result.remove(result.size() - 1);
            }
        }
        arrayList = result;
    }

    private boolean checkNext(char next) {
        return next != '+' && next != 'X' && next != '/' && next != '.';
    }

    public void clear(View view) {
        arrayList = new ArrayList<>();
        arrayList.add("0");
        String listString = String.join("", arrayList);
        textView.setText(listString);
    }

    public void delete(View view) {
        if (arrayList.size() <= 1) {
            clear(view);
        } else {
            arrayList.remove(arrayList.size() - 1);
            String listString = String.join("", arrayList);
            textView.setText(listString);
        }
    }


    public void solveExpression(View view) {
        String line = String.join("", arrayList).replace('X', '*');
        boolean isNegative = false;
        if (line.charAt(0) == '-') {
            line = line.substring(1);
            isNegative = true;
        }
        String[] numbers = line.split("[*/+-]");
        ArrayList<Character> symbols = new ArrayList<>();
        String find = "[*/+-]";
        Pattern pattern = Pattern.compile(find);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            symbols.add(matcher.group().charAt(0));
        }
        ArrayList<Double> array = new ArrayList<>();
        for (String str : numbers) {
            array.add(Double.parseDouble(str));
        }
        if (isNegative) {
            double negative = array.get(0) * -1.;
            array.set(0, negative);
        }
        int minus = 0;
        int plus = 0;
        int multiple = 0;
        int divide = 0;
        for (Character symbol : symbols) {
            if (symbol == '+') {
                plus++;
            }
            if (symbol == '-') {
                minus++;
            }
            if (symbol == '*') {
                multiple++;
            }
            if (symbol == '/') {
                divide++;
            }
        }
        for (int i = 0; i < symbols.size(); i++) {
            for (int j = 0; j < multiple; j++) {
                for (int k = 0; k < symbols.size(); k++) {
                    if (symbols.get(k) == '*') {
                        double d = array.get(k) * array.get(k + 1);
                        array.remove(k);
                        array.remove(k);
                        array.add(k, d);
                        symbols.remove(k);
                    }
                }
            }
            System.out.println(array);
            System.out.println(symbols);
            for (int j = 0; j < divide; j++) {
                for (int k = 0; k < symbols.size(); k++) {
                    if (symbols.get(k) == '/') {
                        double d = array.get(k) / array.get(k + 1);
                        array.remove(k);
                        array.remove(k);
                        array.add(k, d);
                        symbols.remove(k);
                    }
                }
            }
            for (int j = 0; j < minus; j++) {
                for (int k = 0; k < symbols.size(); k++) {
                    if (symbols.get(k) == '-') {
                        double d = array.get(k) - array.get(k + 1);
                        array.remove(k);
                        array.remove(k);
                        array.add(k, d);
                        symbols.remove(k);
                    }
                }
            }
            for (int j = 0; j < plus; j++) {
                for (int k = 0; k < symbols.size(); k++) {
                    if (symbols.get(k) == '+') {
                        double d = array.get(k) + array.get(k + 1);
                        array.remove(k);
                        array.remove(k);
                        array.add(k, d);
                        symbols.remove(k);
                    }
                }
            }
            String result;
            if (array.get(0) - array.get(0).intValue() > 0.) {
                result = array.get(0).toString();
            } else {
                result = Integer.toString(array.get(0).intValue());
            }
            arrayList = new ArrayList<>();
            arrayList.add(result);
            textView.setText(result);
        }
    }
}