package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.sharedsecuritysystem.Adapter.ContactAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactResponse> list;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        String userId =  getIntent().getStringExtra("userId");
        recyclerView = findViewById(R.id.recyclerContactList);

        //List<Collection> Contacts = document.getList("Contacts");


        db = FirebaseFirestore.getInstance();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference applicationsRef = db.collection("Users").document(userId).
                collection("Contacts");

        applicationsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    list.add(new ContactResponse(document.getString("contactEmail"),document.getString("contactName"),document.getString("contactPhone")));

                }
                contactAdapter = new ContactAdapter(list,this);
                recyclerView.setAdapter(contactAdapter);

                //Log.d("TAG",list.get(1).getPhone().toString());
                } else {
                 Log.d("TAG", "Error getting documents: ", task.getException());
                }
            //List<Map<String, Object>> Users = (List<Map<String, Object>>) document().get("Contacts");
        });
    }
}