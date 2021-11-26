package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManageVaccinationActivity extends AppCompatActivity {

    String batchNumber, vaccinationNumber;
    TextView patientFullnameTextView,patientIDTextView,
            vaccinationBatchNoTextView,manufacturerTextView,vaccineNameTextView,
            usernameTextView,vaccinationStatusTextView,vaccineIDTextView;
    FirebaseFirestore db;
    LinearLayout administerVaccinationLinlay, confirmRejectVaccLinlay;
    RadioGroup cofirmAndRejectGroup;
    RadioButton radioButton;
    Button confirmChoiceBtn, administeredBtn;
    EditText remarksEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vaccination);

        db = FirebaseFirestore.getInstance();
        administerVaccinationLinlay = findViewById(R.id.administer_vaccination_linear_layout);
        confirmRejectVaccLinlay = findViewById(R.id.confirm_reject_vaccination_linear_layout);
        patientFullnameTextView = findViewById(R.id.patient_full_name_textView);
        patientIDTextView = findViewById(R.id.patient_id_textView);
        vaccinationBatchNoTextView = findViewById(R.id.vaccination_info_batch_number_textView);
        manufacturerTextView = findViewById(R.id.vaccination_manufacturer_name_textView);
        vaccineNameTextView = findViewById(R.id.vaccination_vaccine_name_textView);
        usernameTextView = findViewById(R.id.user_textView);
        vaccinationStatusTextView = findViewById(R.id.vaccination_status_textView);
        vaccineIDTextView = findViewById(R.id.vaccination_vaccineID_textView);

        batchNumber = getIntent().getStringExtra("BATCHNUMBER");
        vaccinationNumber = getIntent().getStringExtra("VACCINATIONNUMBER");
        DocumentReference docRef1 = db.collection("Batches").document(batchNumber);
        docRef1.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Batch batchInfo = documentSnapshot.toObject(Batch.class);
                        vaccinationBatchNoTextView.setText(batchInfo.batchNo);
                        vaccineIDTextView.setText(batchInfo.vaccineID);
                        DocumentReference docRef4 = db.collection("Vaccines")
                                .document(vaccineIDTextView.getText().toString());
                        docRef4.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Vaccine vaccine = documentSnapshot.toObject(Vaccine.class);
                                        manufacturerTextView.setText(vaccine.manufacturer);
                                        vaccineNameTextView.setText(vaccine.vaccineName);
                                    }
                                });
                    }
                });

        DocumentReference docRef2 = db.collection("Vaccination")
                .document(vaccinationNumber);
        docRef2.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Vaccination vaccination = documentSnapshot.toObject(Vaccination.class);
                        vaccinationStatusTextView.setText(vaccination.status);
                        String status = vaccination.status;
                        usernameTextView.setText(vaccination.username);
                        DocumentReference docRef3 = db.collection("Patients")
                                .document(usernameTextView.getText().toString());
                        docRef3.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Patient patient = documentSnapshot.toObject(Patient.class);
                                        patientFullnameTextView.setText(patient.patientName);
                                        patientIDTextView.setText(patient.patientID);
                                        if (status.equals("pending") ){
                                            administerVaccinationLinlay.setVisibility(View.GONE);
                                            confirmRejectVaccLinlay.setVisibility(View.VISIBLE);
                                        }
                                        else if (status.equals("confirmed")){
                                            administerVaccinationLinlay.setVisibility(View.VISIBLE);
                                            confirmRejectVaccLinlay.setVisibility(View.GONE);
                                        }
                                        else if (status.equals("rejected")){
                                            Toast.makeText(ManageVaccinationActivity.this, "Vaccination is rejected", Toast.LENGTH_SHORT).show();
                                            administerVaccinationLinlay.setVisibility(View.GONE);
                                            confirmRejectVaccLinlay.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast.makeText(ManageVaccinationActivity.this, "Vaccination is administered", Toast.LENGTH_SHORT).show();
                                            administerVaccinationLinlay.setVisibility(View.GONE);
                                            confirmRejectVaccLinlay.setVisibility(View.GONE);
                                        }
                                    }
                                });

                    }
                });

        confirmChoiceBtn = findViewById(R.id.confirm_choice_vaccination_status_button);
        cofirmAndRejectGroup = findViewById(R.id.confirm_reject_vaccination_radio_group);
        confirmChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = cofirmAndRejectGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedID);
                if (radioButton.getText().toString().equals("Confirm")){
                    DocumentReference vaccRef =  db.collection("Vaccination")
                            .document(vaccinationNumber);
                    vaccRef.update("status", "confirmed")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ManageVaccinationActivity.this, "Confirmation email has been sent", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ManageVaccinationActivity.this, "Can't update status", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    DocumentReference vaccRef =  db.collection("Vaccination")
                            .document(vaccinationNumber);
                    vaccRef.update("status", "rejected")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ManageVaccinationActivity.this, "Rejection email has been sent", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ManageVaccinationActivity.this, "Can't update status", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        remarksEditText = findViewById(R.id.remarks_vaccination_editText);
        administeredBtn = findViewById(R.id.administer_button);
        administeredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference vaccRef = db.collection("Vaccination")
                        .document(vaccinationNumber);
                vaccRef.update("status", "administered",
                        "remarks", remarksEditText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DocumentReference batchRef = db.collection("Batches")
                                        .document(batchNumber);
                                batchRef.get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Batch batch = documentSnapshot.toObject(Batch.class);
                                                DocumentReference batchUpdateRef =  db.collection("Batches")
                                                        .document(batchNumber);
                                                batchUpdateRef.update("quantityAdministered", batch.quantityAdministered + 1,
                                                        "quantityAvailable", batch.quantityAvailable - 1)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                finish();
                                                            }
                                                        });
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ManageVaccinationActivity.this, "Can't update status", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}