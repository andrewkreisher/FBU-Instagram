package com.example.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagram.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    RecyclerView rvPosts;
    Context context;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_insta, viewGroup, false );
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //get data
        Post post = mPosts.get(i);
        viewHolder.tvDescription.setText(post.getDescription());
        viewHolder.tvUsername.setText(post.getUser().getUsername());
        viewHolder.tvUsername2.setText(post.getUser().getUsername());

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(viewHolder.ivImage);

        Glide.with(context)
                .load("https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/256x256/plain/user.png")
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.ivProfile);




    }

    public PostAdapter(List<Post> posts){
        mPosts = posts;
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfile;
        public TextView tvUsername;
        public TextView tvUsername2;
        public TextView tvDescription;
        public TextView tvDate;
        public ImageView ivImage;

        public ViewHolder(View itemView){
            super(itemView);
            //perform findviewbyid lookups
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvUsername2 = (TextView) itemView.findViewById(R.id.tvUsername2);
            //tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }

    }

}
