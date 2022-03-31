package com.example.redditapp.recyclerviewadapter;

public class PostData {

    private String title;
    private String subname;
    private String imageUrl;

    public PostData() {
        this.title = null;
        this.subname = null;
        this.imageUrl = null;
    }

    public void setTitle(String title) { this.title = title; }

    public void setSubname(String subname) { this.subname = subname; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTitle() { return title; }

    public String getSubname() { return subname; }

    public String getImageUrl() { return imageUrl; }
}