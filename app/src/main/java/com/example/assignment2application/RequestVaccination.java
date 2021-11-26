package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RequestVaccination extends AppCompatActivity {
    public static Vaccination VACCINATION;
    Spinner spinnerVaccine;
    Spinner spinnerVaccineBatch;
    Spinner spinnerCentre;
    Button btnLogout;
    EditText datePicker;
    ImageButton calendarBtn;
    Button btnSubmit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    android.app.DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vaccination);

        spinnerVaccine = findViewById(R.id.spinner_request_vaccine);
        spinnerCentre = findViewById(R.id.spinner_request_centre);
        spinnerVaccineBatch = findViewById(R.id.spinner_request_vaccine_batch);



        datePicker = findViewById(R.id.text_view_select_date);
        btnLogout = findViewById(R.id.btn_request_logout);
        calendarBtn = findViewById(R.id.image_btn_calendar);
        btnSubmit= findViewById(R.id.btn_request_submit);
        openDialog();
        enableSubmitButton();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogout = new Intent(RequestVaccination.this, MainActivity.class);
                startActivity(intentLogout);
            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestVaccination.this,
                        (view, year1, month1, dayOfMonth1) -> datePicker.setText(dayOfMonth1 + "/" + month1 + "/" + year1), year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });






    }




    private void enableSubmitButton(){
        spinnerVaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String vaccineSelection = spinnerVaccine.getSelectedItem().toString();
                if (vaccineSelection.equals("Select Vaccine")){
                    btnSubmit.setEnabled(false);
                }else {
                    spinnerCentre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String centreSelection = spinnerCentre.getSelectedItem().toString();
                            if (centreSelection.equals("Select Healthcare Centre")){
                                btnSubmit.setEnabled(false);
                            }else {
                                spinnerVaccineBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String vaccineBatchSelection = spinnerVaccineBatch.getSelectedItem().toString();
                                        if (vaccineBatchSelection.equals("Select Vaccine Batch")){
                                            btnSubmit.setEnabled(false);
                                        }else {
                                            datePicker.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {
                                                    String date = datePicker.getText().toString().trim();
                                                    if (!date.equals("dd/mm/yyyy")){
                                                        btnSubmit.setEnabled(true);
                                                    }
                                                }
                                            });


                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }


    private void openDialog() {
        List<String> vaccineList = new ArrayList<>();
        ArrayAdapter adapterVaccine = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, vaccineList);
        adapterVaccine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccine.setAdapter(adapterVaccine);
        db.collection("Vaccines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            vaccineList.add("Select Vaccine");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String vaccineInfo = document.getString("vaccineID") + " ( " + document.getString("manufacturer") + " ) ";
                                vaccineList.add(vaccineInfo);
                            }
                            adapterVaccine.notifyDataSetChanged();
                        }
                    }
                });

        List<String> centreList = new ArrayList<>();
        ArrayAdapter adapterCentre = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, centreList);
        adapterCentre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentre.setAdapter(adapterCentre);
        db.collection("HealthcareCentres")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            centreList.add("Select Healthcare Centre");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String centreInfo = document.getString("centreName") + " ( " + document.getString("address") + " )";

                                centreList.add(centreInfo);

                            }
                            adapterCentre.notifyDataSetChanged();
                        }
                    }
                });

        List<String> batchList =  new ArrayList<>();
        ArrayAdapter adapterBatch = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, batchList);
        adapterBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccineBatch.setAdapter(adapterBatch);
        db.collection("Batches")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            batchList.add("Select Vaccine Batch");
                            for (QueryDocumentSnapshot document :task.getResult()){
                                String batchInfo = document.getString("batchNo") + "(Expiry Date "
                                        + document.getString("expiryDate") + " )";


                                batchList.add(batchInfo);

                            }
                            adapterBatch.notifyDataSetChanged();
                        }
                    }
                });
    }




    public void submitRequestBtnClick(View view){
        Vaccination vaccination = new Vaccination();
        vaccination.setVaccinationID(UUID.randomUUID().toString());
        vaccination.setVaccineType(spinnerVaccine.getSelectedItem().toString());
        vaccination.setCentre(spinnerCentre.getSelectedItem().toString());
        vaccination.setVaccineBatch(spinnerVaccineBatch.getSelectedItem().toString());
        vaccination.setAppointmentDate(datePicker.getText().toString());
        vaccination.setStatus("Pending");
        vaccination.setRemark("");

        db.collection("Vaccination")
                .document()
                .set(vaccination)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        RequestVaccination.VACCINATION = vaccination;
                        startActivity(new Intent(RequestVaccination.this, RequestVaccination.class));
                        Toast.makeText(RequestVaccination.this, "Request Vaccination Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RequestVaccination.this, "Request Vaccination Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}