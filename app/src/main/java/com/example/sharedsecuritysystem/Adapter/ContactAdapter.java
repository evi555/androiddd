package com.example.sharedsecuritysystem.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactModel;
import com.example.sharedsecuritysystem.ui.UpdateContactActivity;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<ContactModel> list;
    Context context;
    String userId;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    public ContactAdapter(ArrayList<ContactModel> list, Context context) {
        this.context=context;
        this.list = list;
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
                ContactModel contact = list.get(position);

        Log.e("Clicked item","Selected Item : "+contact.getContactEmail());

                holder.textViewName.setText(contact.getContactName());
                holder.textViewPhone.setText(contact.getContactPhone());
                holder.textViewEmail.setText(contact.getContactEmail());


                /*if(position % 2 ==0) {
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.orange));
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                }else {
                    holder.relativelayout.getBackground().setTint(context.getResources().getColor(R.color.white));
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactModel contact = list.get(holder.getAdapterPosition());
                        Intent intent = new Intent(view.getContext(), UpdateContactActivity.class);
                        intent.putExtra("Contacts", contact);
                        intent.putExtra("uid", userId);
                        view.getContext().startActivity(intent);
                    }
                });

            holder.itemView.findViewById(R.id.imageViewContactListDelete).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*FirebaseFirestore.getInstance().collection("Users").document(mUser.getUid()).
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
                        });*/
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Contacts").orderByChild("title").equalTo("Apple");

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
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
