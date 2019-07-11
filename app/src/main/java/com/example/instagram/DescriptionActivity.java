package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
