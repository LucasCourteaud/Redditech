package com.example.redditapp;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.redditapp.activity.ProfileActivity;
import com.example.redditapp.recyclerviewadapter.PostData;
import com.example.redditapp.recyclerviewadapter.SubData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RedditAPI extends AppCompatActivity {

    private static final String TAG = "RedditAPI";
    private final AVolley myVolley = new AVolley();
    private static final String CLIENT_ID_B64 = "QlhqMkJSa2I5S29vR3h3Rm1qY0g2QTp2OWktOFpWcDl5WWNoU0VzdDM2RmdHZ3dnTGk3dEE=";
    private RequestQueue queue;
    private StringRequest request;

    public void goToActivity(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }

    public void requestProfileInfo(String accessToken) {
        TextView username = findViewById(R.id.username);
        ImageView icon = findViewById(R.id.icon);
        TextView description = findViewById(R.id.description);
        TextView karma = findViewById(R.id.karma);
        queue = Volley.newRequestQueue(this);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        request = myVolley.GetRequestString("https://oauth.reddit.com/api/v1/me.json?raw_json=1", headers, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;
                String get_name;
                String get_image;
                String get_description;
                String get_total_karma;
                try {
                    json = new JSONObject(response);
                    get_name = json.getJSONObject("subreddit").getString("display_name_prefixed");
                    get_image = json.getString("icon_img");
                    get_description = json.getJSONObject("subreddit").getString("public_description");
                    get_total_karma = json.getString("total_karma");
                    username.setText(get_name);
                    Picasso.get().load(get_image).into(icon);
                    description.setText(get_description);
                    karma.setText(get_total_karma);
                } catch (JSONException err) {
                    Log.d(TAG, "error : " + err);
                }
            }
        });
        queue.add(request);
    }

    public void requestAccessToken(String code) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        headers.put("Authorization", "Basic " + CLIENT_ID_B64);
        headers.put("User-Agent", "Kebapp");
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("redirect_uri", "https://www.dorianbse.com");
        queue = Volley.newRequestQueue(this);

        request = myVolley.PostRequestString("https://www.reddit.com/api/v1/access_token", headers, params, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    SingletonToken token = SingletonToken.getInstance(json.getString("access_token"));
                    Log.d("debug", "accessToken : " + token.accessToken);
                } catch (JSONException ignored) {}
                goToActivity(ProfileActivity.class);
            }
        });
        queue.add(request);
    }

    public void patchSettings(String accessToken, JSONObject body) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = myVolley.PatchRequestJSON("https://oauth.reddit.com/api/v1/me/prefs", headers, params, body, new CallbackRequest() {
            @Override
            public void onFail(VolleyError error) {
                super.onFail(error);
            }
        });
        queue.add(jsonObjectRequest);

    }

    public void requestMySub(String accessToken, CallbackRequest callback) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        queue = Volley.newRequestQueue(this);
        request = myVolley.GetRequestString("https://oauth.reddit.com/subreddits/mine/subscriber.json?raw_json=1", headers, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    JSONArray array = json.getJSONObject("data").getJSONArray("children");
                    ArrayList<SubData> array_name = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        String checkIcon = "null";
                        SubData newData = new SubData();
                        try {
                            checkIcon = array.getJSONObject(i).getJSONObject("data").getString("icon_size");
                        } catch (JSONException ignored) {
                        }
                        if (!checkIcon.equals("null"))
                            newData.setImageUrl(array.getJSONObject(i).getJSONObject("data").getString("icon_img"));
                        newData.setSubname(array.getJSONObject(i).getJSONObject("data").getString("display_name_prefixed"));
                        newData.setSubsribers(array.getJSONObject(i).getJSONObject("data").getString("subscribers") + " members");

                        array_name.add(newData);
                        callback.onSuccessSub(array_name);
                    }
                } catch (JSONException err) {
                    Log.d(TAG, "error : " + err);
                }
            }
        });
        queue.add(request);
    }

    public void requestSearchSub(String accessToken, String research, CallbackRequest callback) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        params.put("query", research);
        queue = Volley.newRequestQueue(this);
        request = myVolley.PostRequestString("https://oauth.reddit.com/api/search_subreddits", headers, params, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    JSONArray array = json.getJSONArray("subreddits");
                    ArrayList<SubData> array_name = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        SubData newData = new SubData();
                        String icon;
                        newData.setSubname(array.getJSONObject(i).getString("name"));
                        newData.setSubsribers(array.getJSONObject(i).getString("subscriber_count") + " members");
                        icon = array.getJSONObject(i).getString("icon_img");
                        if (!icon.equals(""))
                            newData.setImageUrl(icon);
                        array_name.add(newData);
                        callback.onSuccessSub(array_name);
                    }
                } catch (JSONException err) {
                    Log.d(TAG, "error : " + err);
                }
            }
        });
        queue.add(request);
    }

    public void requestPostFilted(String accessToken, String filter, CallbackRequest callback) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        queue = Volley.newRequestQueue(this);
        request = myVolley.GetRequestString("https://oauth.reddit.com/" + filter + "?raw_json=1", headers, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    JSONArray array = json.getJSONObject("data").getJSONArray("children");
                    ArrayList<PostData> listPostData = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        PostData postData = new PostData();
                        String type = "";
                        try {
                            type = array.getJSONObject(i).getJSONObject("data").getString("post_hint");
                        } catch (JSONException ignored) {
                        }
                        postData.setSubname(array.getJSONObject(i).getJSONObject("data").getString("subreddit_name_prefixed"));
                        if (type.equals("image"))
                            postData.setImageUrl(array.getJSONObject(i).getJSONObject("data").getString("url_overridden_by_dest"));
                        postData.setTitle(array.getJSONObject(i).getJSONObject("data").getString("title"));
                        listPostData.add(postData);
                        callback.onSuccessPost(listPostData);
                    }
                } catch (JSONException err) {
                    callback.onFail("Post Filted");
                    Log.d(TAG, "error : " + err);
                }
            }
        });
        queue.add(request);
    }

    public void requestSettings(String accessToken) {
        TextView lang = findViewById(R.id.langGet);
        TextView nsfw = findViewById(R.id.nsfwGet);
        TextView night = findViewById(R.id.nightGet);
        TextView country = findViewById(R.id.countryGet);
        TextView noprofan = findViewById(R.id.noprofanGet);
        TextView twittershow = findViewById(R.id.twittershowGet);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("User-Agent", "Kebapp");
        queue = Volley.newRequestQueue(this);
        request = myVolley.GetRequestString("https://oauth.reddit.com/api/v1/me/prefs", headers, new CallbackRequest() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String get_lang = json.getString("lang");
                    String get_nsfw = json.getString("search_include_over_18");
                    String get_night = json.getString("nightmode");
                    String get_country = json.getString("country_code");
                    String get_noprofan = json.getString("no_profanity");
                    String get_twittershow = json.getString("show_twitter");
                    lang.setText(get_lang);
                    nsfw.setText(get_nsfw);
                    night.setText(get_night);
                    country.setText(get_country);
                    noprofan.setText(get_noprofan);
                    twittershow.setText(get_twittershow);
                } catch (JSONException err) {
                    Log.d(TAG, "error : " + err);
                }
            }
        });
        queue.add(request);
    }
}
