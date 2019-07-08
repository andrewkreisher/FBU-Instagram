package com.example.instagram;

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
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        loginBtn  = findViewById(R.id.btnLogin);
        signupBtn = findViewById(R.id.btnSignup);



        //check if user is already logged in, if so move to home page instead
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            ;
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                //Toast.makeText(MainActivity.this, "button clicked",Toast.LENGTH_LONG).show();
                login(username, password);

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                signup(username, password);
            }
        });

    }

    private void login(String username, String password){
        //Todo
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Log.d("LoginActivity","Login succesful");
                    final Intent login2home = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(login2home);
                    finish();
                } else {
                    Log.e("LoginActivity","Login failure");
                    e.printStackTrace();
                }
            }

        });
    }

    private void signup(String username, String password){
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        //user.setEmail("email@example.com");
        // Set custom properties
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    //enter home
                    Log.d("LoginActivity","Signup succesful");
                    final Intent signup2home = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(signup2home);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    Log.e("LoginActivity","Signup failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
