package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.sharedsecuritysystem.Adapter.SystemConnectedAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.SystemConnectedResponse;

import java.util.ArrayList;

public class ConnectedToActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SystemConnectedAdapter systemConnectedAdapter;
    ArrayList<SystemConnectedResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_to);

        list = new ArrayList<SystemConnectedResponse>();
        list.add(new SystemConnectedResponse("Yash Pal", "yash@gmail.com", "901014745"));
        list.add(new SystemConnectedResponse("Aniket", "anku@gmail.com", "901014741"));
        list.add(new SystemConnectedResponse("Biku", "vivek@gmail.com", "98014745"));
        list.add(new SystemConnectedResponse("Ram", "ram@gmail.com", "941014745"));
        list.add(new SystemConnectedResponse("Shyam", "shyam@gmail.com", "9718004745"));
        list.add(new SystemConnectedResponse("Hari", "hari@gmail.com", "8728044760"));
        list.add(new SystemConnectedResponse("Kapil", "kapil@gmail.com", "8428444761"));
        list.add(new SystemConnectedResponse("Sunil", "sunil@gmail.com", "9654782410"));
        list.add(new SystemConnectedResponse("Manoj", "manoj@gmail.com", "9988455661"));
        list.add(new SystemConnectedResponse("Ishu", "ishu@gmail.com", "8219475641"));
        list.add(new SystemConnectedResponse("Biku", "biku@gmail.com", "7877041562"));
        list.add(new SystemConnectedResponse("Suresh", "suresh@gmail.com", "8728044760"));
        recyclerView = findViewById(R.id.recyclerSystemConnected);
        systemConnectedAdapter = new SystemConnectedAdapter(list,this);
        recyclerView.setAdapter(systemConnectedAdapter);
    }
}