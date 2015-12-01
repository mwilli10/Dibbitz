package com.example.finalapp.dibbitz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

/**
 * Created by user on 04/10/2015.
 */
public class DibbitFragment extends Fragment {

    private static final String ARG_DIBBIT_ID = "dibbit_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Dibbit mDibbit;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mDoneCheckBox;

    private Button mTimeButton;

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

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.dibbit_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDibbit.getDate());
                dialog.setTargetFragment(DibbitFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.dibbit_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDibbit.getDate());
                dialog.setTargetFragment(DibbitFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mDoneCheckBox = (CheckBox) v.findViewById(R.id.dibbit_done);
        mDoneCheckBox.setChecked(mDibbit.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the dibbit's done property
                mDibbit.setDone(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDibbit.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mDibbit.setDate(date);
            updateTime();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_item_delete_dibbit:
                if (mDibbit != null)
                    Toast.makeText(getActivity(), "Deleting this dibbit", Toast.LENGTH_SHORT).show();
                DibbitLab.get(getActivity()).deleteDibbit(mDibbit);
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDate() {

        mDateButton.setText(android.text.format.DateFormat.format("EEEE, MMM dd, yyyy", mDibbit.getDate()).toString());
    }

    private void updateTime() {
        mTimeButton.setText(android.text.format.DateFormat.format("hh:mm a", mDibbit.getDate()));
    }

}



//
//package com.bignerdranch.android.criminalintent;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.v4.app.FragmentManager;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.text.format.DateFormat;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//
//import java.util.Date;
//import java.util.UUID;
//
//
//public class DibbitFragment extends Fragment {
//
//    private static final String ARG_CRIME_ID = "crime_id";
//    private static final String DIALOG_DATE = "DialogDate";
//    private static final String DIALOG_TIME = "DialogTime";
//    private static final int REQUEST_DATE = 0;
//    private static final int REQUEST_TIME = 1;
//
//    private Dibbit mDibbit;
//    private EditText mTitleField;
//    private Button mDateButton;
//    private CheckBox mSolvedCheckBox;
//    private Button mTimeButton;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
//        mDibbit = DibbitLab.get(getActivity()).getCrime(crimeId);
//    }
//
//    public static DibbitFragment newInstance(UUID crimeId){
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_CRIME_ID, crimeId);
//
//        DibbitFragment fragment = new DibbitFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_dibbit, container,false);
//        mTitleField = (EditText)v.findViewById(R.id.crime_title);
//        mTitleField.setText(mDibbit.getTitle());
//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { // Intentionally blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mDibbit.setTitle(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {  // Intentionally blank
//            }
//        });
//
//        mDateButton = (Button) v.findViewById(R.id.crime_date);
////        mDateButton.setText(android.text.format.DateFormat.format("Month day, Year.", mDibbit.getDate()));
//        updateDate();
//
//        mDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager manager = getFragmentManager();
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mDibbit.getDate());
//                dialog.setTargetFragment(DibbitFragment.this, REQUEST_DATE);
//                dialog.show(manager, DIALOG_DATE);
//
//            }
//        });
//
//
//        mTimeButton = (Button) v.findViewById(R.id.crime_time);
////        mDateButton.setText(android.text.format.DateFormat.format("Month day, Year.", mDibbit.getDate()));
//        updateTime();
//
//        mTimeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager manager = getFragmentManager();
//                TimePickerFragment dialog = TimePickerFragment.newInstance(mDibbit.getDate());
//                dialog.setTargetFragment(DibbitFragment.this, REQUEST_TIME);
//                dialog.show(manager, DIALOG_TIME);
//            }
//
//        });
//
//
//
//
//        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
//        mSolvedCheckBox.setChecked(mDibbit.isSolved());
//        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //Set the crime's solved property
//                mDibbit.setSolved(isChecked);
//            }
//        });
//        return v;
//
//    }
//
//    private void editDateDialog() {
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        DatePickerFragment dialog = DatePickerFragment.newInstance(mDibbit.getDate());
//        dialog.setTargetFragment(DibbitFragment.this, REQUEST_DATE);
//        dialog.show(manager, null);
//    }
//
//    private void editTimeDialog() {
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        TimePickerFragment dialog = TimePickerFragment.newInstance(mDibbit.getDate());
//        dialog.setTargetFragment(DibbitFragment.this, REQUEST_TIME);
//        dialog.show(manager, null);
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (resultCode != Activity.RESULT_OK){
//            return;
//        }
//        if (requestCode == REQUEST_DATE){
//            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mDibbit.setDate(date);
//            updateDate();
//        }
//        if(requestCode == REQUEST_TIME){
//            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            mDibbit.setTime(date);
//            updateTime();
//        }
//
//
//    }
//
//    private void updateDate() {
//        mDateButton.setText(DateFormat.format("MM/dd/yyy", mDibbit.getDate()));
//    }
//    private void updateTime(){
//        mTimeButton.setText(DateFormat.format("hh:mm a", mDibbit.getTime()));
//    }
//}