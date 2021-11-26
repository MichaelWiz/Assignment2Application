package com.example.assignment2application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VaccinationAdapter extends RecyclerView.Adapter<VaccinationAdapter.VaccinationViewHolder> {

    Context context;
    ArrayList<Vaccination> vaccinationArrayList;

    public VaccinationAdapter(Context context, ArrayList<Vaccination> vaccinationArrayList){
        this.context = context;
        this.vaccinationArrayList = vaccinationArrayList;
    }

    @NonNull
    @Override
    public VaccinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vaccination, parent,false);
        return new VaccinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationViewHolder holder, int position) {
        Vaccination vaccination = vaccinationArrayList.get(position);
        holder.vaccinationID.setText(vaccination.VaccinationID);
        holder.vaccinationStatus.setText(vaccination.Status);
        holder.vaccinationDate.setText(vaccination.AppointmentDate);
    }

    @Override
    public int getItemCount() {
        return vaccinationArrayList.size();
    }

    public class VaccinationViewHolder extends RecyclerView.ViewHolder {

        TextView vaccinationDate, vaccinationStatus, vaccinationID;

        public VaccinationViewHolder(@NonNull View itemView) {
            super(itemView);

            vaccinationID = itemView.findViewById(R.id.vaccination_id_textView);
            vaccinationDate = itemView.findViewById(R.id.appointment_date_textView);
            vaccinationStatus = itemView.findViewById(R.id.vaccination_status_textView);
        }
    }
}
