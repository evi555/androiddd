package com.example.sharedsecuritysystem.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.example.sharedsecuritysystem.databinding.ActivityUpdateContactBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateContactActivity extends AppCompatActivity {
    ActivityUpdateContactBinding binding;

    String userId;
    ProgressDialog progressDialog;

    private String contactEmail, contactName, contactPhone;
    private FirebaseFirestore db;
    ContactResponse contact;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contact = (ContactResponse) getIntent().getSerializableExtra("Contacts");

        userId = getIntent().getStringExtra("userId");
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        binding.editTextUpdateEmail.setText(contact.getEmail());
        binding.editTextUpdateName.setText(contact.getName());
        binding.editTextUpdatePhone.setText(contact.getPhone());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactEmail = binding.editTextUpdateEmail.getText().toString();
                contactName = binding.editTextUpdateName.getText().toString();
                contactPhone = binding.editTextUpdatePhone.getText().toString();

                if (TextUtils.isEmpty(contactEmail)) {
                    binding.editTextUpdateEmail.setError("Please enter email");
                } else if (TextUtils.isEmpty(contactEmail)) {
                    binding.editTextUpdateName.setError("Please enter name");
                } else if (TextUtils.isEmpty(contactPhone)) {
                    binding.editTextUpdatePhone.setError("Please enter phone number");
                } else {
                    progressDialog.setMessage("Please wait while creating contact...");
                    progressDialog.setTitle("Creating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    updatedata(contactEmail, contactName, contactPhone);

                }
            }
        });
    }

    private void updatedata(String contactEmail, String contactName, String contactPhone) {
        Contact updatedContact=new Contact(contactEmail,contactName,contactPhone);

        db.collection("Users").document(mUser.getUid()).collection("Contacts").document(contact.getUid()).set(updatedContact).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateContactActivity.this, "Contact have been updated..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateContactActivity.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}