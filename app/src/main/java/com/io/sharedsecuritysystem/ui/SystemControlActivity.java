package com.io.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.io.sharedsecuritysystem.databinding.ActivitySystemControlBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SystemControlActivity extends AppCompatActivity {
    ActivitySystemControlBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseFirestore db;
    String status="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySystemControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        binding.btnSwitch.setChecked(false);

        binding.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    status="true";
                    Toast.makeText(SystemControlActivity.this, "button is on", Toast.LENGTH_SHORT).show();

                }else{
                    status="false";
                    Toast.makeText(SystemControlActivity.this, "button is off", Toast.LENGTH_SHORT).show();
                }
                    updateData(status);
            }
        });
    }

    private void updateData(String state){
        databaseReference.child("users").child(mUser.getUid()).child("sysControl").setValue(status);
    }
}