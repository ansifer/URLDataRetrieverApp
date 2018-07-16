package com.example.urldataretriever;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.urldataretriever.imagelibrary.ImageLoader;
import com.example.urldataretriever.postlibrary.PostLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Post> postArrayList;
    private ArrayList<JSONPost> jsonPostArrayList;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private LoaderManager loaderManager;
    private LoaderManager.LoaderCallbacks<BitmapWithIndex> bitmapLoaderCallbacks;
    private int postIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        postArrayList = new ArrayList<>();
        jsonPostArrayList = new ArrayList<>();
        adapter = new PostAdapter(this, postArrayList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        loaderManager = getSupportLoaderManager();
        LoaderManager.LoaderCallbacks<ArrayList<JSONPost>> postLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<JSONPost>>() {
            @NonNull
            @Override
            public Loader<ArrayList<JSONPost>> onCreateLoader(int i, @Nullable Bundle bundle) {
                return new PostLoader(MainActivity.this, "http://pastebin.com/raw/wgkJgazE");
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<JSONPost>> loader, ArrayList<JSONPost> posts) {
                if(posts != null) {
                    postArrayList.clear();
                    jsonPostArrayList.clear();
                     jsonPostArrayList = posts;
                     for (int j = 0; j < jsonPostArrayList.size() ; j++) {
                         loaderManager.initLoader(j+1, null, bitmapLoaderCallbacks);
                     }
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<JSONPost>> loader) {

            }
        };

        loaderManager.initLoader(0, null, postLoaderCallbacks);

        bitmapLoaderCallbacks = new LoaderManager.LoaderCallbacks<BitmapWithIndex>() {
            @NonNull
            @Override
            public Loader<BitmapWithIndex> onCreateLoader(int i, @Nullable Bundle bundle) {
                    return new ImageLoader(MainActivity.this, jsonPostArrayList.get(i-1).getImgUrl(), i-1);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<BitmapWithIndex> loader, BitmapWithIndex bitmapWithIndex) {
                if(bitmapWithIndex != null) {
                    Post post = new Post(bitmapWithIndex.getBitmap(), jsonPostArrayList.get(bitmapWithIndex.getI()).getUserName());
                    postArrayList.add(post);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<BitmapWithIndex> loader) {

            }
        };

    }

}
