package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.sharedsecuritysystem.Adapter.ContactAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactResponse> list;
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        userId =  getIntent().getStringExtra("uid");


        recyclerView = findViewById(R.id.recyclerContactList);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        CollectionReference applicationsRef = db.collection("Users").document(mUser.getUid()).
                collection("Contacts");

        applicationsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    list.add(new ContactResponse(document.getString("contactName"),
                            document.getString("contactEmail"),
                            document.getString("contactPhone"),
                            document.getId()));
                }
                contactAdapter = new ContactAdapter(list,this,mUser.getUid());
                recyclerView.setAdapter(contactAdapter);
                } else {
                 Log.d("TAG", "Error getting documents: ", task.getException());
                }
        });
    }

    /*public void getData(){
        DatabaseReference data = databaseReference.child("users").child(userId);
        //   Query citiesQuery = data.orderByKey().startAt(input).endAt(input+"\uf8ff");
        data.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> data = new ArrayList<String>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            data.add(postSnapshot.getValue().toString());
                            list.add(new ContactResponse(dataSnapshot.getString("contactName"),
                                    document.getString("contactEmail"),
                                    document.getString("contactPhone"),
                                    document.getId()));
                        }
                        contactAdapter = new ContactAdapter(list, this, mUser.getUid());
                        recyclerView.setAdapter(contactAdapter);
                    }
                });
            }
        });
    }*/


    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}