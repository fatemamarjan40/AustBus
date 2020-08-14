package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText signInEmail, signInpassword;
private TextView signup;
private Button button;
ProgressBar pb2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signInEmail = findViewById(R.id.signINEmailId);
        signInpassword = findViewById(R.id.signInPasswordId);
        button = findViewById(R.id.signInButtonId);
        signup = findViewById(R.id.signInTest);
        button.setOnClickListener(this);
        signup.setOnClickListener(this);
        pb2= findViewById(R.id.progressbarId);
        pb2.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.signInButtonId:
                userLogin();
                break;
            case R.id.signInTest:
                Intent intent = new Intent(getApplicationContext(),signupActivity.class);
                startActivity(intent);
        }

    }

    private void userLogin() {
        String email = signInEmail.getText().toString().trim();
        String password = signInpassword.getText().toString().trim();
        if(email.isEmpty())
        {

            signInEmail.setError("Enter an email address");
            signInEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmail.setError("Enter a valid email address");
            signInEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            signInpassword.setError("Enter a password");
            signInpassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            signInpassword.setError("Minimum length should be 6");
            signInpassword.requestFocus();
            return;
        }
        pb2.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb2.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),DriverMapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
