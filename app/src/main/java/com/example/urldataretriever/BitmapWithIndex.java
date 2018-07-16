package com.example.urldataretriever;

import android.graphics.Bitmap;

public class BitmapWithIndex {

    Bitmap bitmap;
    int i;

    public BitmapWithIndex(Bitmap bitmap, int i) {
        this.i = i;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getI() {
        return i;
    }
}
