package com.io.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.io.sharedsecuritysystem.Adapter.HistoryAdapter;
import com.io.sharedsecuritysystem.Adapter.HistoryModel;
import com.io.sharedsecuritysystem.R;
import com.io.sharedsecuritysystem.databinding.ActivityHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HistoryActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");
    ActivityHistoryBinding binding;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryModel> list;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Context context;
    String token;
    String msg = "Data updated";
    String title = " There was a movement";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerHistory);

       // Collections.reverse(list);
        historyAdapter = new HistoryAdapter((ArrayList<HistoryModel>) list, this);
        recyclerView.setAdapter(historyAdapter);

        getData();
        getToken();

/*
        binding.btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(mUser.getUid())) {
                            Toast.makeText(HistoryActivity.this, "", Toast.LENGTH_SHORT).show();
                        } else {
                           // progressDialog.dismiss();

                            String uuid = UUID.randomUUID().toString();

                            DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                                    .child("Alerts").child(uuid);

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
                            Date date = new Date();
                            String strDate = dateFormat.format(date);

                            path.child("message").setValue(msg);
                            path.child("title").setValue(title);
                            path.child("time").setValue(strDate);

                            Toast.makeText(HistoryActivity.this, "Alerts", Toast.LENGTH_SHORT).show();
                            finish();
                            //sendUserToNextActivity(mUser.getUid());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                sendFCMPush();
            }
        });
*/
    }

    /*
    getting alerts data
     */
    private void getData() {
        databaseReference.child("users").child(mUser.getUid()).child("Alerts").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.e("datasnapshot", "datasnapsot" + dataSnapshot.toString());
                    HistoryModel historyModel = dataSnapshot.getValue(HistoryModel.class);
                    Log.e("list", "history listttt ..." + list.size());
                    list.add(historyModel);
                    Log.e("list", "history list ..." + list.size());
                }

                try {
                    Collections.sort(list, (obj1, obj2) -> {
                            return obj2.getTime().compareToIgnoreCase(obj1.getTime()); // To compare string values
                        //return Integer.valueOf(obj2.getTime()).compareTo(obj1.getTime()); // To compare integer values

                    });
                }catch (Exception e)
                {
                    Log.e("jdfhjdskhf", e.getMessage());
                }

                historyAdapter = new HistoryAdapter(list, context);
                recyclerView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}