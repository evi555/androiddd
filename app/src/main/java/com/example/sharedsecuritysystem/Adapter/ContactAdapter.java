package com.example.sharedsecuritysystem.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.ui.UpdateContactActivity;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<ContactResponse> list;
    Context context;
    String userId;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public ContactAdapter(ArrayList<ContactResponse> list, Context context, String userId) {
        this.context=context;
        this.list = list;
        this.userId=userId;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
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


                if(position % 2 ==0) {
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.orange));
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                }else {
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.white));
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                   }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactResponse contact = list.get(holder.getAdapterPosition());
                        Intent intent = new Intent(context, UpdateContactActivity.class);
                        intent.putExtra("Contacts", contact);
                        intent.putExtra("userId", userId);
                        context.startActivity(intent);
                    }
                });

            holder.itemView.findViewById(R.id.imageViewContactListDelete).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("Users").document(mUser.getUid()).
                        collection("Contacts").
                        document(contact.getUid()).delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    list.remove(holder.getAdapterPosition());  // remove the item from list
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    Toast.makeText(context,"item deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "item not deleted", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
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
         ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewContactListName);
            textViewEmail = itemView.findViewById(R.id.textViewContactListEmail);
            textViewPhone = itemView.findViewById(R.id.textViewContactListPhone);
            relativelayout = itemView.findViewById(R.id.rlContactItem);
            imageView = itemView.findViewById(R.id.imageViewContactListDelete);
        }
    }
}
