package com.example.furniture;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    private Button btnSignUp;
    private TextView txtLogin;
    private EditText txtEmail, txtPass;
    private Intent intent;
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(SignupActivity.this,"Feild are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this,"SignUp unsuccessful, Please Try Again!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this,"Error Ocurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
