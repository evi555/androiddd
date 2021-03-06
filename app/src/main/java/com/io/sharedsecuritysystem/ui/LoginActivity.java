package com.io.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.io.sharedsecuritysystem.databinding.ActivityLoginBinding;
import com.io.sharedsecuritysystem.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginbinding;
    private FirebaseFirestore db;
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
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //Log.e("user id ","mUser  : "+mUser.getUid());
        if (getIntent().hasExtra("uid")){
            String userId =  getIntent().getStringExtra(Utils.userId);
           // Log.e("LoginActivity","uid"+ userId);
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra(Utils.userId, userId);
            startActivity(i);
        } else if ( mUser != null){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra(Utils.userId, mUser.getUid());
            startActivity(i);
        }

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
                String email = loginbinding.edtTxtEmail.getText().toString().trim();
                String password = loginbinding.edtTxtPswrd.getText().toString().trim();

                if (!email.matches(emailPattern)) {
                    loginbinding.edtTxtEmail.setError("Please enter registered Email ID");
                } else if (password.isEmpty() || password.length() < 6) {
                    loginbinding.edtTxtPswrd.setError("Please enter correct password");
                } else {
                    progressDialog.setMessage("Please wait while Login...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
            }


            private void perForLogin() {
                String email = loginbinding.edtTxtEmail.getText().toString().trim();
                String password = loginbinding.edtTxtPswrd.getText().toString().trim();

                if (!email.matches(emailPattern)) {
                    loginbinding.edtTxtEmail.setError("Please enter registered Email ID");
                } else if (password.isEmpty() || password.length() < 6) {
                    loginbinding.edtTxtPswrd.setError("Please enter correct password");
                } else {
                    progressDialog.setMessage("Please wait while Login...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    /*
                    firebase authentication
                     */
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                mUser = task.getResult().getUser();
                                sendUserToNextActivity();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Email id or password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            private void sendUserToNextActivity() {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                Log.e("uid", mUser.getUid());
                intent.putExtra("uid", mUser.getUid());
                startActivity(intent);
            }
        });
    }
}