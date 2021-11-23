package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Calendar;

public class RequestVaccination extends AppCompatActivity {

    Spinner spinnerVaccine;
    Spinner spinnerVaccineBatch;
    Spinner spinnerCentre;
    android.app.DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vaccination);
        EditText datePicker = findViewById(R.id.text_view_select_date);
        ImageButton calendarBtn = findViewById(R.id.image_btn_calendar);

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestVaccination.this,
                        (view, year1, month1, dayOfMonth1) -> datePicker.setText(dayOfMonth1 + "/" + month1 + "/" + year1), year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        Spinner spinnerVaccine = findViewById(R.id.spinner_request_vaccine);
        ArrayAdapter adapterVaccine = ArrayAdapter.createFromResource(this,
                R.array.vaccines, android.R.layout.simple_spinner_item);
        adapterVaccine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccine.setAdapter(adapterVaccine);

        Spinner spinnerCentre = findViewById(R.id.spinner_request_centre);
        ArrayAdapter adapterCentre = ArrayAdapter.createFromResource(this,
                R.array.centres, android.R.layout.simple_spinner_item);
        adapterCentre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentre.setAdapter(adapterCentre);

        Spinner spinnerVaccineBatch = findViewById(R.id.spinner_request_vaccine_batch);
        ArrayAdapter adapterVaccineBatch = ArrayAdapter.createFromResource(this,
                R.array.vaccineBatch, android.R.layout.simple_spinner_item);
        adapterVaccineBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccineBatch.setAdapter(adapterVaccine);



    }
}