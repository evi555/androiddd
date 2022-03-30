package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityResigtrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    ActivityResigtrationBinding resigtrationBinding;

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
        resigtrationBinding=ActivityResigtrationBinding.inflate(getLayoutInflater());
        setContentView(resigtrationBinding.getRoot());

        /*inputEmail = findViewById(R.id.editTextRegistrationEmail);
        inputPassword=findViewById(R.id.editTextRegistrationPassword);
        inputConfirmPassword=findViewById(R.id.editTextRegistrationConfirmPassword);
        inputName=findViewById(R.id.editTextRegistrationUser);
        inputPhone=findViewById(R.id.editTextRegistrationPhone);*/
        progressDialog= new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        /*textView = findViewById(R.id.textViewRegistrationLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });*/
        resigtrationBinding.textViewRegistrationLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
        resigtrationBinding.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String email=resigtrationBinding.editTextRegistrationEmail.getText().toString();
        String password = resigtrationBinding.editTextRegistrationPassword.getText().toString();
        String confirmPassword = resigtrationBinding.editTextRegistrationConfirmPassword.getText().toString();
        String name= resigtrationBinding.editTextRegistrationUser.getText().toString();
        String phone= resigtrationBinding.editTextRegistrationPhone.getText().toString();

        if(!email.matches(emailPattern))
        {
            resigtrationBinding.editTextRegistrationEmail.setError("Enter Correct Email");
        }else if(password.isEmpty() || password.length()<6)
        {
            resigtrationBinding.editTextRegistrationPassword.setError("Enter proper password");
        }else if(!password.equals(confirmPassword))
        {
            resigtrationBinding.editTextRegistrationConfirmPassword.setError("Password not matched");
        }else if(name.isEmpty() || name.length()<3)
        {
            resigtrationBinding.editTextRegistrationUser.setError("Enter proper name");
        }else if(phone.isEmpty() || phone.length()<10)
        {
            resigtrationBinding.editTextRegistrationPhone.setError("Phone number is not correct");
        }else
        {
            progressDialog.setMessage("Please wait while registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity(){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}