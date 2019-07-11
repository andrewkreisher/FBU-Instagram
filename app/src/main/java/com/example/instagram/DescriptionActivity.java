package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DescriptionActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvCaption;
    ImageView ivImage;
    TextView tvLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        tvDate = findViewById(R.id.tvDate);
        tvCaption = findViewById(R.id.tvCaption);
        ivImage = findViewById(R.id.ivImage);
        tvLikes = findViewById(R.id.tvLikes);

        tvLikes.setText("Likes: " + getIntent().getIntExtra("likes", 0));

        tvCaption.setText("Caption: " + getIntent().getStringExtra("caption"));

        String date = getIntent().getStringExtra("date");
        tvDate.setText("Posted on " + date);

        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(ivImage);

    }
}
