package com.example.sharedsecuritysystem.contact_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedsecuritysystem.ContactListActivity;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.UpdateContactActivity;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<ContactResponse> list;
    Context context;
    public ContactAdapter(ArrayList<ContactResponse> list,Context context) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewName.setText(list.get(position).getName());
        holder.textViewPhone.setText(list.get(position).getPhone());
        holder.textViewEmail.setText(list.get(position).getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateContactActivity.class);
                intent.putExtra("Name", list.get(position).getName());
                intent.putExtra("Email", list.get(position).getEmail());
                intent.putExtra("Phone", list.get(position).getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView textViewName, textViewPhone, textViewEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textviewName);
            textViewEmail = itemView.findViewById(R.id.textviewEmail);
            textViewPhone = itemView.findViewById(R.id.textviewPhone);
        }
    }
}
