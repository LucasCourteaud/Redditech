package com.example.redditapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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

public class SearchActivity extends RedditAPI {

    RecyclerView recyclerView;
    ArrayList<SubData> listSub;
    SubredditRecyclerViewAdapter adapter;
    Button searchButton;
    EditText research;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String accessToken = SingletonToken.getInstance().accessToken;
        recyclerView = findViewById(R.id.searchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listSub = new ArrayList<>();
        adapter = new SubredditRecyclerViewAdapter(listSub);
        recyclerView.setAdapter(adapter);
        searchButton = findViewById(R.id.applySearch);
        research = findViewById(R.id.editTextSubreddit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        searchButton.setOnClickListener(v ->
                requestSearchSub(accessToken, research.getText().toString(), new CallbackRequest() {
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
                })
        );
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}