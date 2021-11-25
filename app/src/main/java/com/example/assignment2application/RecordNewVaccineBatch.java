package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    List<String> vaccinesArray = new ArrayList<>();
    ListView listViewVaccine;
    ImageButton imageButtonCalender;
    Button addBtn;
    Button backBtn;
    Batch batch;

    static Admin ADMIN;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        addBtn = findViewById(R.id.btn_add_record);
        backBtn = findViewById(R.id.btn_back_record);
        imageButtonCalender = findViewById(R.id.image_button_calendar_icon);

        getCurrentVaccine ();
        //getAdmin();

        //textViewCenter.setText(admin.getHealthcareName());
        imageButtonCalender.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RecordNewVaccineBatch.this,
                    (view, year1, month1, dayOfMonth1) -> dateTextView.setText(dayOfMonth1 + "/" + month1 + "/" + year1), year, month, dayOfMonth);
            datePickerDialog.show();
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordNewVaccineBatch.this, AddNewCenter.class));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextVaccineID.getText().toString().isEmpty()&& !editTextBatchNumber.getText().toString().isEmpty()&&
                        !editTextQuantity.getText().toString().isEmpty()&&!dateTextView.getText().toString().isEmpty()) {
                    int quantity = Integer.parseInt(editTextQuantity.getText().toString());

                    String batchNumber = editTextBatchNumber.getText().toString();
                    String date = dateTextView.getText().toString();
                    int qty = quantity;
                    String vaccineId = editTextVaccineID.getText().toString();

                    for(String v:vaccinesArray) {
                        if(v.equalsIgnoreCase(vaccineId)) {
                            batch = new Batch(batchNumber, date, qty, vaccineId,ADMIN.getAdminName());

                            db.collection("Batches")
                                    .document(editTextBatchNumber.getText().toString())
                                    .set(batch)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RecordNewVaccineBatch.this,
                                                    "Added Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RecordNewVaccineBatch.this, AddNewCenter.class));
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
                            break;
                        }
                        else {
                            editTextVaccineID.setText("");
                            Toast.makeText(RecordNewVaccineBatch.this,
                                    "Invalid vaccine ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(RecordNewVaccineBatch.this,
                            "Please fill in all text", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getCurrentVaccine () {
        db.collection("Vaccines").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentSnapshot snapshot : documentSnapshots)
                {
                    vaccinesArray.add(snapshot.getString("vaccineID"));

                }
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext()
                        , android.R.layout.simple_selectable_list_item,vaccinesArray);
                adapter.notifyDataSetChanged();
                listViewVaccine.setAdapter(adapter);
            }
        });
    }

    /*private void getAdmin () {
        db.collection("Admins")
                .whereEqualTo("adminID", loginPage.USER_ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Admin admin = documentSnapshot.toObject(Admin.class);
                            ADMIN = admin;
                            finish();
                            break;
                        }
                    }
              });
   }*/

}