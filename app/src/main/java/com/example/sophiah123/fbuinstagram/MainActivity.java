package com.example.sophiah123.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        usernameInput = findViewById(R.id.username_et);
        passwordInput = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);
        signUpBtn = findViewById(R.id.signUp_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        /*signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                signup(username, password);
            }
        }); */

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");

                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }

}
