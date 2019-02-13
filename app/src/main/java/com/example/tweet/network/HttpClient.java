package com.example.tweet.network;


import com.example.tweet.pojo.Tweet;
import com.example.tweet.pojo.User;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

public class HttpClient {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String GET = "GET";
    private static final String EXTENDED_MODE = "&tweet_mode=extended";

    private JsonParser jsonParser;

    public HttpClient(){

        jsonParser = new JsonParser();
    }

    public Collection<User> readUsers (String query) throws IOException, JSONException {
        String requestUrl = "https://api.twitter.com/1.1/users/search.json?q=" + query;
        String encodeUrl = requestUrl.replaceAll(" ", "%20");
        String response = getResponse(encodeUrl);

        Collection<User> users = jsonParser.getUsers(response);

        return users;
    }

    public Collection<Tweet> readTweets(long userId) throws IOException, JSONException {
        String requestUrl = "https://api.twitter.com/1.1/statuses/user_timeline.json?user_id=" + userId + EXTENDED_MODE;

        String response = getResponse(requestUrl);
        Collection<Tweet> tweets = jsonParser.getTweets(response);

        return tweets;
    }


    public User readUserInfo(long userId) throws IOException, JSONException {

        String requestUrl = "https://api.twitter.com/1.1/users/show.json?user_id=" + userId;

        String response = getResponse(requestUrl);

        User user = jsonParser.getUser(response);

        return user;
    }

    private String getResponse(String requestUrl) throws IOException {

        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String authHeader = getAuthHeder(requestUrl);
        connection.setRequestProperty(HEADER_AUTHORIZATION, authHeader);
        connection.connect();

        InputStream in;
        int status = connection.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK)
            in = connection.getErrorStream();
        else
            in = connection.getInputStream();

        String response = convertStreamToString(in);
        return response;
    }



    private String convertStreamToString(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null){
            sb.append(line).append("\n");
        }
        in.close();

        return sb.toString();
    }

    private String getAuthHeder(String url){
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        return new OAuth1aHeaders().getAuthorizationHeader(authConfig, session.getAuthToken(), null, GET, url, null);

    }
}
