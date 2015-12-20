package com.example.finalapp.dibbitz;

import android.net.wifi.p2p.WifiP2pManager;

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
    //private ParseACL mParseACL;



    public Dibbit() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        setDate(new Date());
        setTime(new Date());
        put("mUser", ParseUser.getCurrentUser());
        ParseACL parseACL = new ParseACL(ParseUser.getCurrentUser());
        setACL(parseACL);
        saveInBackground();

    }

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

    public void setDone(boolean isDone) {
        put("mIsDone",isDone);
        if (isDone){
            deleteEventually();
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