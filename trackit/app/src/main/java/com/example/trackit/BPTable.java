package com.example.trackit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BPTable extends AppCompatActivity {
    public ListView list;
    private DatabaseReference mRef;
    public ArrayList<Table> arraylist = new ArrayList<>();
    public ArrayAdapter<Table> adapter;
    public TextView Table_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bptable);

        Table_Title = (TextView) findViewById(R.id.bpTable_title);

        Intent intent = getIntent();
        String type = intent.getExtras().getString("Table_Type");
        Log.i(type, "onCreate: Intent return String");

        if(type.equals("1")) {
            Table_Title.setText("BP Table");
            mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getName()).child("BP");
        }
        if(type.equals("2")) {
            Table_Title.setText("Diabetes Table");
            mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getName()).child("Diabetes");
        }

        adapter = new ArrayAdapter<Table>(this, android.R.layout.simple_list_item_1, arraylist);
        list = (ListView) findViewById(R.id.bpTable_list);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userdata = dataSnapshot.getChildren();
                for(DataSnapshot child : userdata){
                    Table userinfo = child.getValue(Table.class);
                    adapter.add(userinfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

