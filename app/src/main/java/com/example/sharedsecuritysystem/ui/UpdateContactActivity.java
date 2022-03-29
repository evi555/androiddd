package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.sharedsecuritysystem.R;

public class UpdateContactActivity extends AppCompatActivity {

    String name, email, phone;
    EditText editTextName, editTextEmail, editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        editTextName = findViewById(R.id.editTextUpdateName);
        editTextEmail = findViewById(R.id.editTextUpdateEmail);
        editTextPhone = findViewById(R.id.editTextUpdatePhone);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        email = intent.getStringExtra("Email");
        phone = intent.getStringExtra("Phone");
       // Log.e("check",name);
        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);
    }
}