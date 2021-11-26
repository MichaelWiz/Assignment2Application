package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminMenu extends AppCompatActivity {

    Button btnLogout;
    Button btnViewVaccineBatch;
    Button btnRecordVaccineBatch;
    Button btnAddVaccine;
    Button btnAddCentre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        initializeView();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnViewVaccineBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(adminMenu.this, ViewVaccineBatchActivity.class);
//                startActivity(intent);
            }
        });

        btnRecordVaccineBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(adminMenu.this, RecordNewVaccineBatch.class);
//                startActivity(intent);
            }
        });

        btnAddCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(adminMenu.this , AddNewCentre.class);
//                startActivity(intent);
            }
        });

        btnAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(adminMenu.this, AddNewVaccineType.class);
//                startActivity(intent);
            }
        });
    }

    private void initializeView(){
        btnLogout = findViewById(R.id.btn_logout);
        btnViewVaccineBatch = findViewById(R.id.btn_view_vaccine_batch);
        btnRecordVaccineBatch = findViewById(R.id.btn_record_vaccine_batch);
        btnAddVaccine = findViewById(R.id.btn_add_vaccine);
        btnAddCentre = findViewById(R.id.btn_add_centre);
    }

    private void onClickButton(){

    }



}