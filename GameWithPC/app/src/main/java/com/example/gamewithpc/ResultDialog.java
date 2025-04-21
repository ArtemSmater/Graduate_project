package com.example.gamewithpc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultDialog extends Dialog {

    private final String message;
    private final int level;
    private EasyPlayGame easyField;
    private MediumPlayGame mediumField;
    private HardPlayGame hardField;

    public ResultDialog(@NonNull Context context, String message, EasyPlayGame easyField) {
        super(context);
        this.message = message;
        this.easyField = easyField;
        level = 1;
    }

    public ResultDialog(@NonNull Context context, String message, MediumPlayGame mediumField) {
        super(context);
        this.message = message;
        this.mediumField = mediumField;
        level = 2;
    }

    public ResultDialog(@NonNull Context context, String message, HardPlayGame hardField) {
        super(context);
        this.message = message;
        this.hardField = hardField;
        level = 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);
        TextView winnerText = findViewById(R.id.winnerField);
        winnerText.setText(message);
        Button backBtn = findViewById(R.id.btnToAgain);
        backBtn.setOnClickListener(v -> {
            switch (level) {
                case 1:
                    easyField.restartMatch();
                    break;
                case 2:
                    mediumField.restartMatch();
                    break;
                default:
                    hardField.restartMatch();
                    break;
            }
            dismiss();
        });
    }
}