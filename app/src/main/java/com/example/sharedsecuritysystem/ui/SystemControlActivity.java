package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.sharedsecuritysystem.R;

import java.io.File;


public class SystemControlActivity extends AppCompatActivity {


    File destination;
    Uri selectedImage;
    public static String selectedPath1 = "NONE";
    private static final int PICK_Camera_IMAGE = 2;
    private static final int SELECT_FILE1 = 1;
    public static Bitmap bmpScale;
    public static String imagePath;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_control);

        imageView = findViewById(R.id.cameraimg);
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                *//*intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(destination));*//*
                startActivityForResult(intent, PICK_Camera_IMAGE);
            }
        });*/

    }
}