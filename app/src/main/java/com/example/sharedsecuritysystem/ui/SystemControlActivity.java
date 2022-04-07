package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivitySystemControlBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SystemControlActivity extends AppCompatActivity {
    ActivitySystemControlBinding binding;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySystemControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        CollectionReference applicationsRef = db.collection("Users").document(mUser.getUid()).
                collection("System Control");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("states").child("001");

        /*binding.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String, Object> newStatus = new HashMap<>();
                childUpdates.put("states", isChecked)

                ref.update(newStatus)
            }
        });*/

    }
}