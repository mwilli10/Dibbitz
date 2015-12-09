package com.example.finalapp.dibbitz;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;


/**
 * Created by user on 10/8/2015.
 */
public class DibbitLab {
    private static DibbitLab sDibbitLab;
    private Context mContext;
    private boolean mDidDataSetChange=false;

    private List<Dibbit> mDibbits;

    private DibbitLab(Context context) {
        mContext = context;
        mDibbits = new ArrayList<>();
    }




//        for(int i = 0; i < 100; i++) {
//            Dibbit dibbit = new Dibbit();
//            dibbit.setTitle("Dibbit #" + i);
//            dibbit.setSolved(i%2 == 0);  //every other one
//            mDibbits.add(dibbit);
//        }


    public static DibbitLab get(Context context) {
        if (sDibbitLab == null) {
            sDibbitLab = new DibbitLab(context);
        }
            return sDibbitLab;
        }

    public void updateDibbits(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Dibbit");

        // Restrict to cases where the author is the current user.
        query.whereEqualTo("mUser", ParseUser.getCurrentUser());

        // Run the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> dibbitList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of dibbits
                    // and notify the adapter
                    mDibbits.clear();
                    //mDibbits.addAll((List<Dibbit>) dibbitList);

                    for (ParseObject dibbit : dibbitList) {
                        System.out.println("Got one");
                        mDibbits.add((Dibbit) dibbit);
                    }

                    //mDidDataSetChange = true;
                    //THIS THE SHIT THAT AIN'T WORKING RIGHT
                    //(RecyclerView.Adapter) DLF.getAdapter().notifyDataSetChanged();
                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });

    }

    public boolean didDataSetChange() {
        return mDidDataSetChange;
    }

    public List<Dibbit> getDibbits() {
        return mDibbits;
    }

    public Dibbit getDibbit(UUID id) {
        for(Dibbit dibbit : mDibbits) {
            if(dibbit.getId().equals(id)) {
                return dibbit;
            }
        }
        return null;
    }

    public void addDibbit(Dibbit c){
        mDibbits.add(c);
    }

    public void deleteDibbit(Dibbit c){
        mDibbits.remove(c);
    }

    public File getPhotoFile(Dibbit dibbit){

        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir ==null){
            return null;
        }
        return new File(externalFilesDir, dibbit.getPhotoFileName());
    }

}

