package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements PostAdapter.OnClickListener {

    private Button logoutBtn;
    private Button imageBtn;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public byte[] photo;
    File photoFile;
    RecyclerView rvPosts;
    ArrayList<Post> posts;
    PostAdapter postAdapter;
    SwipeRefreshLayout swipeContainer;
    ImageView ivLogout;
    ImageView ivPost;
    private EndlessRecyclerViewScrollListener scrollListener;
    String maxId = null;
    int add = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();



        ivPost = findViewById(R.id.ivPost);

        ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home2camera = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(home2camera);
            }
        });



        ivLogout = findViewById(R.id.ivLogout);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home2logout = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(home2logout);
            }
        });

        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        //init arraylist / data source
        posts = new ArrayList<>();
        //construct adapter from datasource
        postAdapter = new PostAdapter(posts, this);
        //recyclerview setup (layoutmanager, use adapter)

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextData(5);

            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);
        rvPosts.setAdapter(postAdapter);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipecontainer);
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadTopPosts();
            }
        });




        loadTopPosts();


    }

    public void loadNextData(int additional) {
        add += additional;
        loadTopPosts();
        //populateTimeline(maxID);
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void loadTopPosts(){

        final Post.Query postsQuery = new Post.Query();

        //postsQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        postsQuery.getTop(add).withUser();


        postsQuery.orderByDescending("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e==null){
                    postAdapter.clear();
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("Home", "Post["+i+"]: " + objects.get(i).getDescription() + "\nUsername: " + objects.get(i).getUser().getUsername());
                        posts.add(objects.get(i));
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(int i) {
        Intent post2details = new Intent(HomeActivity.this, DescriptionActivity.class);
        post2details.putExtra("date", posts.get(i).getCreatedAt().toString());
        post2details.putExtra("caption", posts.get(i).getDescription());
        post2details.putExtra("image", posts.get(i).getImage().getUrl());


        startActivity(post2details);
    }




}
