package com.example.phoenix.xlambton;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phoenix.xlambton.models.Session;

public class MainActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    Button loginBtn;
    public static boolean appOpenFlag;
    Activity asdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appOpenFlag = true;
        if (Session.loggedIn == true) {
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(intent);
            finish();
        }

        loginBtn = (Button) findViewById(R.id.buttonLogin);
        emailText = (EditText) findViewById(R.id.editTextEmail);
        passwordText = (EditText) findViewById(R.id.editTextPassword);
        
        

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.equals("admin@gmail.com") && password.equals("password")) {
                    Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                    startActivity(intent);
                    Session.loggedIn = true;
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
