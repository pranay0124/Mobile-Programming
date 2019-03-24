package com.example.shoppingcart;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText email;
    private EditText password;
    private Button signup;
    private TextView toLoginPage;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        userName = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.user_mail);
        password = (EditText) findViewById(R.id.user_password);
        signup = (Button) findViewById(R.id.button_signup);
        toLoginPage = (TextView) findViewById(R.id.button_loginPage);

        signup.setOnClickListener(this);
        toLoginPage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == signup) {
            registerUser();
        }

        if(v == toLoginPage) {
            //will open login page
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void registerUser() {
        String person_name = userName.getText().toString();
        String person_email = email.getText().toString();
        String person_password = password.getText().toString();

        if(TextUtils.isEmpty(person_name)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(person_email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(person_password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(person_email, person_password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //user has successfully signed up
                            //start the login page here
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                            Toast.makeText(MainActivity.this, "successfully registered", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
