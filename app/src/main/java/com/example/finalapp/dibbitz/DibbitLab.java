package com.example.finalapp.dibbitz;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 10/8/2015.
 */
public class DibbitLab {
    private static DibbitLab sDibbitLab;
    private Context mContext;

    private List<Dibbit> mDibbits;

    private DibbitLab(Context context) {
        mContext = context;
        mDibbits = new ArrayList<>();
//        for(int i = 0; i < 100; i++) {
//            Dibbit dibbit = new Dibbit();
//            dibbit.setTitle("Dibbit #" + i);
//            dibbit.setSolved(i%2 == 0);  //every other one
//            mDibbits.add(dibbit);
//        }
    }

    public static DibbitLab get(Context context) {
        if(sDibbitLab == null) {
            sDibbitLab = new DibbitLab(context);
        }
        return sDibbitLab;
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

