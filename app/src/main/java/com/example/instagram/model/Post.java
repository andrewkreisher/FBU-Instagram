package com.example.instagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("Post")
public class Post extends ParseObject {
    //fields
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE = "image";
    private final static String KEY_USER = "user";
    private static int limit = 20;
    public ArrayList<ParseUser> likedBy = new ArrayList<>();
    public int test = 5;

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }




    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public ArrayList<ParseUser> getLikes(){
        return likedBy;
    }

    public void setLikes(){
        put("likes", 10);
    }

    public void unlike(){
        likedBy.remove(ParseUser.getCurrentUser());
    }

    public void liked(ParseUser user){
        likedBy.add(user);
    }


    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post> {
        public Query(){
            super(Post.class);
        }

        public Query getTop(int add){
            setLimit(20 + add);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }

    }






}
