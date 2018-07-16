package com.example.urldataretriever;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public class Post {

    private Bitmap bitmap;
    private String userName;

    public Post(Bitmap bitmap,String userName) {
        this.bitmap = bitmap;
        this.userName = userName;

    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
