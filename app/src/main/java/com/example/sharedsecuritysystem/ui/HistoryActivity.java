package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.sharedsecuritysystem.Adapter.HistoryAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.HistoryResponse;
import com.example.sharedsecuritysystem.Response.SystemConnectedResponse;
import java.util.ArrayList;
public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = new ArrayList<HistoryResponse>();
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));
        list.add(new HistoryResponse(R.drawable.user1, "This is alert", "31/03/2022", "12:18 PM", "This is a note"));




        recyclerView = findViewById(R.id.recyclerHistory);
        historyAdapter = new HistoryAdapter(list,this);
        recyclerView.setAdapter(historyAdapter);
    }
}