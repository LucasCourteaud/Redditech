package com.example.redditapp.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.redditapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {
    private ArrayList<PostData> postsData;

    public PostRecyclerViewAdapter(ArrayList<PostData> postsData) { this.postsData = postsData; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, null);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(PostRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtViewTitle.setText(postsData.get(position).getTitle());
        holder.txtViewSubname.setText(postsData.get(position).getSubname());
        Picasso.get().load(postsData.get(position).getImageUrl()).into(holder.imgViewIcon);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewTitle;
        public TextView txtViewSubname;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = itemLayoutView.findViewById(R.id.post_title);
            txtViewSubname = itemLayoutView.findViewById(R.id.post_subname);
            imgViewIcon = itemLayoutView.findViewById(R.id.post_image);
        }
    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }
}