package com.example.finalapp.dibbitz;

/**
 * Created by KJ on 12/8/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSignupActivity extends AppCompatActivity {
    // Declare Variables
    Button loginbutton;
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

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "jNMKS9zcvkVoRIcGxCUX7ANncRFlhK9VhD0sBhcr", "hGutVVJezPf9aSz1r7Qtz7UlPjzuWZR8mQeS4R3x");
        ParseObject.registerSubclass(Dibbit.class);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username_box);
        password = (EditText) findViewById(R.id.password_box);

        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login_button);
        signup = (Button) findViewById(R.id.signup_button);


        // Login Button Click Listener
        loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();




                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            System.out.println("Inside done()");
                            if ((e== null) &( user != null)) {
                                // Valid user... Let them in the app
                                startActivity(new Intent(LoginSignupActivity.this, MainActivity.class));
                                Toast.makeText(getApplicationContext(),
                                        ("Welcome back " + usernametxt),
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "No such user exists, please signup or try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });

        // Sign up Button Click Listener
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") || passwordtxt.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}