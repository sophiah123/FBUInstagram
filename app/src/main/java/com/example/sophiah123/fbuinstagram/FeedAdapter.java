package com.example.sophiah123.fbuinstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sophiah123.fbuinstagram.model.Post;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    private List<Post> mPosts;
    Context context;
    //pass in the Tweets array in the constructor

    public FeedAdapter(List<Post> tweets) {
        mPosts = tweets;
    }
    //for each row, inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

//        View tweetView =inflater.inflate(R.layout.item_post, parent, false);
//        ViewHolder viewHolder = new ViewHolder(tweetView);
//        return viewHolder;
        return null;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to position
        Post post = mPosts.get(position);
        //populate the views according to this data
        //holder.tvUsername.setText(post.user.name);
//        holder.tvDescription.setText(post.getDescription());
        //holder.tvRelativeTimestamp.setText(post.createdAt);
        //holder.tvScreenName.setText(post.user.screenName);


//        Glide.with(context).load(post.user.profileImageUrl).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
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

    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvRelativeTimestamp;
        public TextView tvScreenName;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups

//            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
//            tvUsername  = (TextView) itemView.findViewById(R.id.tvUserName);
//            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            //tvRelativeTimestamp = (TextView) itemView.findViewById(R.id.tvRelativeTimestamp);
            //tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);


        }
    }

}

