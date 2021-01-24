package com.example.mymovie.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    //for trailers
    private static final String BASE_URL_TRAILERS="https://api.themoviedb.org/3/movie/%s/videos";


    //for reviews
    private static final String BASE_URL_REVIEWS ="https://api.themoviedb.org/3/movie/%s/reviews";



    //for movies
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";

    private static final String API_KEY = "04dabf1dbe500236d0527e58748c7e18";
    private static final String LANGUAGE = "ru-RU";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_VOTE_AVERAGE = "vote_average.desc";

    public static final int POPULARITY = 0;
    public static final int VOTE_AVERAGE = 1;

    private static URL buildURL(int sortBy, int page) {
        String methodOfSort;
        URL url = null;
        if (sortBy == POPULARITY)
            methodOfSort = SORT_BY_POPULARITY;
        else
            methodOfSort = SORT_BY_VOTE_AVERAGE;

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page)).build();
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
        }
        return url;
    }

    private static URL buildUrlForTrailer(int idOfMovie){
        Uri uri = Uri.parse(String.format(BASE_URL_TRAILERS,idOfMovie)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY,API_KEY).build();
        try{
            return new URL(uri.toString());
        }catch (Exception e){}
        return null;
    }

    private static URL buildUrlForReview(int idOfMovie){
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEWS,idOfMovie)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY,API_KEY)
                .build();
        try{
            return new URL(uri.toString());

        }catch (Exception e){}
        return null;
    }

    public static JSONObject getJSONFromNetwork(int sortBy,int page){
        JSONObject result = null;
        URL url = buildURL(sortBy,page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONForTrailer(int idOfMovie){
        JSONObject result = null;
        URL url = buildUrlForTrailer(idOfMovie);
        try{
            result = new JSONLoadTask().execute(url).get();
        }catch (Exception e){}
        return result;
    }

    public static JSONObject getJSONForReview(int idOfMovie){
        JSONObject result = null;
        URL url = buildUrlForReview(idOfMovie);
        try{
            result = new JSONLoadTask().execute(url).get();
        }catch (Exception e){}
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0)
                return result;
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                StringBuilder stringBuilder = new StringBuilder();
                while (line!=null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                Log.i("my_json",stringBuilder.toString());
                result = new JSONObject(stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection!=null)
                    connection.disconnect();
            }
            return result;

        }
    }

}
