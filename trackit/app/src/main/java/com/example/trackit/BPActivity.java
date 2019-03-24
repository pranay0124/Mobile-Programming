package com.example.trackit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BPActivity extends AppCompatActivity {

    private EditText user_bp;
    private Button submit_bp;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);

        user_bp = (EditText) findViewById(R.id.enterBP);
        submit_bp = (Button) findViewById(R.id.button_bp_submit);

//        System.out.println(Prevalent.currentOnlineUser.getName());
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getName()).child("BP");


        submit_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BPtoDB();
            }
        });
    }

    private void BPtoDB() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        System.out.println(strDate);

        String value = user_bp.getText().toString();
        String key = mRef.push().getKey(); // this will create a new unique key

        Map<String, Object> bp_map = new HashMap<>();
        bp_map.put("value", value);
        bp_map.put("timestamp", strDate);
        mRef.child(key).setValue(bp_map);

        Toast.makeText(BPActivity.this, "Value Stored", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(BPActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
