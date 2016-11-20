package com.simoncherry.faketantan.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Simon on 2016/11/13.
 */

public class BitmapUtil {

    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
//        if (!origin.isRecycled()) {
//            origin.recycle();
//        }
        return newBM;
    }

    public static Bitmap cropBitmap(Bitmap srcBmp) {
        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
//        if (!srcBmp.isRecycled()) {
//            srcBmp.recycle();
//            srcBmp = null;
//        }
        return dstBmp;
    }
}
