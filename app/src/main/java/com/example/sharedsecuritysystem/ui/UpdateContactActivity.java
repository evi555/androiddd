package com.example.sharedsecuritysystem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharedsecuritysystem.Response.ContactModel;
import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.example.sharedsecuritysystem.databinding.ActivityUpdateContactBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.UUID;

public class UpdateContactActivity extends AppCompatActivity {
    ActivityUpdateContactBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    String userId;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private String contactEmail, contactName, contactPhone;
    private FirebaseFirestore db;
    ContactModel contact;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contact = (ContactModel) getIntent().getSerializableExtra("Contacts");

        userId = getIntent().getStringExtra("userId");
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        binding.editTextUpdateName.setText(contact.getContactName());
        binding.editTextUpdateEmail.setText(contact.getContactEmail());
        binding.editTextUpdatePhone.setText(contact.getContactPhone());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactName = binding.editTextUpdateName.getText().toString();
                contactEmail = binding.editTextUpdateEmail.getText().toString();
                contactPhone = binding.editTextUpdatePhone.getText().toString();

                if (TextUtils.isEmpty(contactName)) {
                    binding.editTextUpdateName.setError("Please enter name");
                } else if (!contactEmail.matches(emailPattern)) {
                    binding.editTextUpdateEmail.setError("Please enter valid email");
                } else if (TextUtils.isEmpty(contactPhone) || contactPhone.length()<10) {
                    binding.editTextUpdatePhone.setError("Please enter correct phone number");
                } else {
                    progressDialog.setMessage("Please wait while creating contact...");
                    progressDialog.setTitle("Creating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    updatedata(contactName, contactEmail, contactPhone);

                }
            }
        });
    }

    private void updatedata(String contactName, String contactEmail, String contactPhone) {
        DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                .child("Contacts").child(contact.getContactId());

        databaseReference.child("users").child(mUser.getUid()).child("Contacts").child(contact.getContactId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    path.child("contactName").setValue(contactName);
                    path.child("contactEmail").setValue(contactEmail);
                    path.child("contactPhone").setValue(contactPhone);

                    // TODO
                sendUserToNextActivity();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("uid", userId);
        startActivity(intent);
        //finishAffinity();
    }
}