package com.io.sharedsecuritysystem.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.io.sharedsecuritysystem.R;
import com.io.sharedsecuritysystem.Response.ContactModel;
import com.io.sharedsecuritysystem.databinding.ActivityUpdateContactBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class UpdateContactActivity extends AppCompatActivity {
    ActivityUpdateContactBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");
    String msg = "Data updated";
    String title = "There was a movement";
    String userId;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String contactEmail, contactName, contactPhone;
    private FirebaseFirestore db;
    ContactModel contact;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getToken();
        contact = (ContactModel) getIntent().getSerializableExtra("Contacts");

        userId =  getIntent().getStringExtra("uid");
        Log.e("User ","UUUSSEERR : "+userId);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        binding.editTextUpdateName.setText(contact.getContactName());
        binding.editTextUpdateEmail.setText(contact.getContactEmail());
        binding.editTextUpdatePhone.setText(contact.getContactPhone());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactName = binding.editTextUpdateName.getText().toString();
                contactEmail = binding.editTextUpdateEmail.getText().toString();
                contactPhone = binding.editTextUpdatePhone.getText().toString();

                if (TextUtils.isEmpty(contactName)) {
                    binding.editTextUpdateName.setError("Please enter name");
                } else if (!contactEmail.matches(emailPattern)) {
                    binding.editTextUpdateEmail.setError("Please enter valid email");
                } else if (TextUtils.isEmpty(contactPhone) || contactPhone.length()<10) {
                    binding.editTextUpdatePhone.setError("Please enter correct phone number");
                } else {
                    progressDialog.setMessage("Please wait while creating contact...");
                    progressDialog.setTitle("Creating");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    updatedata(contactName, contactEmail, contactPhone);

                }
            }
        });
    }

    /*
    update contacts data
     */
    private void updatedata(String contactName, String contactEmail, String contactPhone) {
        DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                .child("Contacts").child(contact.getContactId());

        databaseReference.child("users").child(mUser.getUid()).child("Contacts").child(contact.getContactId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    path.child("contactName").setValue(contactName);
                    path.child("contactEmail").setValue(contactEmail);
                    path.child("contactPhone").setValue(contactPhone);
                sendFCMPush();
                sendUserToNextActivity();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("uid", userId);
        Log.e("uid", "userid" + userId);
        startActivity(intent);
        finishAffinity();
        saveNotifications();
    }

    private void sendFCMPush() {

        String msg = "Data updated";
        String title = " There was a movement";

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

    void saveNotifications(){
        databaseReference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mUser.getUid())) {
                    Toast.makeText(UpdateContactActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();

                    String uuid = UUID.randomUUID().toString();

                    DatabaseReference path =  databaseReference.child("users").child(mUser.getUid())
                            .child("Alerts").child(uuid);

                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
                    Date date = new Date();
                    String strDate = dateFormat.format(date);


                    path.child("message").setValue(msg);
                    path.child("title").setValue(title);
                    path.child("time").setValue(strDate);
                    Toast.makeText(UpdateContactActivity.this, "Created contact successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.e("fcmtoken", task.getResult().toString());
                    token=task.getResult();
                }
            }
        });
    }
}