package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityHomeBinding;
import com.example.sharedsecuritysystem.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding homeBinding;
    private FirebaseFirestore db;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userId;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        db = FirebaseFirestore.getInstance();

        if (getIntent().hasExtra("uid")){
            userId =  getIntent().getStringExtra("uid");
            getData();
        }
         Log.e("HomeActivity","uid"+ userId);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        homeBinding.btnFloating.setVisibility(View.GONE);
        navigationView = (NavigationView) findViewById(R.id.navViewHome);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_contact_list).setVisible(false);
        nav_menu.findItem(R.id.nav_system_control).setVisible(false);
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
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });
    }

    private void showItem()
    {
        navigationView = (NavigationView) findViewById(R.id.navViewHome);
        Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_contact_list).setVisible(true);
            nav_Menu.findItem(R.id.nav_system_control).setVisible(true);
            homeBinding.btnFloating.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_contact_list:
                Intent intent = new Intent(HomeActivity.this, ContactListActivity.class);
                userId =  getIntent().getStringExtra(Utils.userId);
                intent.putExtra(Utils.userId,userId);
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
                                mAuth.signOut();
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
                intent = new Intent(this, SystemControlActivity.class);
                userId =  getIntent().getStringExtra("uid");
                intent.putExtra("uid",userId);
                startActivity(intent);
                //finishAffinity();
                break;
                case R.id.nav_my_profile:
                    intent = new Intent(this, HomeActivity.class);
                    userId =  getIntent().getStringExtra("uid");
                    intent.putExtra("uid",userId);
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

    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout)findViewById(R.id.drawerHome);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getData(){

        DatabaseReference data = databaseReference.child("users").child(userId);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("dataa", dataSnapshot.toString());
                List<String> data = new ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    data.add(postSnapshot.getValue().toString());
                }
                if (dataSnapshot.child("name").getValue()!= null)
                    homeBinding.txtUsr.setText("Welcome back "+(dataSnapshot.child("name")).getValue().toString());
                if (dataSnapshot.child("name").getValue()!= null)
                    homeBinding.txtName.setText(dataSnapshot.child("name").getValue().toString());
                if (dataSnapshot.child("email").getValue()!= null)
                homeBinding.edtTxtEmail.setText(dataSnapshot.child("email").getValue().toString());
                if (dataSnapshot.child("phone").getValue()!= null)
                homeBinding.txtPhn.setText(dataSnapshot.child("phone").getValue().toString());

                if (dataSnapshot.child("own system security").getValue()!= null && dataSnapshot.child("own system security").getValue().equals(true)){
                    showItem();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
}