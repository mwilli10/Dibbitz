package com.example.finalapp.dibbitz;

/**
 * Created by KJ on 12/8/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSignUpActivity extends AppCompatActivity {
    // Declare Variables
    Button loginsignupbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_login_signup);


        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username_box);
        password = (EditText) findViewById(R.id.password_box);

        // Locate Buttons in main.xml
        loginsignupbutton = (Button) findViewById(R.id.login_signup_button);



        // Login Button Click Listener
        loginsignupbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                //If nothing is in either textbox
                if (usernametxt.equals("") || passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();
                }

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if ((e== null) &( user != null)) {
                                // Valid user... Let them in the app
                                startActivity(new Intent(LoginSignUpActivity.this, MainActivity.class));
                                Toast.makeText(getApplicationContext(), ("Welcome back " + usernametxt), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else {
                                //Sign them up
                                user = new ParseUser();
                                user.setUsername(usernametxt);
                                user.setPassword(passwordtxt);
                                user.signUpInBackground(new SignUpCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            // Show a simple Toast message upon successful registration
                                            Toast.makeText(getApplicationContext(),
                                                    "Successfully Signed up! Logging in now!",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginSignUpActivity.this, MainActivity.class));
                                            Toast.makeText(getApplicationContext(),
                                                    ("Welcome " + usernametxt),
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Login Error.  Double check username/password and try again!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                });
            }
        });

    }


}