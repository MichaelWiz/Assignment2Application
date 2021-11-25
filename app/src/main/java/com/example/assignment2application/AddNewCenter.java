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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNewCenter extends AppCompatActivity {

    private EditText editTextCenterName;
    private EditText editTextAddress;
    private Button addBtn;
    private Button backBtn;
    private HealthcareCentre hcc;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_center);
        db =FirebaseFirestore.getInstance();
        editTextCenterName = findViewById(R.id.edit_text_center_name_add);
        editTextAddress = findViewById(R.id.edit_text_address_add);
        addBtn = findViewById(R.id.btn_add_center);
        backBtn = findViewById(R.id.btn_back_center);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewCenter.this, AddNewVaccineType.class));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hccName = editTextCenterName.getText().toString();
                String address = editTextAddress.getText().toString();
                if(hccName.isEmpty()||address.isEmpty()){
                    Toast.makeText(AddNewCenter.this,
                            "Please enter all text", Toast.LENGTH_SHORT).show();
                }
                else {
                    HealthcareCentre hcc = new HealthcareCentre(hccName, address);
                    upload(hcc);
                }
            }
        });

    }

    private void upload(HealthcareCentre hcc) {
        db.collection("HealthcareCentres")
                .document(editTextCenterName.getText().toString())
                .set(hcc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddNewCenter.this,
                                "Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewCenter.this, AddNewVaccineType.class));
                        finish();
                    }
                })
                .addOnFailureListener((e)->{
                        Toast.makeText(AddNewCenter.this,
                               "Failure", Toast.LENGTH_SHORT).show();
                });
    }
}