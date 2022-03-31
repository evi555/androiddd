package com.example.sharedsecuritysystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.HistoryResponse;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<HistoryResponse> list;
    Context context;

    public HistoryAdapter(ArrayList<HistoryResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.textViewAlert.setText(list.get(position).getAlert());
        holder.textViewDate.setText(list.get(position).getDate());
        holder.textViewTime.setText(list.get(position).getTime());
        holder.textViewNote.setText(list.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewAlert, textViewDate, textViewTime, textViewNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewHistoryItem);
            textViewAlert = itemView.findViewById(R.id.textViewHistoryAlert);
            textViewDate = itemView.findViewById(R.id.textViewHistoryDate);
            textViewTime = itemView.findViewById(R.id.textViewHistoryTime);
            textViewNote = itemView.findViewById(R.id.textViewHistoryNote);
        }
    }
}
