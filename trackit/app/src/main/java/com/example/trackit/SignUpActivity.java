package com.example.trackit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText person_name, person_email, person_password, person_age;
    private Button person_gender, submit;
    private RadioGroup genders;
    private TextView toLoginPage;
    private ProgressDialog loadingbar;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        person_name = (EditText) findViewById(R.id.user_name);
        person_email = (EditText) findViewById(R.id.user_mail);
        person_password = (EditText) findViewById(R.id.user_password);
        person_age = (EditText) findViewById(R.id.user_age);
        submit = (Button) findViewById(R.id.button_signup);
        toLoginPage = (TextView) findViewById(R.id.button_toLoginPage);

        genders = (RadioGroup) findViewById(R.id.radioBtn_group);
        int selectedId = genders.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        person_gender = (RadioButton) findViewById(selectedId);

        loadingbar = new ProgressDialog(this);
        mRef = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    public void createAccount() {
        final String name = person_name.getText().toString();
        final String email = person_email.getText().toString();
        final String password = person_password.getText().toString();
        final String age = person_age.getText().toString();
        // final String gender = (String) person_gender.getText();

        //To check whether the data is given or not
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //progress bar
            loadingbar.setMessage("Creating Account....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            //user validation and creation in database
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!(dataSnapshot.child("Users").child(name).exists())) {
                        HashMap<String, Object> userDataMap = new HashMap<>();
                        userDataMap.put("name", name);
                        userDataMap.put("email", email);
                        userDataMap.put("password", password);
                        userDataMap.put("age", age);
                        // userDataMap.put("gender", gender);
                        Log.i("userDataMap", "onDataChange: haspmap entered");
                        mRef.child("Users").child(name).updateChildren(userDataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("DB", "onComplete: data entered to database");
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                            loadingbar.dismiss();
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                                            loadingbar.dismiss();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
