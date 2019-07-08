package com.example.instagram;

import android.app.Application;

import com.example.instagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //register subclass
        ParseObject.registerSubclass(Post.class);


        //initilaize parse
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("Instagram")
                .clientKey("werdna24")
                .server("http://andrewkreisher-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
