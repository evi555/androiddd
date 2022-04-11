package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.example.sharedsecuritysystem.Adapter.ContactAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactResponse> list;
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

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
}