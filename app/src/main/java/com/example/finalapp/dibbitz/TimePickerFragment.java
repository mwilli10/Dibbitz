package com.example.finalapp.dibbitz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by user on 17/10/15.
 */


public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.example.finalapp.dibbitz.time";

    private Date mDate;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate.setHours(hourOfDay);
                mDate.setMinutes(minute);
                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }
}



//public class TimePickerFragment extends DialogFragment {
//
//    public static final String EXTRA_TIME = "com.example.finalapp.dibbitz.time";
//
//    private static final String ARG_TIME = "time";
//
//    private TimePicker mTimePicker;
//    private int year, month, day;
//    private Date date;
//
//    public static TimePickerFragment newInstance(Date date) {
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_TIME, date);
//
//        TimePickerFragment fragment = new TimePickerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        date = (Date) getArguments().getSerializable(ARG_TIME);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        int hour = calendar.get(Calendar.HOUR);
//        int minute = calendar.get(Calendar.MINUTE);
//
//
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
//
//        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
//        mTimePicker.setHour(hour);
//        mTimePicker.setMinute(minute);
//
//        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hour, int minute) {
//                date = new GregorianCalendar(year, month, day, hour, minute).getTime();
//                getArguments().putSerializable(EXTRA_TIME, date);
//            }
//        });
//
//        return new AlertDialog.Builder(getActivity())
//                .setView(v)
//                .setTitle(R.string.time_picker_title)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        int hour = mTimePicker.getHour();
//                        int minute = mTimePicker.getMinute();
//
//                        Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();
//                        sendResult(Activity.RESULT_OK, date);
//                    }
//                })
//                .create();
//    }
//
//    private void sendResult(int resultCode, Date date) {
//        if (getTargetFragment() == null) {
//            return;
//        }
//
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_TIME, date);
//
//        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
//    }
//}
