package com.example.trackit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText person_name, person_password;
    private Button login;
    private TextView toSignupPage;
    private ProgressDialog loadingBar;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        person_name = (EditText) findViewById(R.id.login_name);
        person_password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.button_login);
        toSignupPage = (TextView) findViewById(R.id.button_toSignupPage);

        loadingBar = new ProgressDialog(this);
        Paper.init(this);
        mRef = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        final String name = person_name.getText().toString();
        final String password = person_password.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //progress bar
            loadingBar.setMessage("User Logging in....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            //to keep user logged in


            //to add user details to db
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Users").child(name).exists()){
                        Users userData = dataSnapshot.child("Users").child(name).getValue(Users.class);
                        if(userData.getName().equals(name)){
                            if(userData.getPassword().equals(password)){
                                Prevalent.currentOnlineUser = userData;
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "User not Registered", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
