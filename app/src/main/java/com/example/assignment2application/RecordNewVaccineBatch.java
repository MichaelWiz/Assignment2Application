package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecordNewVaccineBatch extends AppCompatActivity {
    TextView textViewCenter;
    EditText editTextVaccineID;
    EditText editTextBatchNumber;
    EditText editTextQuantity;
    EditText dateTextView;
    List<Vaccine> vaccinesArray;
    ListView listViewVaccine;
    ImageButton imageButtonCalender;
    ListAdapter adapter;
    Button addBtn;
    Batch batch;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_vaccine_batch);
        dateTextView = findViewById(R.id.edit_text_expiry_date_record);
        textViewCenter = findViewById(R.id.text_view_healthcareCentre);
        listViewVaccine = findViewById(R.id.list_view_vaccine);
        editTextVaccineID = findViewById(R.id.edit_text_vaccine_id_record);
        editTextBatchNumber = findViewById(R.id.edit_text_vaccine_batch_number_record);
        editTextQuantity = findViewById(R.id.edit_text_quantity_record);
        imageButtonCalender = findViewById(R.id.image_button_calendar_icon);
        /*databaseReference= firebaseDatabase.getReference("Vaccines");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                vaccinesArray.add(value);
                adapter = new ArrayAdapter<String>(RecordNewVaccineBatch.this, android.R.layout.simple_list_item_1, vaccinesArray);
                listViewVaccine.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        imageButtonCalender.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RecordNewVaccineBatch.this,
                    (view, year1, month1, dayOfMonth1) -> dateTextView.setText(dayOfMonth1 + "/" + month1 + "/" + year1), year, month, dayOfMonth);
            datePickerDialog.show();
        });

        batch = (Batch) getIntent().getSerializableExtra("batch");

        if (batch != null) {
            editTextVaccineID.setText(batch.getVaccineID());
            editTextBatchNumber.setText(batch.getBatchNo());
            dateTextView.setText(batch.getExpiryDate());
            editTextQuantity.setText(batch.getQuantityAvailable());
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextVaccineID.getText().toString().isEmpty()) {
                    if (batch == null) {
                        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                        DocumentReference newVacRef = db.collection("Batch").document();
                        batch = new Batch(editTextVaccineID.getText().toString(),
                                dateTextView.getText().toString(),quantity,
                                editTextVaccineID.getText().toString());
                        db.collection("Batch")
                                .document(newVacRef.getId())
                                .set(batch)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RecordNewVaccineBatch.this,
                                                e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        DocumentReference vacRef = db.collection("Batch")
                                .document(batch.getBatchNo());
                        vacRef.update("batchNumber", editTextBatchNumber.getText().toString(),
                                "vaccineID", editTextVaccineID.getText().toString(),
                                "expiryDate",dateTextView.getText().toString(),"quantity",editTextQuantity.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RecordNewVaccineBatch.this,
                                                e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

    }

    private void getCurrentUserNotes () {
        db.collection("Vaccines")
                .whereEqualTo("userId", "vCuBHXckNMQGwNVTqwwi7IU5sBz2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            vaccinesArray = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Vaccine vaccine = document.toObject(Vaccine.class);
                                vaccinesArray.add(vaccine);
                            }
                            adapter = new ArrayAdapter<Vaccine>(RecordNewVaccineBatch.this,
                                    android.R.layout.simple_list_item_1, vaccinesArray);
                            listViewVaccine.setAdapter(adapter);
                        }
                    }
                });
    }
}