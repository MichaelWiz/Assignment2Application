package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class RecordNewVaccineBatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_vaccine_batch);
        EditText dateTextView = findViewById(R.id.edit_text_expiry_date);
        ImageButton imageButtonCalender = findViewById(R.id.image_button_calendar_icon);
        imageButtonCalender.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RecordNewVaccineBatch.this,
                    (view, year1, month1, dayOfMonth1) -> dateTextView.setText(dayOfMonth1 + "/" + month1 + "/" + year1), year, month, dayOfMonth);
            datePickerDialog.show();
        });
    }
}