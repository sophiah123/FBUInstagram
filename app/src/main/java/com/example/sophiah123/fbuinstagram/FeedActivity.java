package com.example.sophiah123.fbuinstagram;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sophiah123.fbuinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

        RecyclerView rvPosts;
        static ArrayList<Post> posts;
        private PostAdapter postAdapter;
        private SwipeRefreshLayout swipeContainer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_feed);

            rvPosts = (RecyclerView) this.findViewById(R.id.rvPosts);
            posts = new ArrayList<>();

            postAdapter = new PostAdapter(posts);
            // set up RecyclerView (layout manager, use adapter)
            rvPosts.setLayoutManager(new LinearLayoutManager(this));
            // set adapter
            rvPosts.setAdapter(postAdapter);


            loadTopPosts();


            // implement swipe to refresh
            // Lookup the swipe container view
            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    fetchTimelineAsync(0);
                    swipeContainer.setRefreshing(false);
                }
            });
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

        }

        public void fetchTimelineAsync(int page) {
            // Send the network request to fetch the updated data
            // `client` here is an instance of Android Async HTTP
            // getHomeTimeline is an example endpoint.
            // Remember to CLEAR OUT old items before appending in the new ones
            postAdapter.clear();
            loadTopPosts();
            postAdapter.addAll(posts);
            // Now we call setRefreshing(false) to signal refresh has finished
            swipeContainer.setRefreshing(false);
        }


        private void loadTopPosts() {
            final Post.Query postsQuery = new Post.Query();
            postsQuery.getTop().withUser();

            postsQuery.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> objects, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < objects.size(); i++) {
                            Log.d("HomeActivity", "Post[" + i + "] = "
                                    + objects.get(i).getDescription()
                                    + "\nusername = " + objects.get(i).getUser().getUsername());
                            Post post = new Post();
                            post.setUser(objects.get(i).getUser());
                            post.setImage(objects.get(i).getImage());
                            post.setDescription(objects.get(i).getDescription());
                            posts.add(post);
                            postAdapter.notifyItemInserted(posts.size() - 1);
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }