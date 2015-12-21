package com.example.finalapp.dibbitz.model;

import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseACL;



import java.util.Date;
import java.util.UUID;



/**
 * Created by user on 10/6/15.
 */
@ParseClassName("Dibbit")
public class Dibbit extends ParseObject {
    private UUID mId;


    public Dibbit() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        put("mUser", ParseUser.getCurrentUser());
        saveInBackground();
    }

    public UUID getId() {
        return mId;
    }

    public void makePublic(){
        ParseACL parseACL = getACL();
        parseACL.setPublicReadAccess(true);
        parseACL.setPublicWriteAccess(true);
        setACL(parseACL);
        saveInBackground();

        put("mIsPublic", (parseACL.getPublicReadAccess() && parseACL.getPublicWriteAccess()));
        saveInBackground();
    }

    public ParseACL checkAndUpdatePublicness(){
        if (isPublic()) {
            makePublic();

        }

        else{
        ParseACL parseACL = getACL();
            parseACL.setPublicWriteAccess(false);
            parseACL.setPublicReadAccess(false);
            setACL(parseACL);
            saveInBackground();

        }
        return getACL();
    }
    public boolean isPublic() {
        return getBoolean("mIsPublic");
    }

    public String getDescription() {
        return getString("mDescription");
    }

    public void setDescription(String description) {
        put("mDescription",description);
        saveInBackground();
    }



    public double getDifficulty() {
        return getDouble("mDifficulty");
    }

    public void setDifficulty(double difficulty) {
        put("mDifficulty", difficulty);
        saveInBackground();
    }

    public int getEventId(){
        return getInt("mEventId");
    }
    public void setEventId(int eventID){
        put("mEventId", eventID);
        saveInBackground();
    }

    public String getTitle() {
        return getString("mTitle");
    }

    public void setTitle(String title) {
        put("mTitle",title);
        saveInBackground();
    }

    public Date getDate() {
        if (getDate("mDate") == null){
            put ("mDate", new Date());
        }
        return getDate("mDate");
    }

    public void setDate(Date date) {
        put("mDate",date);
        saveInBackground();
    }

    public String getLocation() {
        return getString("mLocation");
    }

    public void setLocation(String location) {
        put("mLocation", location);
        saveInBackground();
    }

    public boolean isDone() {
        return getBoolean("mIsDone");
    }

    public void setDone(boolean isDone, Context context) {
        put("mIsDone", isDone);


        if (isDone) {
           //
            new AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("You sure you want to click this?  You'll never see this dibbit again")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            deleteEventually();

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }


        saveInBackground();
    }


    public String getPhotoFileName() {
        return "IMG"+ getId().toString()+".jpg";

    }

    public boolean getCalStatus() {
        return getBoolean("mCalStatus");
    }


    public void setCalStatus( boolean saveToCal) {
        put("mCalStatus", saveToCal);
        saveInBackground();
    }
    public boolean getMapStatus() {
        return getBoolean("mMapStatus");
    }


    public void setMapStatus( boolean saveToMap) {
        put("mMapStatus", saveToMap);
        saveInBackground();
    }


}