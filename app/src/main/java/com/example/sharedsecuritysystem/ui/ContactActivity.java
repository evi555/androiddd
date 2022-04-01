package com.example.sharedsecuritysystem.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sharedsecuritysystem.databinding.ActivityContactBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding contactBinding;

    /*private EditText contactNameEdt, contactEmailEdt, contactPhoneEdt;
    private Button createContact;*/
    ProgressDialog progressDialog;

    private String contactName, contactEmail, contactPhone;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactBinding=ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(contactBinding.getRoot());

        db = FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(this);

        /*contactNameEdt=findViewById(R.id.editTextContactName);
        contactEmailEdt=findViewById(R.id.editTextContactEmail);
        contactPhoneEdt=findViewById(R.id.editTextContactPhone);
        createContact=findViewById(R.id.btn_createContact);*/

        contactBinding.btnCrtCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactName=contactBinding.txtName.getText().toString();
                contactEmail=contactBinding.txtEmail.getText().toString();
                contactPhone=contactBinding.txtPhn.getText().toString();

                if(TextUtils.isEmpty(contactName)){
                    contactBinding.txtName.setError("Please enter name");
                }else if(TextUtils.isEmpty(contactEmail)){
                    contactBinding.txtEmail.setError("Please enter email");
                }else if(TextUtils.isEmpty(contactPhone)){
                    contactBinding.txtPhn.setError("Please enter phone number");
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