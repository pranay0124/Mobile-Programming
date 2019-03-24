package com.example.trackit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    ImageButton login;
    ImageButton signup;
//    private ProgressDialog loadingBar;
//    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){

        }
        setContentView(R.layout.activity_main);

        login = (ImageButton) findViewById(R.id.button_login);
        signup = (ImageButton) findViewById(R.id.button_signup);
//        loadingBar = new ProgressDialog(this);
//        Paper.init(this);
//        mRef = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

//        String username_key = Paper.book().read(Prevalent.username_key);
//        String userpassword_key = Paper.book().read(Prevalent.userpassword_key);
//        if(username_key != "" && userpassword_key != "") {
//            if(!TextUtils.isEmpty(username_key) && !TextUtils.isEmpty(userpassword_key)) {
//                allowAccess(username_key, userpassword_key);
//                loadingBar.setMessage("User Logging in....");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//            }
//        }
    }

//    private void allowAccess(final String name, final String password) {
//        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child("Users").child(name).exists()){
//                    Users userData = dataSnapshot.child("Users").child(name).getValue(Users.class);
//                    if(userData.getName().equals(name)){
//                        if(userData.getPassword().equals(password)){
//                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
//                            loadingBar.dismiss();
//                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "User not Registered", Toast.LENGTH_LONG).show();
//                    loadingBar.dismiss();
//
//                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
