package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class signupAdmin extends AppCompatActivity {

    Button btnBack;
    EditText editTextAdminName;
    EditText editTextAdminCentreName;
    EditText editTextAdminUsername;
    EditText editTextAdminPassword;
    EditText editTextAdminConfirmPassword;
    EditText editTextAdminEmail;
    Button btnSubmit;
    FirebaseFirestore db;

    private Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);
        editTextAdminConfirmPassword = findViewById(R.id.edit_text_admin_signup_confirm_password);
        btnBack = findViewById(R.id.btn_back_admin_signup);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(signupAdmin.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        initializeView();

        editTextAdminName.addTextChangedListener(signupTextWatcher);
        editTextAdminCentreName.addTextChangedListener(signupTextWatcher);
        editTextAdminUsername.addTextChangedListener(signupTextWatcher);
        editTextAdminPassword.addTextChangedListener(signupTextWatcher);
        editTextAdminConfirmPassword.addTextChangedListener(signupTextWatcher);
        editTextAdminEmail.addTextChangedListener(signupTextWatcher);

        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener((v)->{


            String adminName = editTextAdminName.getText().toString();
            String adminCentre = editTextAdminCentreName.getText().toString();
            String adminUsername = editTextAdminUsername.getText().toString();
            String adminPassword = editTextAdminPassword.getText().toString();
            String adminEmail = editTextAdminEmail.getText().toString();

            admin = new Admin(adminName, adminCentre, adminUsername, adminPassword, adminEmail);
            uploadAdmin(admin);
        });

    }


    private TextWatcher signupTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String adminNameInput = editTextAdminName.getText().toString().trim();
            String adminCentreInput = editTextAdminCentreName.getText().toString().trim();
            String adminUsernameInput = editTextAdminUsername.getText().toString().trim();
            String adminPasswordInput = editTextAdminPassword.getText().toString().trim();
            String adminConfirmPasswordInput = editTextAdminConfirmPassword.getText().toString().trim();
            String adminEmailInput = editTextAdminEmail.getText().toString().trim();

            btnSubmit.setEnabled(!adminNameInput.isEmpty() && !adminCentreInput.isEmpty()
                    && !adminUsernameInput.isEmpty() && !adminPasswordInput.isEmpty()
                    && !adminConfirmPasswordInput.isEmpty() && !adminEmailInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void uploadAdmin(Admin admin){
        db.collection("Admins")
                .document(editTextAdminUsername.getText().toString())
                .set(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loginPage.USER_ID = admin.getAdminID();
                        Toast.makeText(signupAdmin.this, "Signup Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signupAdmin.this, signupAdmin.class));
                        finish();
                    }
                })
                .addOnFailureListener((e) ->{
                    Toast.makeText(signupAdmin.this, "Signup Fail", Toast.LENGTH_SHORT).show();
                });
    }

    public void initializeView(){
        editTextAdminName = findViewById(R.id.edit_text_admin_signup_name);
        editTextAdminCentreName = findViewById(R.id.edit_text_admin_signup_centre);
        editTextAdminUsername = findViewById(R.id.edit_text_admin_signup_username);
        editTextAdminPassword = findViewById(R.id.edit_text_admin_signup_password);
        editTextAdminEmail = findViewById(R.id.edit_text_admin_signup_email);
        btnSubmit = findViewById(R.id.btn_submit_admin_signup);
    }

}