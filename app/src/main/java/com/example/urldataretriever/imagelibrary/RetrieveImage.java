package com.example.urldataretriever.imagelibrary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;

import com.example.urldataretriever.BitmapWithIndex;

public class RetrieveImage {

    private Activity mContext;
    private String imageURL;
    public LoaderManager loaderManager;
    private int index;
    private RetrieveImageListener listener;


    public RetrieveImage(Activity context, String imageURL, int index) {
        this.listener = null;
        mContext = context;
        this.imageURL = imageURL;
        this.index = index;
        getImage();
    }

    private void getImage() {
        loaderManager = mContext.getLoaderManager();
        LoaderManager.LoaderCallbacks<BitmapWithIndex> bitmapWithIndexLoaderCallbacks = new LoaderManager.LoaderCallbacks<BitmapWithIndex>() {
            @Override
            public Loader<BitmapWithIndex> onCreateLoader(int i, Bundle bundle) {
               // return new ImageLoader(mContext, imageURL, index-1);
                return null;
            }

            @Override
            public void onLoadFinished(Loader<BitmapWithIndex> loader, BitmapWithIndex bitmapWithIndex) {
                if(bitmapWithIndex != null) {
                    listener.onDataLoaded(bitmapWithIndex.getBitmap(), bitmapWithIndex.getI());
                }
            }

            @Override
            public void onLoaderReset(Loader<BitmapWithIndex> loader) {

            }
        };

        loaderManager.initLoader(index, null, bitmapWithIndexLoaderCallbacks);
    }

    public void setRetrieveImageListener(RetrieveImageListener retrieveImageListener) {
        this.listener = retrieveImageListener;
    }



    public interface RetrieveImageListener {
        public void onDataLoaded(Bitmap bitmap, int index);
    }


}
