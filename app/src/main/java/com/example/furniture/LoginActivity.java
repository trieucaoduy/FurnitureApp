package com.example.furniture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtSignUp;
    private EditText txtEmail, txtPass;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmailLogin);
        txtPass = findViewById(R.id.txtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this,"You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Please Login!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pwd = txtPass.getText().toString();
                if(email.isEmpty()) {
                    txtEmail.setError("Please enter your email!");
                    txtEmail.requestFocus();
                }
                else if(pwd.isEmpty()) {
                    txtPass.setError("Please enter your password!");
                    txtPass.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"Feild are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this,"Login Error, Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intoHome = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intoHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Ocurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intentSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
