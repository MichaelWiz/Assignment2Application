package com.example.assignment2application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class signupAdmin extends AppCompatActivity {

    public static Admin ADMIN;

    Button btnBack;
    EditText editTextAdminName;
    EditText editTextAdminCentreName;
    EditText editTextAdminUsername;
    EditText editTextAdminPassword;
    EditText editTextAdminConfirmPassword;
    EditText editTextAdminEmail;
    ListView listViewCentreList;
    Button btnSubmit;
    FirebaseFirestore db;

    List<String> centreList = new ArrayList<>();

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
        viewCentreList();

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
            ADMIN = admin;
            uploadAdmin(admin);
        });

    }

    private void viewCentreList(){
        db.collection("HealthcareCentres").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot snapshot: documentSnapshots){
                    centreList.add(snapshot.getString("centerName"));
                }
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext()
                        , android.R.layout.simple_selectable_list_item, centreList);
                adapter.notifyDataSetChanged();
                listViewCentreList.setAdapter(adapter);

            }
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

            if (adminConfirmPasswordInput.equals(adminPasswordInput)){
                btnSubmit.setEnabled(!adminNameInput.isEmpty() && !adminCentreInput.isEmpty()
                        && !adminUsernameInput.isEmpty() && !adminPasswordInput.isEmpty()
                        && !adminConfirmPasswordInput.isEmpty() && !adminEmailInput.isEmpty());
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void uploadAdmin(Admin admin){
        db.collection(Admin.COLLECTION_NAME)
                .document(editTextAdminUsername.getText().toString())
                .set(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loginPage.USER_ID = admin.getAdminID();
                        loginPage.ADMIN = admin;
                        Toast.makeText(signupAdmin.this, "Signup Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signupAdmin.this, adminMenu.class));
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
        listViewCentreList = findViewById(R.id.list_view_centre_list);
        btnSubmit = findViewById(R.id.btn_submit_admin_signup);
    }

}