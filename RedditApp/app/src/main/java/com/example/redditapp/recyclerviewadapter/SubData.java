package com.example.redditapp.recyclerviewadapter;

public class SubData {

    private String subname;
    private String imageUrl;
    private String subsribers;

    public SubData() {
        this.subname = null;
        this.imageUrl = null;
        this.subsribers = null;
    }

    public void setSubname(String subname) { this.subname = subname; }

    public void setSubsribers(String subsribers) { this.subsribers = subsribers; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSubname() { return subname; }

    public String getSubsribers() { return subsribers; }

    public String getImageUrl() { return imageUrl; }

}