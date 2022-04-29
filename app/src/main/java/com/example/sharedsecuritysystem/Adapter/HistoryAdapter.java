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
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<HistoryModel> list;
    Context context;

    public HistoryAdapter(ArrayList<HistoryModel> list, Context context) {
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

        HistoryModel historyModel = list.get(position);
       // holder.imageView.setImageResource(historyModel.ge);
        holder.textViewAlert.setText(historyModel.getTitle());
        holder.textViewNote.setText(historyModel.getMessage());

        //  holder.textViewDate.setText(list.get(position).getDate());
       // holder.textViewTime.setText(list.get(position).getTime());

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
            //imageView = itemView.findViewById(R.id.imageViewHistoryItem);
            textViewAlert = itemView.findViewById(R.id.textViewHistoryAlert);
            textViewDate = itemView.findViewById(R.id.textViewHistoryDate);
            textViewTime = itemView.findViewById(R.id.textViewHistoryTime);
            textViewNote = itemView.findViewById(R.id.textViewHistoryNote);
        }
    }
}
