package com.example.redditapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

import com.example.redditapp.R;
import com.example.redditapp.RedditAPI;
import com.example.redditapp.SingletonToken;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyActivity extends RedditAPI {

    private String language, nsfw, nightmode, country, noprofan, showtwitter;
    private Button buttonLangFr, buttonLangEn;
    private Button buttonNsfwOn, buttonNsfwOff;
    private Button buttonNightOn, buttonNightOff;
    private Button buttonCountryRE, buttonCountryFR;
    private Button buttonNoprofanOn, buttonNoprofanOff;
    private Button buttonShowtwitterOn, buttonShowtwitterOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        String accessToken = SingletonToken.getInstance().accessToken;
        Button buttonApply;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.buttonLangEn = findViewById(R.id.langEn);
        buttonLangEn.setOnClickListener(v -> {
            language = "en";
            buttonLangEn.setBackgroundColor(Color.GREEN);
            buttonLangFr.setBackgroundColor(Color.RED);
        });
        this.buttonLangFr = findViewById(R.id.langFr);
        buttonLangFr.setOnClickListener(v -> {
            language = "fr";
            buttonLangEn.setBackgroundColor(Color.RED);
            buttonLangFr.setBackgroundColor(Color.GREEN);
        });

        this.buttonNsfwOn = findViewById(R.id.nsfwOn);
        buttonNsfwOn.setOnClickListener(v -> {
            nsfw = "true";
            buttonNsfwOn.setBackgroundColor(Color.GREEN);
            buttonNsfwOff.setBackgroundColor(Color.RED);
        });
        this.buttonNsfwOff = findViewById(R.id.nsfwOff);
        buttonNsfwOff.setOnClickListener(v -> {
            nsfw = "false";
            buttonNsfwOn.setBackgroundColor(Color.RED);
            buttonNsfwOff.setBackgroundColor(Color.GREEN);
        });

        this.buttonNightOn = findViewById(R.id.nightOn);
        buttonNightOn.setOnClickListener(v -> {
            nightmode = "true";
            buttonNightOn.setBackgroundColor(Color.GREEN);
            buttonNightOff.setBackgroundColor(Color.RED);
        });
        this.buttonNightOff = findViewById(R.id.nightOff);
        buttonNightOff.setOnClickListener(v -> {
            nightmode = "false";
            buttonNightOn.setBackgroundColor(Color.RED);
            buttonNightOff.setBackgroundColor(Color.GREEN);
        });

        this.buttonCountryRE = findViewById(R.id.countryRe);
        buttonCountryRE.setOnClickListener(v -> {
            country = "RE";
            buttonCountryFR.setBackgroundColor(Color.RED);
            buttonCountryRE.setBackgroundColor(Color.GREEN);
        });
        this.buttonCountryFR = findViewById(R.id.countryFr);
        buttonCountryFR.setOnClickListener(v -> {
            country = "FR";
            buttonCountryFR.setBackgroundColor(Color.GREEN);
            buttonCountryRE.setBackgroundColor(Color.RED);
        });

        this.buttonNoprofanOn= findViewById(R.id.noprofanOn);
        buttonNoprofanOn.setOnClickListener(v -> {
            noprofan = "true";
            buttonNoprofanOn.setBackgroundColor(Color.GREEN);
            buttonNoprofanOff.setBackgroundColor(Color.RED);
        });
        this.buttonNoprofanOff = findViewById(R.id.noprofanOff);
        buttonNoprofanOff.setOnClickListener(v -> {
            noprofan = "false";
            buttonNoprofanOn.setBackgroundColor(Color.RED);
            buttonNoprofanOff.setBackgroundColor(Color.GREEN);
        });

        this.buttonShowtwitterOn = findViewById(R.id.showtwitterOn);
        buttonShowtwitterOn.setOnClickListener(v -> {
            showtwitter = "true";
            buttonShowtwitterOn.setBackgroundColor(Color.GREEN);
            buttonShowtwitterOff.setBackgroundColor(Color.RED);
        });
        this.buttonShowtwitterOff = findViewById(R.id.showtwitterOff);
        buttonShowtwitterOff.setOnClickListener(v -> {
            showtwitter = "false";
            buttonShowtwitterOn.setBackgroundColor(Color.RED);
            buttonShowtwitterOff.setBackgroundColor(Color.GREEN);
        });

        buttonApply = findViewById(R.id.buttonApply);
        buttonApply.setOnClickListener(v -> {
            JSONObject new_settings = new JSONObject();
            try {
                new_settings.put("lang", language);
                new_settings.put("search_include_over_18", nsfw);
                new_settings.put("nightmode", nightmode);
                new_settings.put("country_code", country);
                new_settings.put("no_profanity", noprofan);
                new_settings.put("show_twitter", showtwitter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            patchSettings(accessToken, new_settings);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.ModifyActivity), R.string.applyChange, Snackbar.LENGTH_LONG);
            snackbar.show();
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