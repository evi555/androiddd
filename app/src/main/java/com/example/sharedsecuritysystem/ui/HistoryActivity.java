package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.example.sharedsecuritysystem.Adapter.HistoryAdapter;
import com.example.sharedsecuritysystem.Adapter.HistoryModel;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.databinding.ActivityHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class HistoryActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://security-system-58cb7-default-rtdb.firebaseio.com/");
    ActivityHistoryBinding binding;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryModel> list;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Context context;

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
        historyAdapter = new HistoryAdapter((ArrayList<HistoryModel>)list,this);
        recyclerView.setAdapter(historyAdapter);

        getData();

    }

    private void getData() {
        databaseReference.child("users").child(mUser.getUid()).child("Alerts").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    HistoryModel historyModel = dataSnapshot.getValue(HistoryModel.class);
                    //HistoryResponse historyResponse = dataSnapshot.getValue(HistoryResponse.class);
                    Log.e("list", "history listttt ..." + list.size());

                    list.add(historyModel);
                    Log.e("list", "history list ..." + list.size());
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
}