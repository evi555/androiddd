package com.example.sharedsecuritysystem.contact_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedsecuritysystem.R;

import java.util.ArrayList;

public class SystemConnectedAdapter extends RecyclerView.Adapter<SystemConnectedAdapter.ViewHolder> {
    ArrayList<SystemConnectedResponse> list;
    Context context;
    public SystemConnectedAdapter(ArrayList<SystemConnectedResponse> list, Context context){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SystemConnectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.system_connected_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SystemConnectedAdapter.ViewHolder holder, int position) {
    holder.textViewName.setText(list.get(position).getName());
    holder.textViewEmail.setText(list.get(position).getEmail());
    holder.textViewphone.setText(list.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail, textViewphone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textviewsystemName);
            textViewEmail = itemView.findViewById(R.id.textviewsystemEmail);
            textViewphone = itemView.findViewById(R.id.textviewsystemPhone);
        }
    }
}
