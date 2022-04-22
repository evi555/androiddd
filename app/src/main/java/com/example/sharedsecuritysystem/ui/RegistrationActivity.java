package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.sharedsecuritysystem.databinding.ActivityResigtrationBinding;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    // below line is used to get the
    // instance of our Firebase database.
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    ActivityResigtrationBinding binding;
    private FirebaseFirestore db;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Boolean own = false;

    // creating a variable for our
    // Firebase Database.
    //DatabaseReference firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    //DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    RegistrationData registrationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResigtrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // below line is used to get reference for our database.
        //databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        // initializing our object
        // class variable.
//        registrationData = new RegistrationData();


        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        binding.llSysId.setVisibility(View.GONE);

        binding.rdBtnOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rdBtnOwn.isChecked()) {
                    binding.llSysId.setVisibility(View.VISIBLE);
                     own = true;

                }else
                    binding.llSysId.setVisibility(View.GONE);
                    own = false;
            }
        });

        binding.rdBtnDntOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rdBtnDntOwn.isChecked()) {
                    binding.llSysId.setVisibility(View.GONE);
                    own = false;
                }else
                    binding.llSysId.setVisibility(View.VISIBLE);
            }
        });

        binding.txtBckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PerforAuth();
                /*String email = binding.edtTxtEmail.getText().toString().trim();
                String password = binding.edtTxtPswrd.getText().toString().trim();
                String confirmPassword = binding.edtTxtCnfrmPswrd.getText().toString().trim();
                String name = binding.edtTxtUsr.getText().toString().trim();
                String phone = binding.edtTxtPhn.getText().toString().trim();
                String sysId = binding.edtTxtSysId.getText().toString().trim();
                Boolean ownSecuritySystem = binding.rdBtnOwn.isChecked();
                Boolean dontOwnSecuritySystem = binding.rdBtnDntOwn.isChecked();

                if (!email.matches(emailPattern)) {
                    binding.edtTxtEmail.setError("Enter Correct Email");
                } else if (password.isEmpty() || password.length() < 6) {
                    binding.edtTxtPswrd.setError("Enter proper password");
                } else if (!password.equals(confirmPassword)) {
                    binding.edtTxtCnfrmPswrd.setError("Password not matched");
                } else if (name.isEmpty() || name.length() < 3) {
                    binding.edtTxtUsr.setError("Enter proper name");
                } else if (phone.isEmpty() || phone.length() < 10) {
                    binding.edtTxtPhn.setError("Phone number is not correct");
                } else {
                    progressDialog.setMessage("Please wait while registration...");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(email.replace(".", ","))){
                                Toast.makeText(RegistrationActivity.this, "phone is already registered", Toast.LENGTH_SHORT).show();
                            }else {
                                progressDialog.dismiss();
                                *//*RegistrationData data = new RegistrationData(name,email,
                                        phone,"",true,false,false);
                                databaseReference.child("users").child("data").setValue(data);*//*

                                databaseReference.child("users").child(email.replace(".", ",")).child("name").setValue(name);
                                databaseReference.child("users").child(email.replace(".", ",")).child("phone").setValue(phone);
                                databaseReference.child("users").child(email.replace(".", ",")).child("password").setValue(password);
                                databaseReference.child("users").child(email.replace(".", ",")).child("System ID").setValue(sysId);
                                databaseReference.child("users").child(email.replace(".", ",")).child("Own System Security").setValue(ownSecuritySystem);
                                databaseReference.child("users").child(email.replace(".", ",")).child("Don't Own System Security").setValue(dontOwnSecuritySystem);

                                Toast.makeText(RegistrationActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                sendUserToNextActivity();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }*/
            }
        });
    }

    private void PerforAuth() {
        String email = binding.edtTxtEmail.getText().toString().trim();
        String password = binding.edtTxtPswrd.getText().toString().trim();
        String confirmPassword = binding.edtTxtCnfrmPswrd.getText().toString().trim();
        String name = binding.edtTxtUsr.getText().toString().trim();
        String phone = binding.edtTxtPhn.getText().toString().trim();
        String sysID = binding.edtTxtSysId.getText().toString().trim();
        Boolean ownSecuritySystem = binding.rdBtnOwn.isChecked();
        Boolean dontOwnSecuritySystem = binding.rdBtnDntOwn.isChecked();

        if (!email.matches(emailPattern)) {
            binding.edtTxtEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.edtTxtPswrd.setError("Enter proper password");
        } else if (!password.equals(confirmPassword)) {
            binding.edtTxtCnfrmPswrd.setError("Password not matched");
        } else if (name.isEmpty() || name.length() < 3) {
            binding.edtTxtUsr.setError("Enter proper name");
        } else if (phone.isEmpty() || phone.length() < 10) {
            binding.edtTxtPhn.setError("Phone number is not correct");
        } else {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        mUser  = task.getResult().getUser();
                        sendUserToNextActivity();
                       // addDataToFireStore(task.getResult().getUser());



                        databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(name)) {
                                    Toast.makeText(RegistrationActivity.this, "phone is already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();

                                    DatabaseReference path =  databaseReference.child("users").child(mUser.getUid());

                                    path.child("phone").setValue(phone);
                                    path.child("password").setValue(password);
                                    path.child("system id").setValue(sysID);
                                    path.child("own system security").setValue(ownSecuritySystem);
                                    path.child("don't own system security").setValue(dontOwnSecuritySystem);
                                    path.child("email").setValue(email);
                                    path.child("name").setValue(name);

                                    Toast.makeText(RegistrationActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                    //sendUserToNextActivity();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //addDatatoRealtime(email, password, name, phone, sysID);
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*private void addDatatoRealtime(String email, String password, String name, String phone, String sysId) {
         email = binding.edtTxtEmail.getText().toString().trim();
         password = binding.edtTxtPswrd.getText().toString().trim();
         String confirmPassword = binding.edtTxtCnfrmPswrd.getText().toString().trim();
         name = binding.edtTxtUsr.getText().toString().trim();
         phone = binding.edtTxtPhn.getText().toString().trim();
         sysId = binding.edtTxtSysId.getText().toString().trim();
        Boolean ownSecuritySystem = binding.rdBtnOwn.isChecked();
        Boolean dontOwnSecuritySystem = binding.rdBtnDntOwn.isChecked();

        if (!email.matches(emailPattern)) {
            binding.edtTxtEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.edtTxtPswrd.setError("Enter proper password");
        } else if (!password.equals(confirmPassword)) {
            binding.edtTxtCnfrmPswrd.setError("Password not matched");
        } else if (name.isEmpty() || name.length() < 3) {
            binding.edtTxtUsr.setError("Enter proper name");
        } else if (phone.isEmpty() || phone.length() < 10) {
            binding.edtTxtPhn.setError("Phone number is not correct");
        } else {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }
    }*/

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.putExtra("uid", mUser.getUid());
            startActivity(intent);
    }

    /*private void addDataToFireStore(FirebaseUser user) {

        db.collection("Users").document(user.getUid())
                .set(userLogin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }*/
}
