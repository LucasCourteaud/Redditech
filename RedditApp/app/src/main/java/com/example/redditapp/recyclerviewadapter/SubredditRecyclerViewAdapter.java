package com.example.redditapp.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redditapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubredditRecyclerViewAdapter extends RecyclerView.Adapter<SubredditRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<SubData> data;

    public SubredditRecyclerViewAdapter(ArrayList<SubData> data) { this.data = data; }

    @NonNull
    @Override
    public SubredditRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(SubredditRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getSubname());
        holder.subscribersView.setText(data.get(position).getSubsribers());
        Picasso.get().load(data.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public TextView subscribersView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.search_name);
            this.subscribersView = view.findViewById(R.id.subscriberSub);
            this.imageView = view.findViewById(R.id.search_icon);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}