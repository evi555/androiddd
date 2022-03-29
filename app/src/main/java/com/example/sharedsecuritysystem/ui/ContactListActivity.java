package com.example.sharedsecuritysystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.sharedsecuritysystem.Adapter.ContactAdapter;
import com.example.sharedsecuritysystem.R;
import com.example.sharedsecuritysystem.Response.ContactResponse;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        list = new ArrayList<ContactResponse>();
        list.add(new ContactResponse("Yash", "yash@gmail.com", "901014745"));
        list.add(new ContactResponse("Aniket", "anku@gmail.com", "901014741"));
        list.add(new ContactResponse("Vivek", "vivek@gmail.com", "98014745"));
        list.add(new ContactResponse("Ram", "ram@gmail.com", "941014745"));
        list.add(new ContactResponse("Shyam", "shyam@gmail.com", "9718004745"));
        list.add(new ContactResponse("Hari", "hari@gmail.com", "8728044760"));
        list.add(new ContactResponse("Kapil", "kapil@gmail.com", "8428444761"));
        list.add(new ContactResponse("Sunil", "sunil@gmail.com", "9654782410"));
        list.add(new ContactResponse("Manoj", "manoj@gmail.com", "9988455661"));
        list.add(new ContactResponse("Ishu", "ishu@gmail.com", "8219475641"));
        list.add(new ContactResponse("Biku", "biku@gmail.com", "7877041562"));
        list.add(new ContactResponse("Suresh", "suresh@gmail.com", "8728044760"));
        recyclerView = findViewById(R.id.recyclerContactList);
        contactAdapter = new ContactAdapter(list,this);
        recyclerView.setAdapter(contactAdapter);

    }
}