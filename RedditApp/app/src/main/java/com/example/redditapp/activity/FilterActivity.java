package com.example.redditapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redditapp.CallbackRequest;
import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;
import com.example.redditapp.SingletonToken;
import com.example.redditapp.recyclerviewadapter.PostData;
import com.example.redditapp.recyclerviewadapter.PostRecyclerViewAdapter;

import org.json.JSONException;

import java.util.ArrayList;

public class FilterActivity extends RedditAPI {

    RecyclerView recyclerView;
    ArrayList<PostData> listPostData;
    PostRecyclerViewAdapter adapter;

    public void setRecyclerViewFilter(String filter) {
        String accessToken = SingletonToken.getInstance().accessToken;
        requestPostFilted(accessToken, filter, new CallbackRequest() {
            @Override
            public void onSuccessPost(ArrayList<PostData> list) throws JSONException {
                super.onSuccessPost(list);
                listPostData.clear();
                listPostData.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        recyclerView = findViewById(R.id.myPostList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listPostData = new ArrayList<>();
        adapter = new PostRecyclerViewAdapter(listPostData);
        recyclerView.setAdapter(adapter);
        setRecyclerViewFilter("new");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button hotButton = findViewById(R.id.hotButton);
        hotButton.setOnClickListener(v -> setRecyclerViewFilter("hot"));

        Button newButton = findViewById(R.id.newButton);
        newButton.setOnClickListener(v -> setRecyclerViewFilter("new"));

        Button bestButton = findViewById(R.id.bestButton);
        bestButton.setOnClickListener(v -> setRecyclerViewFilter("best"));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}