package com.example.sharedsecuritysystem.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sharedsecuritysystem.databinding.ActivityContactBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding contactBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private String contactName, contactEmail, contactPhone;
    private FirebaseFirestore db;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactBinding=ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(contactBinding.getRoot());

        String userId = getIntent().getStringExtra("uid");
        db = FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        contactBinding.btnCrtCnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactName=contactBinding.txtName.getText().toString();
                contactEmail=contactBinding.txtEmail.getText().toString();
                contactPhone=contactBinding.txtPhn.getText().toString();

                if(TextUtils.isEmpty(contactName)){
                    contactBinding.txtName.setError("Please enter name");
                }else if(!contactEmail.matches(emailPattern)){
                    contactBinding.txtEmail.setError("Please enter valid email");
                }else if(TextUtils.isEmpty(contactPhone) || contactPhone.length()<10){
                    contactBinding.txtPhn.setError("Please enter valid phone number");
                }else{
                    progressDialog.setMessage("Please wait while creating contact...");
                    progressDialog.setTitle("Creating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    //addDataToFireStore(contactName, contactEmail, contactPhone);
                    //finishAffinity();
                    databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(mUser.getUid())) {
                                Toast.makeText(ContactActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                        /*RegistrationData data = new RegistrationData(name, email,
                                phone, "", true, false, false);
                        databaseReference.child("users").child("data").setValue(data);*/

                                String uuid = UUID.randomUUID().toString();

                                DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                                        .child("Contacts").child(uuid);

                                path.child("contactName").setValue(contactName);
                                path.child("contactEmail").setValue(contactEmail);
                                path.child("contactPhone").setValue(contactPhone);
                                path.child("contactId").setValue(uuid);

                                Toast.makeText(ContactActivity.this, "Created contact successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                sendUserToNextActivity(mUser.getUid());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void addDataToFireStore(String contactName, String contactEmail, String contactPhone) {
        CollectionReference dbContacts = db.collection("Users").document(mUser.getUid()).collection("Contacts");
        Contact contacts = new Contact(contactName, contactEmail, contactPhone);
        dbContacts.add(contacts).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressDialog.dismiss();
                Toast.makeText(ContactActivity.this, "Your contact have been created", Toast.LENGTH_SHORT).show();
                //finishAffinity();
                //Log.e("7h7yn8i", userId);
                sendUserToNextActivity(mUser.getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContactActivity.this, "Fail to create contact", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendUserToNextActivity(String userId) {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("uid",userId);
        startActivity(intent);
    }
}