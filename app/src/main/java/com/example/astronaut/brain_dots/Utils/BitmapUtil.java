package com.example.astronaut.brain_dots.Utils;

/*
 *Created by 魏兴源 on 2018-09-04
 *Time at 22:56
 *
 */


import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 该类用于处理Bitmap,可以缩放,更改大小等
 */
public class BitmapUtil {

    public Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
