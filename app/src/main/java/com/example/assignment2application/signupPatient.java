package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class signupPatient extends AppCompatActivity {

    public static Patient PATIENT;

    EditText editTextPatientName;
    EditText editTextPatientID;
    EditText editTextPatientUsername;
    EditText editTextPatientPassword;
    EditText editTextPatientConfirmPassword;
    EditText editTextPatientEmail;
    Button btnSubmit;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_patient);
        editTextPatientConfirmPassword = findViewById(R.id.edit_text_patient_signup_confirm_password);

        initializeView();

        editTextPatientName.addTextChangedListener(signupTextWatcher);
        editTextPatientID.addTextChangedListener(signupTextWatcher);
        editTextPatientUsername.addTextChangedListener(signupTextWatcher);
        editTextPatientPassword.addTextChangedListener(signupTextWatcher);
        editTextPatientConfirmPassword.addTextChangedListener(signupTextWatcher);
        editTextPatientEmail.addTextChangedListener(signupTextWatcher);

        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener((v)-> {
            String patientName = editTextPatientName.getText().toString();
            String patientID = editTextPatientID.getText().toString();
            String patientUsername = editTextPatientUsername.getText().toString();
            String patientPassword = editTextPatientPassword.getText().toString();
            String patientEmail = editTextPatientEmail.getText().toString();




            Patient patient = new Patient(patientName, patientID, patientUsername, patientPassword, patientEmail);
            PATIENT = patient;
            uploadPatient(patient);
        });
    }



    private TextWatcher signupTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String patientNameInput = editTextPatientName.getText().toString().trim();
            String patientIDInput = editTextPatientID.getText().toString().trim();
            String patientUsernameInput = editTextPatientUsername.getText().toString().trim();
            String patientPasswordInput = editTextPatientPassword.getText().toString().trim();
            String patientConfirmPasswordInput = editTextPatientConfirmPassword.getText().toString().trim();
            String patientEmailInput = editTextPatientEmail.getText().toString().trim();
            if (patientConfirmPasswordInput.equals(patientPasswordInput)){
                btnSubmit.setEnabled(!patientNameInput.isEmpty() && !patientIDInput.isEmpty()
                        && !patientUsernameInput.isEmpty() && !patientPasswordInput.isEmpty()
                        && !patientConfirmPasswordInput.isEmpty() && !patientEmailInput.isEmpty());
            }




        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void uploadPatient(Patient patient){
        db.collection(Patient.COLLECTION_NAME)
                .document(editTextPatientUsername.getText().toString())
                .set(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loginPage.USER_ID = editTextPatientID.getText().toString();
                        loginPage.PATIENT= patient;
                        Toast.makeText(signupPatient.this, "Signup Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signupPatient.this, RequestVaccination.class));
                        finish();
                    }
                })
                .addOnFailureListener((e) ->{
                    Toast.makeText(signupPatient.this, "Signup Fail",Toast.LENGTH_SHORT).show();
                });
    }


    public void initializeView(){

        editTextPatientName = findViewById(R.id.edit_text_patient_signup_name);
        editTextPatientID = findViewById(R.id.edit_text_patient_signup_id);
        editTextPatientUsername = findViewById(R.id.edit_text_patient_signup_username);
        editTextPatientPassword = findViewById(R.id.edit_text_patient_signup_password);
        editTextPatientEmail = findViewById(R.id.edit_text_patient_signup_email);
        btnSubmit = findViewById(R.id.btn_submit_patient_signup);

    }

}