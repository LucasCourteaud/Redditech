package com.example.redditapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;
import com.example.redditapp.SingletonToken;

public class ProfileActivity extends RedditAPI {

    private Button buttonSearch, buttonSettings, buttonPosts, buttonMySubs;

    public void goToActivity(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String accessToken = SingletonToken.getInstance("").accessToken;
        requestProfileInfo(accessToken);

        this.buttonMySubs = findViewById(R.id.mysubsButton);
        buttonMySubs.setOnClickListener(v -> goToActivity(MySubredditActivity.class));
        this.buttonSearch = findViewById(R.id.searchButton);
        buttonSearch.setOnClickListener(v -> goToActivity(SearchActivity.class));
        this.buttonSettings = findViewById(R.id.buttonsetting);
        buttonSettings.setOnClickListener(v -> goToActivity(SettingsActivity.class));
        this.buttonPosts = findViewById(R.id.postButton);
        buttonPosts.setOnClickListener(v -> goToActivity(FilterActivity.class));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_profile);
        String accessToken = SingletonToken.getInstance("").accessToken;
        requestProfileInfo(accessToken);

        this.buttonMySubs = findViewById(R.id.mysubsButton);
        buttonMySubs.setOnClickListener(v -> goToActivity(MySubredditActivity.class));
        this.buttonSearch = findViewById(R.id.searchButton);
        buttonSearch.setOnClickListener(v -> goToActivity(SearchActivity.class));
        this.buttonSettings = findViewById(R.id.buttonsetting);
        buttonSettings.setOnClickListener(v -> goToActivity(SettingsActivity.class));
        this.buttonPosts = findViewById(R.id.postButton);
        buttonPosts.setOnClickListener(v -> goToActivity(FilterActivity.class));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}