package com.example.finalapp.dibbitz;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
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
    private boolean mDidDataSetChange = false;
    List<Dibbit> mapDibbits = new ArrayList<>();
    List<String> map = new ArrayList<>();

    private List<Dibbit> mDibbits;

    private DibbitLab(Context context) {
        mContext = context;
        mDibbits = new ArrayList<>();
    }


    public static DibbitLab get(Context context) {
        if (sDibbitLab == null) {
            sDibbitLab = new DibbitLab(context);
        }
        return sDibbitLab;
    }

    public void updateDibbits() {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Dibbit");
        query1.whereEqualTo("mUser", ParseUser.getCurrentUser());

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Dibbit");
        query2.whereEqualTo("mIsPublic",true);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        // Run the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> dibbitList, ParseException e) {

                if (e == null) {
                    // If there are results, update the list of dibbits
                    // and notify the adapter
                    mDibbits.clear();


                    for (ParseObject dibbit : dibbitList) {
                        mDibbits.add((Dibbit) dibbit);
                        if(((Dibbit) dibbit).isPublic()){
                            ((Dibbit) dibbit).makePublic();
                            //and change background to purple

                        }
                    }

                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });


    }


    public List<Dibbit> getMapDibbits() {
        return mapDibbits;

    }

    public List<Dibbit> getDibbits() {
        return mDibbits;
    }

    public Dibbit getDibbit(UUID id) {
        for (Dibbit dibbit : mDibbits) {
            if (dibbit.getId().equals(id)) {
                return dibbit;
            }
        }
        return null;
    }

    public void addDibbit(Dibbit c) {
        mDibbits.add(c);
    }


    public File getPhotoFile(Dibbit dibbit) {

        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, dibbit.getPhotoFileName());
    }


    public List getLocations() {
        List<Pair<String, String>> locations = new ArrayList<>();
        for (Dibbit dibbit : mDibbits) {
            if (dibbit.getMapStatus() == true && dibbit.getLocation() != "" && dibbit.getLocation() != null) {
                mapDibbits.add((Dibbit) dibbit);
                Pair<String, String> mapDibbit = new Pair<>(dibbit.getTitle(), dibbit.getLocation());
                locations.add(mapDibbit);
            }
        }
        return locations;
    }

}

