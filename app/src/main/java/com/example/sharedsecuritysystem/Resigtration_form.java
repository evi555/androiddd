package com.example.sharedsecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Resigtration_form extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigtration_form);

        textView = findViewById(R.id.textview3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Resigtration_form.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}