package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.sharedsecuritysystem.Adapter.ContactAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactModel;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    Context context;
    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactModel> list;
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        userId =  getIntent().getStringExtra("uid");
        Log.e("uid", "check user id" + userId);


        recyclerView = findViewById(R.id.recyclerContactList);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        list  = new ArrayList<>();

        getData();
    }

    public void getData(){
        databaseReference.child("users").child(userId).child("Contacts").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.e("list", "contactList" + snapshot);
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    ContactModel contactModel = dataSnapshot.getValue(ContactModel.class);

                    list.add(contactModel);
                    Log.e("list", "contactList list ..." + list.size());
                }
                Log.e("Data ","Data : "+list.size()+"   "+list.get(0).getContactEmail());
                contactAdapter = new ContactAdapter((ArrayList<ContactModel>) list, context);
                recyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("uid", userId);
        startActivity(intent);
        finishAffinity();

    }
}