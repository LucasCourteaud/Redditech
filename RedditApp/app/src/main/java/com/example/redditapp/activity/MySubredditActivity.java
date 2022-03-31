package com.example.redditapp.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redditapp.CallbackRequest;
import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;
import com.example.redditapp.SingletonToken;
import com.example.redditapp.recyclerviewadapter.SubData;
import com.example.redditapp.recyclerviewadapter.SubredditRecyclerViewAdapter;

import org.json.JSONException;

import java.util.ArrayList;

public class MySubredditActivity extends RedditAPI {

    RecyclerView recyclerView;
    ArrayList<SubData> listSub;
    SubredditRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysubreddit);
        String accessToken = SingletonToken.getInstance().accessToken;
        recyclerView = findViewById(R.id.mySubList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listSub = new ArrayList<>();
        adapter = new SubredditRecyclerViewAdapter(listSub);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        requestMySub(accessToken, new CallbackRequest() {
            @Override
            public void onSuccessSub(ArrayList<SubData> list) throws JSONException {
                super.onSuccessSub(list);
                listSub.clear();
                listSub.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String error) {
                super.onFail(error);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}