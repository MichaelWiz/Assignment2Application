package com.example.assignment2application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewVaccineBatchActivity extends AppCompatActivity {

    Spinner batchNumberSpinner;
    Spinner vaccinationNumberSpinner;
    RecyclerView vaccinationRecyclerView;
    FirebaseFirestore db;
    VaccinationAdapter vaccinationAdapter;
    ArrayList<Vaccination> vaccinationArrayList;
    ProgressDialog progressDialog;
    List<Batch> availableBatchArrayList;
    RecyclerView availableBatchRecyclerView;
    TextView centreName;
    BatchAdapter availableBatchAdapter;
    TextView selectedBatchNum, selectedVaccination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccine_batch);
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        availableBatchRecyclerView = findViewById(R.id.vaccine_batch_recyclerView);
        selectedBatchNum = findViewById(R.id.selected_batch_textView);
        selectedVaccination = findViewById(R.id.selected_vaccination_textView);

        centreName =  findViewById(R.id.healthcare_name_textView);



        vaccinationRecyclerView = findViewById(R.id.vaccination_list_recycleView);
        Button manageVaccinationBtn = findViewById(R.id.manage_vaccination_button);

        availableBatchArrayList =  new ArrayList<Batch>();
        availableBatchAdapter = new BatchAdapter(ViewVaccineBatchActivity.this, availableBatchArrayList);
        availableBatchRecyclerView.setAdapter(availableBatchAdapter);
        BatchListListener();




        batchNumberSpinner = findViewById(R.id.batch_number_spinner);
        List<String> batchNumberList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewVaccineBatchActivity.this, android.R.layout.simple_spinner_item, batchNumberList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchNumberSpinner.setAdapter(adapter);
        db.collection("Batches")
                .whereEqualTo("centreName", centreName.getText().toString())
//                .orderBy("batchNo",Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            batchNumberList.add("Select batch number");
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String batch = document.getString("batchNo");
                                batchNumberList.add(batch);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ViewVaccineBatchActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        Toast.makeText(MainActivity.this, batchNumberList.size(), Toast.LENGTH_SHORT).show();

        batchNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                String selectedBatch = batchNumberSpinner.getSelectedItem().toString();
                LinearLayout batchInfo = findViewById(R.id.linear_layout_for_batch_info);
                if (selectedBatch.equals("Select batch number") ){
                    batchInfo.setVisibility(View.GONE);
                }
                else {
                    batchInfo.setVisibility(View.VISIBLE);
                    vaccinationArrayList = new ArrayList<Vaccination>();
                    vaccinationAdapter = new VaccinationAdapter(ViewVaccineBatchActivity.this, vaccinationArrayList);
                    vaccinationRecyclerView.setAdapter(vaccinationAdapter);
                    VaccinationListListener(selectedBatch);

                    vaccinationNumberSpinner = findViewById(R.id.vaccination_number_spinner);
                    List<String> vaccinationNumberList = new ArrayList<>();
                    ArrayAdapter<String> vaccinationNumberAdapter = new ArrayAdapter<String>(ViewVaccineBatchActivity.this, android.R.layout.simple_spinner_item, vaccinationNumberList);
                    vaccinationNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    vaccinationNumberSpinner.setAdapter(vaccinationNumberAdapter);
                    try {
                        db.collection("Vaccination")
                                .whereEqualTo("batchNo", selectedBatch)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            vaccinationNumberList.add("Select vaccination number");
                                            for (QueryDocumentSnapshot document : task.getResult()){
                                                String vaccination = document.getString("vaccinationID");
                                                vaccinationNumberList.add(vaccination);
                                            }
                                            vaccinationNumberAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });

                        TextView expiryDateTextView = findViewById(R.id.batch_expiry_date_textView);
                        TextView quantityAvailableTextView = findViewById(R.id.batch_quantity_available_textView);
                        TextView administeredQuantityTextView = findViewById(R.id.batch_administered_quantity_textView);
                        TextView pendingQuantityTextView = findViewById(R.id.batch_quantity_pending_textView);

                        DocumentReference docRef = db.collection("Batches").document(selectedBatch);
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Batch currentBatchInfo = documentSnapshot.toObject(Batch.class);
                                selectedBatchNum.setText(currentBatchInfo.batchNo);
                                expiryDateTextView.setText(currentBatchInfo.expiryDate);
                                quantityAvailableTextView.setText(String.valueOf(currentBatchInfo.quantityAvailable));
                                administeredQuantityTextView.setText(String.valueOf(currentBatchInfo.quantityAdministered));

                            }
                        });
                        ArrayList<String> listOfPendingVaccination = new ArrayList<>();

                        db.collection( "Vaccination")
                                .whereEqualTo("batchNo", selectedBatch)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot document: task.getResult()){
                                                String vaccination = document.getString("vaccinationID");
                                                listOfPendingVaccination.add(vaccination);
                                            }
                                            pendingQuantityTextView.setText(String.valueOf(listOfPendingVaccination.size()));
                                        }
                                    }
                                });

                        try {
                            vaccinationNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                                    String selectedVaccinationNo = vaccinationNumberSpinner.getSelectedItem().toString();
                                    if (selectedVaccinationNo == "Select vaccination number"){
                                        manageVaccinationBtn.setEnabled(false);
                                    }
                                    else{
                                        manageVaccinationBtn.setEnabled(true);
                                        DocumentReference docRef2 = db.collection("Vaccination").document(selectedVaccinationNo);
                                        docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Vaccination selectedVaccinationNum = documentSnapshot.toObject(Vaccination.class);
                                                selectedVaccination.setText(selectedVaccinationNum.vaccinationID);

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(ViewVaccineBatchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(ViewVaccineBatchActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        manageVaccinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageVaccinationIntent = new Intent(ViewVaccineBatchActivity.this, ManageVaccinationActivity.class);
                manageVaccinationIntent.putExtra("BATCHNUMBER",selectedBatchNum.getText().toString());
                manageVaccinationIntent.putExtra("VACCINATIONNUMBER", selectedVaccination.getText().toString());
                startActivity(manageVaccinationIntent);
            }
        });

        try {

        }catch (Exception e){
            Toast.makeText(ViewVaccineBatchActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }

    private void BatchListListener() {
        db.collection("Batches")
                .whereEqualTo("centreName", centreName.getText().toString())
                .orderBy("batchNo", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                try {
                                    availableBatchArrayList.add(dc.getDocument().toObject(Batch.class));
                                }catch (Exception e){
                                    Toast.makeText(ViewVaccineBatchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            availableBatchAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void VaccinationListListener(String batchNo) {
        db.collection("Vaccination")
                .whereEqualTo("batchNo", batchNo)
                .orderBy("vaccinationID", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                vaccinationArrayList.add(dc.getDocument().toObject(Vaccination.class));
                            }
                            vaccinationAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                    }
                });
    }
}