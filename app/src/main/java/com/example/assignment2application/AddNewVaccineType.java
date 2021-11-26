package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddNewVaccineType extends AppCompatActivity {

    EditText editTextVaccineName;
    EditText editTextManufacturer;
    Vaccine vaccine;
    Button addBtn;
    Button backBtn;
    List<String> vaccinesArray = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vaccine_type);
        getCurrentVaccine();

        editTextVaccineName =findViewById(R.id.edit_text_vaccine_name);
        editTextManufacturer = findViewById(R.id.edit_text_manufacturer);
        addBtn = findViewById(R.id.btn_add_vaccine);
        backBtn = findViewById(R.id.btn_back_vaccine);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vaccineName = editTextVaccineName.getText().toString();
                String manufacturer = editTextManufacturer.getText().toString();
                Vaccine.vaccineNum = vaccinesArray.size();
                vaccine = new Vaccine(vaccineName, manufacturer);
                if (vaccineName.isEmpty()||manufacturer.isEmpty()){
                    Toast.makeText(AddNewVaccineType.this,
                            "Please enter all text", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (vaccinesArray == null ){
                        upload(vaccine);
                        Toast.makeText(AddNewVaccineType.this,
                                "Added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for (String v:vaccinesArray){
                            if (!v.equalsIgnoreCase(vaccine.getVaccineID())) {
                                upload(vaccine);
                                Toast.makeText(AddNewVaccineType.this,
                                        "Added Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewVaccineType.this, adminMenu.class));
                finish();
            }
        });
    }
    private void upload(Vaccine vaccine) {
        db.collection("Vaccines")
                .document(vaccine.getVaccineID())
                .set(vaccine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddNewVaccineType.this,
                                "Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewVaccineType.this, adminMenu.class));
                        finish();
                    }
                })
                .addOnFailureListener((e)->{
                    Toast.makeText(AddNewVaccineType.this,
                            "Failure", Toast.LENGTH_SHORT).show();
                });
    }
    private void getCurrentVaccine () {
        db.collection("Vaccines").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentSnapshot snapshot : documentSnapshots)
                {
                    vaccinesArray.add(snapshot.getString("vaccineID"));

                }
            }
        });
    }
}