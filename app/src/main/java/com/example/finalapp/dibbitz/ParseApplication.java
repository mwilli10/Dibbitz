package com.example.finalapp.dibbitz;

/**
 * Created by KJ on 12/8/15.
 */

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "jNMKS9zcvkVoRIcGxCUX7ANncRFlhK9VhD0sBhcr", "hGutVVJezPf9aSz1r7Qtz7UlPjzuWZR8mQeS4R3x");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseObject.registerSubclass(Dibbit.class);

        //Makes the default private
        defaultACL.setPublicReadAccess(false);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}