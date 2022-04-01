package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.example.sharedsecuritysystem.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginbinding;
    private FirebaseFirestore db;
    private String Name, Email, Phone;

    /*TextView textView;
    Button button;
    EditText inputEmail, inputPassword;*/
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginbinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginbinding.getRoot());

        db = FirebaseFirestore.getInstance();


        /*inputEmail = findViewById(R.id.editTextLoginEmail);
        inputPassword=findViewById(R.id.editTextLoginPassword);*/
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //textView = findViewById(R.id.textViewLoginNewUser);

        loginbinding.txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });*/

        //button = findViewById(R.id.btn_login);

        loginbinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perForLogin();
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perForLogin();
                *//*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();*//*
            }
        });*/
    }

    private void perForLogin() {
        //String email = inputEmail.getText().toString();
        String email = loginbinding.edtTxtEmail.getText().toString();
        //String password = inputPassword.getText().toString();
        String password = loginbinding.edtTxtPswrd.getText().toString();

        if (!email.matches(emailPattern)) {
            loginbinding.edtTxtEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            loginbinding.edtTxtPswrd.setError("Enter proper password");
        } else {
            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //task.getResult().getUser().getIdToken(true);
                        progressDialog.dismiss();
                        mUser = task.getResult().getUser();
                        addDataToFireStore(task.getResult().getUser());
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void addDataToFireStore(FirebaseUser user) {
        ContactResponse newuser = new ContactResponse("", "", "");
        Task<DocumentReference> dbRegistration = db.collection("Users").document(user.getUid()).
                collection("Contacts").add(newuser);
        dbRegistration.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("userId",mUser.getUid());
        startActivity(intent);
    }
}