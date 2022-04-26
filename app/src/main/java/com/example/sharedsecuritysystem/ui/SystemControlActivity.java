package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.example.sharedsecuritysystem.databinding.ActivitySystemControlBinding;
import com.google.android.gms.tasks.OnSuccessListener;
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
                    status="true"; //edit here
                    Toast.makeText(SystemControlActivity.this, "button is on", Toast.LENGTH_SHORT).show();

                }else{
                    status="false"; //edit here
                    Toast.makeText(SystemControlActivity.this, "button is off", Toast.LENGTH_SHORT).show();
                }
                    updateData(status);
            }
        });
    }

    private void updateData(String state){
        databaseReference.child("users").child(mUser.getUid()).child("sysControl").setValue(status);
       /* db.collection("Users").document(mUser.getUid()).update("sysControl",status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SystemControlActivity.this, "data updated", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}