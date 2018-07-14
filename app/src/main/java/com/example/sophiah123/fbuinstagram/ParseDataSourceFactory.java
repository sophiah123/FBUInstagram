package com.example.sophiah123.fbuinstagram;

import android.arch.paging.DataSource;

import com.example.sophiah123.fbuinstagram.model.Post;

public class ParseDataSourceFactory extends DataSource.Factory<Integer, Post> {

    @Override
    public DataSource<Integer, Post> create() {
        ParsePositionalDataSource source = new ParsePositionalDataSource();
        return source;
    }
}
