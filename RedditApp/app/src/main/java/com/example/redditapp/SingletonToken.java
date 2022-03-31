package com.example.redditapp;

public final class SingletonToken {
    private static SingletonToken instance;
    public String accessToken;

    public SingletonToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static SingletonToken getInstance(String accessToken) {
        if (instance == null) {
            instance = new SingletonToken(accessToken);
        }
        return instance;
    }

    public static SingletonToken getInstance() {
        return instance;
    }
}