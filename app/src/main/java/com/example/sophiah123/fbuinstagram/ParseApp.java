package com.example.sophiah123.fbuinstagram;

import android.app.Application;

import com.example.sophiah123.fbuinstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("johnny-hopkins")
                .clientKey("sloan-kettering")
                .server("http://sophiah123-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
