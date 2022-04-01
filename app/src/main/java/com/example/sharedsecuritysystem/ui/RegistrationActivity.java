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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {
    ActivityResigtrationBinding binding;
    private FirebaseFirestore db;

    /*TextView textView;
    Button button;
    EditText inputEmail, inputPassword, inputConfirmPassword, inputName, inputPhone;*/
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResigtrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        //checkButton();

        /*inputEmail = findViewById(R.id.editTextRegistrationEmail);
        inputPassword=findViewById(R.id.editTextRegistrationPassword);
        inputConfirmPassword=findViewById(R.id.editTextRegistrationConfirmPassword);
        inputName=findViewById(R.id.editTextRegistrationUser);
        inputPhone=findViewById(R.id.editTextRegistrationPhone);*/


        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /*textView = findViewById(R.id.textViewRegistrationLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });*/

        binding.llSysId.setVisibility(View.GONE);

        binding.rdBtnOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rdBtnOwn.isChecked())
                    binding.llSysId.setVisibility(View.VISIBLE);

                else
                    binding.llSysId.setVisibility(View.GONE);
            }
        });

        binding.rdBtnDntOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rdBtnDntOwn.isChecked())
                    binding.llSysId.setVisibility(View.GONE);
                else
                    binding.llSysId.setVisibility(View.VISIBLE);
            }
        });

        binding.txtBckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        /*button = findViewById(R.id.btn_registration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
                *//*Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();*//*
            }
        });*/
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();

            }
        });
    }


    private void PerforAuth() {
        String email = binding.edtTxtEmail.getText().toString();
        String password = binding.edtTxtPswrd.getText().toString();
        String confirmPassword = binding.edtTxtCnfrmPswrd.getText().toString();
        String name = binding.edtTxtUsr.getText().toString();
        String phone = binding.edtTxtPhn.getText().toString();

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
                        sendUserToNextActivity();
                        addDataToFireStore(task.getResult().getUser());
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
//        intent.putExtra("email", ());
//        intent.putExtra("userId", mUser.getUid());
//        intent.putExtra("name", mUser.getDisplayName());
        startActivity(intent);
    }


    private void addDataToFireStore(FirebaseUser user) {
        RegistrationData userLogin = new RegistrationData(binding.edtTxtUsr.getText().toString(),
                user.getEmail(), binding.edtTxtPhn.getText().toString());

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
    }
}
