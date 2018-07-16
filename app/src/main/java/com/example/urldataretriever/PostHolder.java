package com.example.urldataretriever;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PostHolder extends RecyclerView.ViewHolder {

    private ImageView postImageView;
    private TextView userNameTextView;

    public PostHolder(Context context, @NonNull View itemView) {
        super(itemView);
        postImageView = itemView.findViewById(R.id.post_image);
        userNameTextView = itemView.findViewById(R.id.user_name);
    }

    public void bindPost(Post post) {
        postImageView.setImageBitmap(post.getBitmap());
        userNameTextView.setText(post.getUserName());
    }


}
