package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewVaccineType extends AppCompatActivity {

    EditText editTextVaccineID;
    EditText editTextVaccineName;
    EditText editTextManufacturer;
    Vaccine vaccine;
    Button addBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vaccine_type);

        editTextVaccineID = findViewById(R.id.edit_text_vaccine_id);
        editTextVaccineName =findViewById(R.id.edit_text_vaccine_name);
        editTextManufacturer = findViewById(R.id.edit_text_manufacturer);
        addBtn = findViewById(R.id.btn_add_vaccine);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vaccineID = editTextVaccineID.getText().toString();
                String vaccineName = editTextVaccineName.getText().toString();
                String manufacturer = editTextManufacturer.getText().toString();
                if (vaccineID.isEmpty()||vaccineName.isEmpty()||manufacturer.isEmpty()){
                    Toast.makeText(AddNewVaccineType.this,
                            "Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Vaccine vaccine = new Vaccine(vaccineID, vaccineName, manufacturer);
                    upload(vaccine);
                }
            }
        });
    }
    private void upload(Vaccine vaccine) {
        db.collection("Vaccine")
                .document(editTextVaccineID.getText().toString())
                .set(vaccine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddNewVaccineType.this,
                                "Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewVaccineType.this, RecordNewVaccineBatch.class));
                        finish();
                    }
                })
                .addOnFailureListener((e)->{
                    Toast.makeText(AddNewVaccineType.this,
                            "Failure", Toast.LENGTH_SHORT).show();
                });
    }
}