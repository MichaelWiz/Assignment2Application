package com.example.assignment2application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnAdmin;
    Button btnPatient;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdmin = findViewById(R.id.button_admin);
        btnPatient = findViewById(R.id.button_patient);
        textViewLogin= findViewById(R.id.text_view_login);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdmin = new Intent(MainActivity.this, signupAdmin.class);
                startActivity(intentAdmin);
            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPatient = new Intent(MainActivity.this, signupPatient.class);
                startActivity(intentPatient);
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(MainActivity.this, loginPage.class);
                startActivity(intentLogin);
            }
        });

    }




}