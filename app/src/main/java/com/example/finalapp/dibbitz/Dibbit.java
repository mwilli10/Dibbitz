package com.example.finalapp.dibbitz;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by user on 10/6/15.
 */
//@ParseClassName("Dibbit")
public class Dibbit{
    private UUID mId;     //The ID is now a String so it can be used in the DB
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean mIsDone;


    public Dibbit(){
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        mDate = new Date();
        mTime = new Date();
    }
  //  public void setId(UUID uuid){
   //     put("mId",uuid.toString());
   // }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean isDone) {
        mIsDone = isDone;
    }

    /*
    public String getTitle() {
        return getString("mTitle");
    }

    public void setTitle(String title) {
        put("mTitle",title);
    }

    public Date getDate() {
        return getDate("mDate");
    }

    public void setDate(Date date) {
        put("mDate",date);
    }

    public Date getTime() { return getDate("mTime");}

    public void setTime(Date time) {
        put("mTime",time);
    }

    public boolean isDone() {
        return getBoolean("mIsDone");
    }

    public void setDone(boolean isDone) {
        put("mIsDone",isDone);
    }

    public String getDateString() {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mDate);
    }
    */
}
