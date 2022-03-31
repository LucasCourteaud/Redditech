package com.example.redditapp;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.redditapp.recyclerviewadapter.PostData;
import com.example.redditapp.recyclerviewadapter.SubData;

import org.json.JSONException;

import java.util.ArrayList;

public class CallbackRequest {

    public void onSuccess(String response) throws JSONException {
        Log.d("request", "Request success");
    }

    public void onSuccess() throws JSONException {
        Log.d("request", "Request success");
    }

    public void onSuccessPost(ArrayList<PostData> listPostData) throws JSONException {
        Log.d("request", "Request success");
    }

    public void onSuccessSub(ArrayList<SubData> listSubData) throws  JSONException {
        Log.d("request", "Request success");
    }

    public void onFail(VolleyError error) {
        Log.w("request", "RequestError: " + error.toString());
    }

    public void onFail(String error) {
        Log.w("request", "RequestError: " + error);
    }

}
