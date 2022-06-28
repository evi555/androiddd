package com.io.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.io.sharedsecuritysystem.R;
import com.io.sharedsecuritysystem.databinding.ActivityHomeBinding;
import com.io.sharedsecuritysystem.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding homeBinding;
    private FirebaseFirestore db;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userId;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");
    String msg = "Data updated";
    String title = " There was a movement";
    String token;
    String system_alerts = "yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        db = FirebaseFirestore.getInstance();
        //getToken();

        if (getIntent().hasExtra("uid")){
            userId =  getIntent().getStringExtra("uid");
            getToken();
            getData();

        }
         Log.e("HomeActivity","uid"+ userId);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        homeBinding.btnFloating.setVisibility(View.GONE);
        navigationView = findViewById(R.id.navViewHome);
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


        /*
        btnFloating is for going to ContactActivity
         */
        homeBinding.btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });
    }


    /*
    showItem() used for show contact list and system control for owners.
     */
    private void showItem()
    {
        navigationView = findViewById(R.id.navViewHome);
        Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_contact_list).setVisible(true);
            nav_Menu.findItem(R.id.nav_system_control).setVisible(true);
            homeBinding.btnFloating.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
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

    /*
    getting data from realtime database.
     */
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

                if (dataSnapshot.child("own_system_security").getValue()!= null && dataSnapshot.child("own_system_security").getValue().equals(true)){
                    showItem();
                }


                if (dataSnapshot.child("system_alerts").getValue()!= null && dataSnapshot.child("system_alerts").getValue().equals(system_alerts)){
                    DatabaseReference pathalerts =  databaseReference.child("users").child(mUser.getUid());
                    pathalerts.child("system_alerts").setValue("no");
                    databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(mUser.getUid())) {
                                Toast.makeText(HomeActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {
                                // progressDialog.dismiss();

                                /*
                                   adding system_alerts here.
                                */
                                String uuid = UUID.randomUUID().toString();

                                DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                                        .child("Alerts").child(uuid);

                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
                                Date date = new Date();
                                String strDate = dateFormat.format(date);

                                path.child("message").setValue(msg);
                                path.child("title").setValue(title);
                                path.child("time").setValue(strDate);

                                sendFCMPush();


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    /*
    push notification
     */
    private void sendFCMPush() {

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("return here>>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("True", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("False", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + getApplicationContext().getResources().getString(R.string.server_key));
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    /*
    getting fcm token
     */
    void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    Log.e("fcmtoken", task.getResult().toString());
                    token = task.getResult();
                }
            }
        });
    }

    private void sendUserToNextActivity(String userId) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("uid",userId);
        startActivity(intent);
        finishAffinity();
    }
}