package com.example.shoppingcart;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button login;
    private TextView toSignUpPage;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.button_login);
        toSignUpPage = (TextView) findViewById(R.id.button_signupPage);

        login.setOnClickListener(this);
        toSignUpPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            userLogin();
        }
        if(view == toSignUpPage){
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void userLogin() {
        String user_email = loginEmail.getText().toString();
        String user_password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(user_email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(user_password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("User logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user gets logged in and products page is displayed
                            Intent intent1 = new Intent(LoginActivity.this, ProductList.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(LoginActivity.this, "incorrect details", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
