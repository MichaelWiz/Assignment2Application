package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class loginPage extends AppCompatActivity {

    public static Patient PATIENT;
    public static Admin ADMIN;

    EditText editTextLoginUsername;
    EditText editTextLoginPassword;
    Button btnLogin;
    Button btnBack;
    List<Patient> patientArray = new ArrayList<Patient>();
    public static String USER_ID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initializeView();
        getLoginInfo();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Patient p: patientArray){
                    if (p.getPatientUsername().equalsIgnoreCase(editTextLoginUsername.getText().toString())
                            && p.getPatientPassword().equalsIgnoreCase(editTextLoginPassword.getText().toString())){
                        startActivity(new Intent(loginPage.this, RequestVaccination.class));
                        finish();
                        break;
                    }else{
                        Toast.makeText(loginPage.this, "Wrong Username or Password!",Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(loginPage.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    private void getLoginInfo(){
        db.collection("Patients").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot snapshot : documentSnapshots){
                    patientArray.add(new Patient(snapshot.getString("patientName"),snapshot.getString("patientID")
                            ,snapshot.getString("patientUsername"),snapshot.getString("patientPassword"),snapshot.getString("patientEmail")));
                }
            }
        });
    }



    public void initializeView(){
        editTextLoginUsername = findViewById(R.id.edit_text_login_username);
        editTextLoginPassword = findViewById(R.id.edit_text_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnBack = findViewById(R.id.btn_back_login);
    }

}
