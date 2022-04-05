package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//import com.example.sharedsecuritysystem.Response.ContactResponse;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginbinding;
    private FirebaseFirestore db;
    private String Name, Email, Phone;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginbinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginbinding.getRoot());

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });


        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loginbinding.txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        loginbinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perForLogin();
            }
        });
    }

    private void perForLogin() {
        String email = loginbinding.edtTxtEmail.getText().toString();
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
                        //addDataToFireStore(task.getResult().getUser());
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

    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("userId",mUser.getUid());
        startActivity(intent);
    }
}