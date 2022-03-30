package com.example.sharedsecuritysystem.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding testBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBinding=ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(testBinding.getRoot());

        testBinding.t1.setText("Iam changed now");

    }
}