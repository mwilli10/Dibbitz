package com.example.finalapp.dibbitz;

import android.Manifest;
import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by user on 04/10/2015.
 */
public class DibbitFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback  {

    private static final String ARG_DIBBIT_ID = "dibbit_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CALENDAR = 1;
    private static final int NUM_STARS = 5;

    private Dibbit mDibbit;
    private EditText mTitleField;
    private EditText mLocationField;
    private Date mDate;

    private Button mDateButton;
    private CheckBox mDoneCheckBox;
    private Button mTimeButton;
    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private File mPhotoFile;
    private RatingBar mRatingBar;
    private EditText mDescriptionBox;
    private Button mCalSaveButton;
    private Button mMapSaveButton;


    public static DibbitFragment newInstance(UUID dibbitId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIBBIT_ID, dibbitId);

        DibbitFragment fragment = new DibbitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID dibbitId = (UUID) getArguments().getSerializable(ARG_DIBBIT_ID);
        mDibbit = DibbitLab.get(getActivity()).getDibbit(dibbitId);
        mPhotoFile = DibbitLab.get(getActivity()).getPhotoFile(mDibbit);
        mDate = mDibbit.getDate();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dibbit, container, false);

        mTitleField = (EditText)v.findViewById(R.id.dibbit_title);
        mTitleField.setText(mDibbit.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDibbit.setTitle(s.toString());
            }

            // If dibbit is in calendar, update it
            @Override
            public void afterTextChanged(Editable s) {
                if (mDibbit.getCalStatus()) {
                    if (mDibbit.getEventId() != 0) {
                        updateCalendarEntry(mDibbit.getEventId());
                    }
                    //
                }
            }
        });

        mDateButton = (Button) v.findViewById(R.id.dibbit_date);
        updateDate();
        // Open date dialogue
        // If dibbit is in calendar, update it
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDibbit.getDate());
                dialog.setTargetFragment(DibbitFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);

                if (mDibbit.getCalStatus()) {
                    if (mDibbit.getEventId() != 0) {
                        updateCalendarEntry(mDibbit.getEventId());
                    }
                    //
                }
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.dibbit_time);
        updateTime();
        // Open time dialogue
        // If dibbit is in calendar, update it
        mTimeButton.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        mTimeButton.setText( selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, false);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//
//            }
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDibbit.getDate());
                dialog.setTargetFragment(DibbitFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);

                if (mDibbit.getCalStatus()) {
                    if (mDibbit.getEventId() != 0) {
                        updateCalendarEntry(mDibbit.getEventId());
                    }
                    //
                }
            }
        });

        mDoneCheckBox = (CheckBox) v.findViewById(R.id.dibbit_done);
        mDoneCheckBox.setChecked(mDibbit.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the dibbit's done property
                mDibbit.setDone(isChecked,getContext());

            }
        });

        // Allow rating bar to be set to half value
        mRatingBar = (RatingBar) v.findViewById(R.id.dibbit_difficulty_ratingBar);
        mRatingBar.setNumStars(NUM_STARS);
        mRatingBar.setStepSize(0.5f);
        mRatingBar.setRating((float)mDibbit.getDifficulty());
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mDibbit.setDifficulty((double) rating);
            }
        });



        mDescriptionBox = (EditText) v.findViewById(R.id.dibbit_description);
        mDescriptionBox.setText(mDibbit.getDescription());
        mDescriptionBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDibbit.setDescription(s.toString());
            }

            //If dibbit in calendar, update it
            @Override
            public void afterTextChanged(Editable s) {
                if (mDibbit.getCalStatus()){
                    if (mDibbit.getEventId() !=0 ) {
                        updateCalendarEntry(mDibbit.getEventId());
                    }
                }

            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.dibbit_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivityForResult(captureImage, REQUEST_PHOTO);
                                            }
                                        });

        mPhotoView = (ImageView) v.findViewById(R.id.dibbit_photo);
        updatePhotoView();

        // Update or remove dibbit to calendar based on user click
        mCalSaveButton = (Button) v.findViewById(R.id.btn_calendar_save);
        if (mDibbit.getCalStatus()){
            mCalSaveButton.setText(R.string.remove_from_cal_label);
        }else{
            mCalSaveButton.setText(R.string.save_to_cal_label);
        }
        mCalSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mDibbit.getCalStatus()) {
                    addEventToCalendar(getActivity());
                    mDibbit.setCalStatus(true);
                    mCalSaveButton.setText(R.string.remove_from_cal_label);
                }else {
                    if(mDibbit.getEventId() > 0 ) {
                        deleteCalendarEntry(mDibbit.getEventId());
                        mCalSaveButton.setText(R.string.save_to_cal_label);
                        mDibbit.setCalStatus(false);
                    }
                }

            }
        });
        // Update or remove dibbit to map based on user click
        mMapSaveButton = (Button) v.findViewById(R.id.btn_map_save);
        if (mDibbit.getMapStatus()){
            mMapSaveButton.setText(R.string.remove_from_map_label);
        }else{
            mMapSaveButton.setText(R.string.save_to_map_label);
        }
        mMapSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDibbit.getMapStatus()) {
                    Toast.makeText(getActivity(), "Saved to Map", Toast.LENGTH_SHORT).show();
                    mDibbit.setMapStatus(true);
                    mMapSaveButton.setText(R.string.remove_from_map_label);
                }else{
                    mDibbit.setMapStatus(false);
                    Toast.makeText(getActivity(), "Removed from Map", Toast.LENGTH_SHORT).show();
                    mMapSaveButton.setText(R.string.save_to_map_label);
                }
            }
        });
        mLocationField = (EditText)v.findViewById(R.id.dibbit_location_text);
        mLocationField.setText(mDibbit.getLocation());
        mLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDibbit.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }


        //Updates the date/time buttons
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDibbit.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_DATE);
            mDibbit.setDate(date);
            updateTime();
        }
        if (requestCode == REQUEST_PHOTO){
            updatePhotoView();
        }
    }

    private void updateDate() {
        mDateButton.setText(android.text.format.DateFormat.format("EEEE, MMM dd, yyyy", mDibbit.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(android.text.format.DateFormat.format("hh:mm a", mDibbit.getDate()));
    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
        }
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

    }

    // Access Dibbit attributes and insert event to calendar using Content Resolver and Values
    public void addEventToCalendar(Activity curActivity) {

        long calID = 1;
        Calendar calDate = Calendar.getInstance();

        long testDate = mDibbit.getDate().getTime();
        long endMillis = (testDate + 60 * 60 * 1000);

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.EVENT_COLOR, 2);
        values.put(CalendarContract.Events.DTSTART, testDate);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, mDibbit.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, mDibbit.getDescription());
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

        values.put("hasAlarm", 1); // 0 for false, 1 for true


            //DO CHECK PERMISSION
            Toast.makeText(getActivity(), "Added to Calendar", Toast.LENGTH_SHORT).show();

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_CALENDAR},
                        PERMISSION_REQUEST_CALENDAR);
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            int eventID = Integer.parseInt(uri.getLastPathSegment());
            mDibbit.setEventId(eventID);



        }


    // Access Dibbit attributes and update event in calendar using Content Resolver and Values
    private int updateCalendarEntry(int eventID) {
        int iNumRowsUpdated = 0;

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        Uri updateUri = null;

        Calendar calDate = Calendar.getInstance();

        long testDate = mDibbit.getDate().getTime();
        long endMillis = (testDate + 60 * 60 * 1000);


        values.put(CalendarContract.Events.EVENT_COLOR, 2);
        values.put(CalendarContract.Events.DTSTART, testDate);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, mDibbit.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, mDibbit.getDescription());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

        values.put("hasAlarm", 1); // 0 for false, 1 for true

        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getActivity().getContentResolver().update(updateUri, values, null, null);
        return iNumRowsUpdated;
    }

    private int deleteCalendarEntry(int eventID) {
        int iNumRowsUpdated = 0;
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getActivity().getContentResolver().delete(deleteUri, null, null);
        if (rows>0){
            Toast.makeText(getActivity(), "Deleted from Calendar",Toast.LENGTH_SHORT ).show();
            mDibbit.setCalStatus(false);
        }
        return iNumRowsUpdated;
    }


        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            // BEGIN_INCLUDE(onRequestPermissionsResult)
            if (requestCode == PERMISSION_REQUEST_CALENDAR) {
                // Request for camera permission.
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted. Start calendar preview Activity.
                    Toast.makeText(getActivity(),"Calendar permission was granted.",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission request was denied.
                    Toast.makeText(getActivity(), "Calendar permission was denied.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
            // END_INCLUDE(onRequestPermissionsResult)
        }

}


