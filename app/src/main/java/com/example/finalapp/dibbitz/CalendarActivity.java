package com.example.finalapp.dibbitz;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Morgan on 12/2/15.
 */
public class CalendarActivity extends Fragment {
    private ImageButton mCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("content://com.android.calendar/time/")));
        View v = inflater.inflate(R.layout.activity_calendar, container, false);

        mCalendar = (ImageButton) v.findViewById(R.id.camera_img);

        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent calIntent = new Intent(Intent.ACTION_INSERT);
//                calIntent.setData(CalendarContract.Events.CONTENT_URI);
//                startActivity(calIntent);
                startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("content://com.android.calendar/time/")));

            }
        });

        return v;

    }

//
//    @Override
//    public void onResume() {
//        super.onResume();
//        startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("content://com.android.calendar/time/")));
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
}

