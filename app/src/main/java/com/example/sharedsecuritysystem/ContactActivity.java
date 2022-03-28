package com.example.sharedsecuritysystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContactActivity extends AppCompatActivity {

    private EditText contactNameEdt, contactEmailEdt, contactPhoneEdt;
    private Button createContact;
    ProgressDialog progressDialog;

    private String contactName, contactEmail, contactPhone;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        db = FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(this);

        contactNameEdt=findViewById(R.id.editTextContactName);
        contactEmailEdt=findViewById(R.id.editTextContactEmail);
        contactPhoneEdt=findViewById(R.id.editTextContactPhone);
        createContact=findViewById(R.id.btn_createContact);

        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactName=contactNameEdt.getText().toString();
                contactEmail=contactEmailEdt.getText().toString();
                contactPhone=contactPhoneEdt.getText().toString();

                if(TextUtils.isEmpty(contactName)){
                    contactNameEdt.setError("Please enter name");
                }else if(TextUtils.isEmpty(contactEmail)){
                    contactEmailEdt.setError("Please enter email");
                }else if(TextUtils.isEmpty(contactPhone)){
                    contactPhoneEdt.setError("Please enter phone number");
                }else{
                    progressDialog.setMessage("Please wait while creating contact...");
                    progressDialog.setTitle("Creating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    addDataToFireStore(contactName, contactEmail, contactPhone);

                }

            }
        });

    }

    private void addDataToFireStore(String contactName, String contactEmail, String contactPhone) {
        CollectionReference dbContacts = db.collection("Contacts");
        Contact contacts = new Contact(contactName, contactEmail, contactPhone);
        dbContacts.add(contacts).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.dismiss();
                Toast.makeText(ContactActivity.this, "Your contact have been created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContactActivity.this, "Fail to create contact", Toast.LENGTH_SHORT).show();
            }
        });
    }
}