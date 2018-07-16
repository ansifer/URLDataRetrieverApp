package com.example.urldataretriever;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.urldataretriever.imagelibrary.ImageLoader;
import com.example.urldataretriever.postlibrary.PostLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String POST_URL = "http://pastebin.com/raw/wgkJgazE";

    private ArrayList<Post> postArrayList;
    private ArrayList<JSONPost> jsonPostArrayList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostAdapter adapter;
    private LoaderManager loaderManager;
    private ProgressBar progressBar;
    private boolean isConnected;
    private ConnectivityManager cm;
    private LoaderManager.LoaderCallbacks<BitmapWithIndex> bitmapLoaderCallbacks;
    LoaderManager.LoaderCallbacks<ArrayList<JSONPost>> postLoaderCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = getSupportLoaderManager();
        cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        swipeRefreshLayout  = findViewById(R.id.refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);
        postArrayList = new ArrayList<>();
        jsonPostArrayList = new ArrayList<>();
        adapter = new PostAdapter(this, postArrayList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.progress_bar);

        //TO Retrieve JSON Data First
        postLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<JSONPost>>() {
            @NonNull
            @Override
            public Loader<ArrayList<JSONPost>> onCreateLoader(int i, @Nullable Bundle bundle) {
                return new PostLoader(MainActivity.this, POST_URL);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<JSONPost>> loader, ArrayList<JSONPost> posts) {
                if(posts != null) {
                    postArrayList.clear();
                    jsonPostArrayList.clear();
                     jsonPostArrayList = posts;
                     for (int j = 0; j < jsonPostArrayList.size() ; j++) {
                         //Different thread for each image initialize to retrieve bitmaps from URL retrieved from JSON Asynchronously
                         loaderManager.initLoader(j+1, null, bitmapLoaderCallbacks);
                     }
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<JSONPost>> loader) {

            }
        };

        //TO Retrieve Bitmaps from URL retrieved from JSON Asynchronously
        bitmapLoaderCallbacks = new LoaderManager.LoaderCallbacks<BitmapWithIndex>() {
            @NonNull
            @Override
            public Loader<BitmapWithIndex> onCreateLoader(int i, @Nullable Bundle bundle) {
                    return new ImageLoader(MainActivity.this, jsonPostArrayList.get(i-1).getImgUrl(), i-1);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<BitmapWithIndex> loader, BitmapWithIndex bitmapWithIndex) {
                if(bitmapWithIndex != null) {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Post post = new Post(bitmapWithIndex.getBitmap(), jsonPostArrayList.get(bitmapWithIndex.getI()).getUserName());
                    postArrayList.add(post);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<BitmapWithIndex> loader) {

            }
        };

        if(isConnected) {
            //INITIALIZE TO Retrieve JSON Data First
            loaderManager.initLoader(0, null, postLoaderCallbacks);
        }
        else {
            Snackbar.make(swipeRefreshLayout, "No Internet Connection.",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    //INITIALIZE TO Retrieve JSON Data First
                    loaderManager.restartLoader(0, null, postLoaderCallbacks);
                }
                else {
                    Snackbar.make(swipeRefreshLayout, "No Internet Connection.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

}
