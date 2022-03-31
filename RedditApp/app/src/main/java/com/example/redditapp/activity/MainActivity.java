package com.example.redditapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;

public class MainActivity extends RedditAPI {

    private static final String TAG = "MainActivity";
    private static final String CLIENT_ID = "BXj2BRkb9KooGxwFmjcH6A";
    private static final String RESPONSE_TYPE = "code";
    private static final String STATE = "randomstr";
    private static final String REDIRECT_URI = "https://www.dorianbse.com";
    private static final String DURATION = "temporary";
    private static final String AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=" + CLIENT_ID +
            "&response_type=" + RESPONSE_TYPE + "&state=" + STATE + "&redirect_uri=" + REDIRECT_URI + "&duration=" + DURATION +
            "&scope=identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread account";

    public void startSignIn(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AUTH_URL));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this::startSignIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String uri = getIntent().getDataString();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            int start = uri.indexOf("code=") + 5;
            int end = uri.indexOf("#");
            String code = uri.substring(start, end);
            requestAccessToken(code);
        } catch (NullPointerException e) {
            Log.w(TAG, "Error : " + e);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}