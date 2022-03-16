package com.example.sharedsecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sharedsecuritysystem.contact_adapter.ContactAdapter;

public class UpdateContactActivity extends AppCompatActivity {

    String name, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Intent intent = new Intent(UpdateContactActivity.this, ContactAdapter.class);
        name = intent.getStringExtra("Name");
//        email = getIntent().getStringExtra("Email");
//        phone = getIntent().getStringExtra("Phone");

    }
}