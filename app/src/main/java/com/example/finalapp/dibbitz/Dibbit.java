package com.example.finalapp.dibbitz;

import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AlertDialog;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseACL;


import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Comparator;


/**
 * Created by user on 10/6/15.
 */
@ParseClassName("Dibbit")
public class Dibbit extends ParseObject implements Comparable<Dibbit> {
    private UUID mId;

    @Override
    public int compareTo(Dibbit another) {
        return (this.getDate().compareTo(another.getDate()));
    }

    // Don't actually need these anymore, but it's dope to see what's there
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean mIsDone;
    private double mDifficulty;
    private String mDescription;
    private ParseUser mUser;
    private boolean mIsPublic;
    //private ParseACL mParseACL;



    public Dibbit() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        put("mUser", ParseUser.getCurrentUser());

//        ParseACL parseACL = getACL();
//        if (!isPublic()) {
//
//        }
       // ParseACL parseACL = checkAndUpdatePublicness();

        saveInBackground();

    }
;
    //There's no need to save the UUId because Parse makes it's own IDs
    //Pretty fuckin clutch
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
    public Date getTime() {
        return getDate("mTime");
    }


    public void setTime(Date time) {
        put("mTime", time);
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
                            //REFRESH HERE IS BETTER
                           // DibbitListFragment dlf = new DibbitListFragment();
                            //dlf.updateUI();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            //REFRESH HERE IS OKAY

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

       /* public String getDateString() {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mDate);
    }*/



}