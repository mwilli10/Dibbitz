package com.example.finalapp.dibbitz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Morgan on 12/2/15.
 */
public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, int desWidth, int desHeight){
        //Read the dimensions of the image on the disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        //figure out how much to scale down by
        int inSampleSize = 1;

        if (srcHeight>desHeight || srcWidth>desWidth){

            if (srcWidth>srcHeight){
                inSampleSize=Math.round(srcHeight/desHeight);
            }
            else{
                inSampleSize = Math.round(srcWidth/desWidth);
            }

        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Read and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
