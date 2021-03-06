package com.io.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.io.sharedsecuritysystem.databinding.ActivityResigtrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    ActivityResigtrationBinding binding;
    private FirebaseFirestore db;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Boolean own = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResigtrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        binding.llSysId.setVisibility(View.GONE);

        /*
        to handle owner button
         */
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
        String alerts = "no";

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

                        databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(name)) {
                                    Toast.makeText(RegistrationActivity.this, "phone is already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressDialog.dismiss();

                                    /*
                                    adding data for registration
                                    */
                                    DatabaseReference path =  databaseReference.child("users").child(mUser.getUid());

                                    path.child("phone").setValue(phone);
                                    path.child("password").setValue(password);
                                    path.child("system_id").setValue(sysID);
                                    path.child("own_system_security").setValue(ownSecuritySystem);
                                    path.child("don't_own_system_security").setValue(dontOwnSecuritySystem);
                                    path.child("email").setValue(email);
                                    path.child("name").setValue(name);
                                    path.child("system_alerts").setValue(alerts);

                                    Toast.makeText(RegistrationActivity.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.putExtra("uid", mUser.getUid());
            startActivity(intent);
    }
}
