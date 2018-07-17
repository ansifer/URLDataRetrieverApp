package com.example.urldataretriever.postlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import com.example.urldataretriever.JSONPost;

import java.util.ArrayList;

public class PostLoader extends AsyncTaskLoader<ArrayList<JSONPost>> {

    private static final String LOG_TAG = PostLoader.class.getName();

    private String mUrl;


    public PostLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<JSONPost> loadInBackground() {
        if(mUrl == null) {
            return null;
        }
        ArrayList<JSONPost> posts = PostUtils.fetchData(mUrl);
        return posts;
    }
}
