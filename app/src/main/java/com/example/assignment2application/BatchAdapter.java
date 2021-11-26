package com.example.assignment2application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.BatchViewHolder> {

    Context context;
    List<Batch> batchArrayList;

    public BatchAdapter(Context context, List<Batch> batchArrayList){
        this.context = context;
        this.batchArrayList = batchArrayList;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_batch, parent,false);
        return new BatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchAdapter.BatchViewHolder holder, int position) {
        Batch batch = batchArrayList.get(position);
        holder.batchNum.setText(batch.batchNo);
    }

    @Override
    public int getItemCount() {
        return batchArrayList.size();
    }

    public static class BatchViewHolder extends RecyclerView.ViewHolder {

        TextView batchNum;

        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            batchNum = itemView.findViewById(R.id.batch_id_textView);
        }
    }
}
