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
public class Dibbit  extends ParseObject{
    private UUID mId;
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
//    public void setId(UUID uuid){
//        put("mId",uuid.toString());
//    }
    public UUID getId() {
        return mId;
    }


    public String getTitle() {
        return getString(mTitle);
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


    public String getDateString() {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mDate);
    }

    public Date getTime() { return mTime;}


    public void setTime(Date time) {mTime = time;}


    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean isDone) {
        mIsDone = isDone;
    }
}
