package com.example.tweet.network;

import com.example.tweet.pojo.Tweet;
import com.example.tweet.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class JsonParser {

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Tweet.class, new TweetDeserializer()).create();

    public Collection<User> getUsers(String response){

        Type usersType = new TypeToken<Collection<User>>(){}.getType();

        return GSON.fromJson(response, usersType);
    }

    public Collection<Tweet> getTweets (String response){

        Type tweetsType = new TypeToken<Collection<Tweet>>(){}.getType();

        return GSON.fromJson(response, tweetsType);
    }



    public User getUser(String response){

        return GSON.fromJson(response, User.class);

    }
}
