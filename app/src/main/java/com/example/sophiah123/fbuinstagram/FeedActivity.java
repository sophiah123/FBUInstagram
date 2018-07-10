package com.example.sophiah123.fbuinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sophiah123.fbuinstagram.model.Post;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }

    // Specify which class to query
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

    // Specify the object id
    public void getPosts(View view) {
        query.getInBackground("aFuEsvjoHt", new GetCallback<Post>() {
            public void done(Post post, ParseException e) {
                if (e == null) {
                    // Access data using the `get` methods for the object
                    String description = post.getDescription();
                    // Access special values that are built-in to each object
                    ParseUser user = post.getUser();
                    ParseFile image = post.getImage();

                    // Do whatever you want with the data...
                    Toast.makeText(FeedActivity.this, description, Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                }
            }
        });
    }
}
