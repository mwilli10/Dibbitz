package com.example.finalapp.dibbitz;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Morgan on 12/13/15.
 */
public class PermissionRuntime {

    Activity context;
    private View mLayout;
    private static final int PERMISSION_REQUEST_CALENDAR = 1;


    public PermissionRuntime(Activity context,View mLayout){
        this.context = context;
        this.mLayout = mLayout;
    }


    public void requestPermissionCalendar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Calendar access is required ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.requestPermissions(context,
                                new String[]{android.Manifest.permission.WRITE_CALENDAR},
                                PERMISSION_REQUEST_CALENDAR);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
