package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding homeBinding;
    private FirebaseFirestore db;
    NavigationView navigationView;
    Boolean abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        db = FirebaseFirestore.getInstance();
        String userId =  getIntent().getStringExtra("userId");

        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        abc= (Boolean) document.getData().get("own");
                        hideItem();
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData().get("email"));
                        homeBinding.txtUsr.setText("Welcome back " + document.getData().get("name").toString());
                        homeBinding.edtTxtEmail.setText(document.getData().get("email").toString());
                        homeBinding.txtName.setText(document.getData().get("name").toString());
                        homeBinding.txtPhn.setText(document.getData().get("phoneNum").toString());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        homeBinding.navViewHome.setNavigationItemSelectedListener(this);

        homeBinding.imgHmburgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homeBinding.drawerHome.isDrawerOpen(GravityCompat.START)){
                    homeBinding.drawerHome.closeDrawer(GravityCompat.START);
                }
                else{
                    homeBinding.drawerHome.openDrawer(GravityCompat.START);
                }
            }
        });

        homeBinding.btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                intent.putExtra("userID",userId);
                startActivity(intent);
            }
        });
    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.navViewHome);
        Menu nav_Menu = navigationView.getMenu();
        if(abc==false) {
            nav_Menu.findItem(R.id.nav_contact_list).setVisible(false);
            nav_Menu.findItem(R.id.nav_system_control).setVisible(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_contact_list:
                Intent intent = new Intent(HomeActivity.this, ContactListActivity.class);
                String userId =  getIntent().getStringExtra("userId");
                intent.putExtra("userId",userId);
                startActivity(intent);
//                finishAffinity();
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Signing out")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                finishAffinity();
                            }
                        }).setNegativeButton("No", null).show();
                break;
            case R.id.nav_connected_to:
                startActivity(new Intent(this, ConnectedToActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.nav_system_control:
                startActivity(new Intent(this, SystemControlActivity.class));
                break;
                case R.id.nav_my_profile:
                    intent = new Intent(this, HomeActivity.class);
                    userId =  getIntent().getStringExtra("userId");
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                    finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}