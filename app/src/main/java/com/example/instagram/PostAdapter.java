package com.example.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagram.model.Post;
import com.parse.ParseUser;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    private OnClickListener monClickListener;
    private ImageView ivLike;
    private ImageView ivUnlike;
    public static Post post;


    RecyclerView rvPosts;
    Context context;


    public PostAdapter(List<Post> posts, OnClickListener onClickListener) {
        mPosts = posts;
        monClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final int k = i;
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View instaView = inflater.inflate(R.layout.item_insta, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(instaView, monClickListener);
        ivLike = instaView.findViewById(R.id.ivLike);
        ivUnlike = instaView.findViewById(R.id.ivUnlike);
        ivUnlike.setVisibility(View.INVISIBLE);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //get data
        post = mPosts.get(i);

        viewHolder.tvDescription.setText(post.getDescription());
        viewHolder.tvUsername.setText(post.getUser().getUsername());
        viewHolder.tvUsername2.setText(post.getUser().getUsername());


        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(viewHolder.ivImage);


        if (post.getUser().getParseFile("profilepic") == null) {
            Glide.with(context)
                    .load("https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/256x256/plain/user.png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.ivProfile);
        } else {
            Glide.with(context)
                    .load(post.getUser().getParseFile("profilepic").getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.ivProfile);
        }


    }


    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    //create viewholder class
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfile;
        public TextView tvUsername;
        public TextView tvUsername2;
        public TextView tvDescription;
        public TextView tvDate;
        public ImageView ivImage;
        public ImageView ivLike;
        OnClickListener onClickListener;
        public ImageView ivUnlike;


        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            //perform findviewbyid lookups
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvUsername2 = (TextView) itemView.findViewById(R.id.tvUsername2);
            //tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivUnlike = itemView.findViewById(R.id.ivUnlike);
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivLike.setVisibility(View.INVISIBLE);
                    ivUnlike.setVisibility(View.VISIBLE);
                    post.liked(ParseUser.getCurrentUser());
                    Log.d("likes", "liked");

                }
            });


            ivUnlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivUnlike.setVisibility(View.INVISIBLE);
                    ivLike.setVisibility(View.VISIBLE);
                }
            });


        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());
        }

    }

    public interface OnClickListener {
        void onClick(int position);
    }

}
