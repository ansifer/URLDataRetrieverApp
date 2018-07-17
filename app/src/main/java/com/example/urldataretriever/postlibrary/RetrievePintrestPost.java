package com.example.urldataretriever.postlibrary;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.urldataretriever.JSONPost;

import java.util.ArrayList;

public class RetrievePintrestPost {

    private RetrievePintrestPostListener listener;

    private LoaderManager.LoaderCallbacks<ArrayList<JSONPost>> postLoaderCallbacks;

    private String mPostsUrl;
    private Activity mContext;
    private LoaderManager loaderManager;

    public RetrievePintrestPost(Activity context, String postsURL) {
        this.listener = null;
        mContext = context;
        mPostsUrl = postsURL;
        loaderManager = context.getLoaderManager();
        getPosts();
    }

    private void getPosts() {
        postLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<JSONPost>>() {
            @NonNull
            @Override
            public Loader<ArrayList<JSONPost>> onCreateLoader(int i, @Nullable Bundle bundle) {
                //return new PostLoader(mContext, mPostsUrl);
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<JSONPost>> loader, ArrayList<JSONPost> posts) {
                if(posts != null) {
                    listener.onDataLoaded(posts);
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<JSONPost>> loader) {

            }
        };
        loaderManager.initLoader(0, null, postLoaderCallbacks);
    }

    public void setRetrievePintrestPostListener(RetrievePintrestPostListener retrievePintrestPostListener) {
        this.listener = retrievePintrestPostListener;
    }

    public interface RetrievePintrestPostListener {
        public void onDataLoaded(ArrayList<JSONPost> arrayList);
    }

}
