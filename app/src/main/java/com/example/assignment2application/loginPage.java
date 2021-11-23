package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginPage extends AppCompatActivity {
    EditText editTextLoginUsername;
    EditText editTextLoginPassword;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initializeView();
        btnBack = findViewById(R.id.btn_back_login);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(loginPage.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }


    public void initializeView(){
        editTextLoginPassword = findViewById(R.id.edit_text_login_username);
        editTextLoginPassword = findViewById(R.id.edit_text_login_password);
    }

}
