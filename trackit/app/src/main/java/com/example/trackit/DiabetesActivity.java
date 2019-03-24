package com.example.trackit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DiabetesActivity extends AppCompatActivity {

    private EditText user_diabetes;
    private Button submit_diabetes;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);

        user_diabetes = (EditText) findViewById(R.id.enterDiabetes);
        submit_diabetes = (Button) findViewById(R.id.button_diabetes_submit);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getName()).child("Diabetes");


        submit_diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiabetestoDB();
            }
        });
    }

    private void DiabetestoDB() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        String value = user_diabetes.getText().toString();
        String key = mRef.push().getKey(); // this will create a new unique key

        Map<String, Object> diabetes_map = new HashMap<>();
        diabetes_map.put("value", value);
        diabetes_map.put("timestamp", strDate);
        mRef.child(key).setValue(diabetes_map);

        Toast.makeText(DiabetesActivity.this, "Value Stored", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DiabetesActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
