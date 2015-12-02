package com.example.finalapp.dibbitz;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by user on 10/6/15.
 */
@ParseClassName("Dibbit")
public class Dibbit extends ParseObject{
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean mIsDone;


    public Dibbit() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        setDate(new Date());
        saveInBackground();
        //mTime = new Date();
    }

    //There's no need to save the UUId because Parse makes it's own IDs
    //Pretty fuckin clutch
    public UUID getId() {
        return mId;
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


    public String getDateString() {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mDate);
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
        saveInBackground();
    }


    public String getPhotoFileName() {
        return "IMG"+ getId().toString()+".jpg";

    }


}