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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {
 private Button b1;
 private TextView t1;
 private EditText e1,e2;
    private FirebaseAuth mAuth;
    private ProgressBar pb1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        b1 = findViewById(R.id.signUpButtonId);
        t1 = findViewById(R.id.signUpTest);
        b1.setOnClickListener(this);
        t1.setOnClickListener(this);
        e1 = findViewById(R.id.signUpEmailId);
        e1.setOnClickListener(this);
        e2 = findViewById(R.id.signUpPasswordId);
        e2.setOnClickListener(this);
        pb1= findViewById(R.id.progressbarId);
        pb1.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.signUpButtonId:
                userRegister();
                Intent bintent = new Intent(signupActivity.this,DriverMapsActivity.class);
                startActivity(bintent);

                break;
            case R.id.signUpTest:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
        }
    }

    private void userRegister() {
        String email = e1.getText().toString().trim();
        String password = e2.getText().toString().trim();
        if(email.isEmpty())
        {

       e1.setError("Enter an email address");
           e1.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            e1.setError("Enter a valid email address");
           e1.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            e2.setError("Enter a password");
            e2.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            e2.setError("Minimum length should be 6");
            e2.requestFocus();
            return;
        }
        pb1.setVisibility(View.VISIBLE);
mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        pb1.setVisibility(View.GONE);
        if (task.isSuccessful()) {
          Toast.makeText(getApplicationContext(),"Register is successful",Toast.LENGTH_SHORT).show();

        } else {
            // If sign in fails, display a message to the user.
            if(task.getException() instanceof FirebaseAuthUserCollisionException)
            {
                Toast.makeText(getApplicationContext(),"User is already Register",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error :"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }
});
    }
}
