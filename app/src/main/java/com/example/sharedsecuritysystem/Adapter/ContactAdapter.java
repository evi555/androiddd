package com.example.sharedsecuritysystem.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.ui.Contact;
import com.example.sharedsecuritysystem.ui.UpdateContactActivity;
import com.example.sharedsecuritysystem.Response.ContactResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<ContactResponse> list;
    Context context;
    String userId;

    public ContactAdapter(ArrayList<ContactResponse> list, Context context, String userId) {
        this.context=context;
        this.list = list;
        this.userId=userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
                ContactResponse contact = list.get(position);
                holder.textViewName.setText(list.get(position).getName());
                holder.textViewPhone.setText(list.get(position).getPhone());
                holder.textViewEmail.setText(list.get(position).getEmail());

                if(position % 2 ==0)
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.orange));
                else
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.white));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactResponse contact = list.get(holder.getAdapterPosition());
                        Intent intent = new Intent(context, UpdateContactActivity.class);
                        intent.putExtra("Contacts", contact);
                        intent.putExtra("userId", userId);
                        context.startActivity(intent);
//                        Intent intent = new Intent(view.getContext(), UpdateContactActivity.class);
//                        intent.putExtra("Name", list.get(position).getName());
//                        intent.putExtra("Email", list.get(position).getEmail());
//                        intent.putExtra("Phone", list.get(position).getPhone());

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView textViewName, textViewPhone, textViewEmail;
         RelativeLayout relativelayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewContactListName);
            textViewEmail = itemView.findViewById(R.id.textViewContactListEmail);
            textViewPhone = itemView.findViewById(R.id.textViewContactListPhone);
            relativelayout = itemView.findViewById(R.id.rlContactItem);
        }
    }
}
