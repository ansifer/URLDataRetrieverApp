package com.example.urldataretriever.imagelibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.urldataretriever.BitmapWithIndex;

public class ImageLoader extends AsyncTaskLoader<BitmapWithIndex> {

    private static final String LOG_TAG = ImageLoader.class.getName();

    private String mUrl;
    private int i;


    public ImageLoader(Context context, String url, int i) {
        super(context);
        mUrl = url;
        this.i = i;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public BitmapWithIndex loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        return ImageUtils.fetchData(mUrl, i);
    }
}
