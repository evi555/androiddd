package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding homeBinding;

    /*ImageView imageView;
    DrawerLayout drawerLayout;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        /*imageView = findViewById(R.id.imageViewHomeHamburger);
        drawerLayout=findViewById(R.id.drawer1);
        floatingActionButton = findViewById(R.id.btn_floating);
        navigationView = findViewById(R.id.navViewHome);*/
        homeBinding.navViewHome.setNavigationItemSelectedListener(this);

        homeBinding.imageViewHomeHamburger.setOnClickListener(new View.OnClickListener() {
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
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_my_profile:
                startActivity(new Intent(this, HomeActivity.class));
//                finishAffinity();
                break;
            case R.id.nav_contact_list:
                startActivity(new Intent(this, ContactListActivity.class));
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
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}