package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox checkBoxShowPassword;
    List<Admin> adminArray = new ArrayList<Admin>();
    List<Patient> patientArray = new ArrayList<Patient>();
    public static String USER_ID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initializeView();
        getLoginInfo();


        checkBoxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                for (Patient p: patientArray){
                    if (p.getPatientUsername().equalsIgnoreCase(editTextLoginUsername.getText().toString())
                            && p.getPatientPassword().equalsIgnoreCase(editTextLoginPassword.getText().toString())){
                        startActivity(new Intent(loginPage.this, RequestVaccination.class));
                        finish();
                        break;
                    }else {
                        Toast.makeText(loginPage.this, "Check Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                for (Admin a: adminArray){
                    if (a.getAdminUsername().equalsIgnoreCase(editTextLoginUsername.getText().toString())
                            && a.getAdminPassword().equalsIgnoreCase(editTextLoginPassword.getText().toString())){
                        startActivity(new Intent(loginPage.this, adminMenu.class));
                        finish();
                        break;
                    }else {
                        Toast.makeText(loginPage.this, "Check Username or Password", Toast.LENGTH_SHORT).show();
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
        db.collection("Admins").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot snapshot : documentSnapshots){
                    adminArray.add(new Admin(snapshot.getString("adminName"),snapshot.getString("adminID")
                            ,snapshot.getString("adminUsername"),snapshot.getString("adminPassword"),snapshot.getString("adminEmail")));
                }
            }
        });
    }



    public void initializeView(){
        editTextLoginUsername = findViewById(R.id.edit_text_login_username);
        editTextLoginPassword = findViewById(R.id.edit_text_login_password);
        checkBoxShowPassword = findViewById(R.id.checkbox_show_password);
        btnLogin = findViewById(R.id.btn_login);
        btnBack = findViewById(R.id.btn_back_login);
    }

}
