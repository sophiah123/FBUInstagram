package com.example.sophiah123.fbuinstagram;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sophiah123.fbuinstagram.model.Post;

import java.util.List;

public class PostAdapter extends PagedListAdapter<Post, PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    /*
    // pass in Tweets array in constructor
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }
    */

    //public PostAdapter(List<Post> posts) {
    //}


    public PostAdapter(List<Post> mPosts) {
        super(new DiffUtil.ItemCallback<Post>() {
            @Override
            public boolean areItemsTheSame(@NonNull Post post, @NonNull Post t1) {
                return post.getObjectId().equals(t1.getObjectId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Post post, @NonNull Post t1) {
                return false;
            }
        });
        this.mPosts = mPosts;
    }

    // for each row, inflate the layout and cache references into ViewHolder
    // only called when creating new rows
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }


/*
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {

        // getItem() should be used with ListAdapter
        Post post = getItem(position);

        // null placeholders if the PagedList is configured to use them
        // only works for data sets that have total count provided (i.e. PositionalDataSource)
        if (post == null)
        {
            return;
        }

        // Handle remaining work here
        // ...
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPicture);

    }
*/


    // bind value of Post object based on position of element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data according to position
        Post post = mPosts.get(position);

        // populate views according to the data
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());

//        // Load favorite icon
//        if (tweet.isFavorited()) {
//            helper.favoriteImage(holder.ivFavorite);
//        } else {
//            helper.unfavoriteImage(holder.ivFavorite);
//        }
//
//        // Load retweet icon
//        if (tweet.isRetweeted()) {
//            helper.retweetOn(holder.ivRetweet);
//        } else {
//            helper.retweetOff(holder.ivRetweet);
//        }

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPicture);
    }



    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPicture;
        TextView tvUsername;
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookups
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            ivPicture = (ImageView) itemView.findViewById(R.id.ivPicture);

            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the tweet at the position, this won't work if the class is static
                Post post = mPosts.get(position);
//                if (view.getId() == ivCompose.getId()) {
//                    listenerRef.get().onComposeClicked(position, tweet.getUser().getScreenName());
//                } else if (view.getId() == ivFavorite.getId()) {
//                    helper.favoriteItem(tweet, client, ivFavorite);
//                } else if (view.getId() == ivRetweet.getId()) {
//                    helper.retweetItem(tweet, client, ivRetweet);
//                } else {
                // create intent for the new activity
                //Intent intent = new Intent(context, DetailsActivity.class);
                // pass extras

//                intent.putExtra("username", post.getUser().getUsername());
//                intent.putExtra("description", post.getDescription());
//                intent.putExtra("image", post.getImage().getUrl());
                //intent.putExtra("timestamp", post.getRelativeTimestamp());
                // show the activity
//                context.startActivity(intent);
//                }

                Bundle args = new Bundle();
                args.putParcelable("post", post);
                PostDetailFragment fragment = new PostDetailFragment();
                fragment.setArguments(args);
                HomeActivity activity = (HomeActivity) context;
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.linearLayout, fragment);
                transaction.commit();
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}