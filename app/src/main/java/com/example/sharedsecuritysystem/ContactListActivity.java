package com.example.sharedsecuritysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import com.example.sharedsecuritysystem.contact_adapter.ContactAdapter;
import com.example.sharedsecuritysystem.contact_adapter.ContactResponse;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    ArrayList<ContactResponse> list;
//    ArrayList<ContactResponse> list2;

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
        recyclerView = findViewById(R.id.recycler1);
        contactAdapter = new ContactAdapter(list,this);
        recyclerView.setAdapter(contactAdapter);

//        list2 = new ArrayList<ContactResponse>();
//        list2.add(new ContactResponse("Yash Pal", "yash@gmail.com", "901014745"));
//        list2.add(new ContactResponse("Anku", "anku@gmail.com", "901014741"));
//        list2.add(new ContactResponse("Vivek", "vivek@gmail.com", "98014745"));
//        list2.add(new ContactResponse("Ram", "ram@gmail.com", "941014745"));
//        list2.add(new ContactResponse("Shyam", "shyam@gmail.com", "9718004745"));
//        list2.add(new ContactResponse("Hari", "hari@gmail.com", "8728044760"));
//        list2.add(new ContactResponse("Kapil", "kapil@gmail.com", "8428444761"));
//        list2.add(new ContactResponse("Sunil", "sunil@gmail.com", "9654782410"));
//        list2.add(new ContactResponse("Manoj", "manoj@gmail.com", "9988455661"));
//        list2.add(new ContactResponse("Ishu", "ishu@gmail.com", "8219475641"));
//        list2.add(new ContactResponse("Biku", "biku@gmail.com", "7877041562"));
//        list2.add(new ContactResponse("Suresh", "suresh@gmail.com", "8728044760"));
//        recyclerView = findViewById(R.id.recycler1);
//        contactAdapter = new ContactAdapter(list2,this);
//        recyclerView.setAdapter(contactAdapter);

    }
}