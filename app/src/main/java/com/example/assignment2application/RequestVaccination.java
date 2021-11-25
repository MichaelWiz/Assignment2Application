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
    //List<Vaccine>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_vaccination);

        spinnerVaccine = findViewById(R.id.spinner_request_vaccine);
        ArrayAdapter adapterVaccine = ArrayAdapter.createFromResource(this,
                R.array.vaccines, android.R.layout.simple_spinner_item);
        adapterVaccine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccine.setAdapter(adapterVaccine);

        spinnerCentre = findViewById(R.id.spinner_request_centre);
        ArrayAdapter adapterCentre = ArrayAdapter.createFromResource(this,
                R.array.centres, android.R.layout.simple_spinner_item);
        adapterCentre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentre.setAdapter(adapterCentre);

        spinnerVaccineBatch = findViewById(R.id.spinner_request_vaccine_batch);
        ArrayAdapter adapterVaccineBatch = ArrayAdapter.createFromResource(this,
                R.array.vaccineBatch, android.R.layout.simple_spinner_item);
        adapterVaccineBatch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccineBatch.setAdapter(adapterVaccineBatch);


        datePicker = findViewById(R.id.text_view_select_date);
        btnLogout = findViewById(R.id.btn_request_logout);
        calendarBtn = findViewById(R.id.image_btn_calendar);
        btnSubmit= findViewById(R.id.btn_request_submit);
        openDialog();
        //enabledSubmitButton();
//        String appointmentDate = datePicker.getText().toString();
//        String vaccineSelection = spinnerVaccine.getSelectedItem().toString();
//        String vaccineBatchSelection = spinnerVaccineBatch.getSelectedItem().toString();
//        String centreSelection = spinnerCentre.getSelectedItem().toString();
//        if (!appointmentDate.equals("dd/mm/yyyy") && !vaccineSelection.equals("-")
//                && !vaccineBatchSelection.equals("-") && !centreSelection.equals("-")){
//            btnSubmit.setEnabled(true);
//        }


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

    private void openDialog(){
        List<String> vaccineList =  new ArrayList<>();//Arrays.asList("Pfizer", "Sinovac");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, vaccineList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccine.setAdapter(adapter);
        db.collection("Vaccines")
                .whereEqualTo("vaccineID", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            vaccineList.add("Select Vaccine Type");
                            for (QueryDocumentSnapshot document :task.getResult()){
                                String vaccine = document.getString("vaccineID");
                                vaccineList.add(vaccine);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        spinnerVaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectVaccine = adapterView.getItemAtPosition(i).toString();
                //LinearLayout batchInfo = findViewById(R.id.linear_layout_vaccine_list);
                if (spinnerVaccine.getSelectedItem() != "Select Vaccine "){
                    btnSubmit.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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