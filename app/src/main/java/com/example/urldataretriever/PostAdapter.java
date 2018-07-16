package com.example.urldataretriever;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    private Context context;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_image, viewGroup, false);
        return new PostHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int i) {
        Post post = posts.get(i);
        postHolder.bindPost(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
