package com.example.sharedsecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharedsecuritysystem.contact_adapter.ContactAdapter;

public class UpdateContactActivity extends AppCompatActivity {

    String name, email, phone;
    EditText editTextName, editTextEmail, editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);

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