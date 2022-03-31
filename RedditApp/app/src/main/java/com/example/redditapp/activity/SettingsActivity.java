package com.example.redditapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;
import com.example.redditapp.SingletonToken;

public class SettingsActivity extends RedditAPI {

    public void goToActivity(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String accessToken = SingletonToken.getInstance().accessToken;
        requestSettings(accessToken);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_settings);
        String accessToken = SingletonToken.getInstance("").accessToken;
        requestSettings(accessToken);

        Button buttonModify = findViewById(R.id.buttonModify);
        buttonModify.setOnClickListener(v -> goToActivity(ModifyActivity.class));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}