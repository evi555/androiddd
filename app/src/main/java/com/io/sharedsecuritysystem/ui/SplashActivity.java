package com.io.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.io.sharedsecuritysystem.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Thread loading = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    Intent main = new   Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(main);
                    finish();


                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };

        loading.start();
    }

    }