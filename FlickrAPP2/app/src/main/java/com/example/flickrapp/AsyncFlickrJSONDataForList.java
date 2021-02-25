package com.example.flickrapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class AsyncFlickrJSONDataForList extends AsyncTask<String,Void, JSONObject> {


    private final MyAdapter adapter;

    public  AsyncFlickrJSONDataForList(MyAdapter a){
        this.adapter=a;
    }

    @Override
    //handle the http connection the same way as we saw in the first part of the lab
    protected JSONObject doInBackground(String... strings) {
        JSONObject s = null;
        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private JSONObject readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            StringBuilder s = new StringBuilder();
            s.append(bo.toString());
            String j = s.substring("jsonFlickrFeed(".length(), s.length() - 1);
            return new JSONObject(j);
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    //In this method we add all the url that we extracted from the api in an adapter list
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try{
            JSONArray items =jsonObject.getJSONArray("items");
            for (int i=0; i<items.length();i++){
                Log.i("ok",""+i);
                adapter.add(items.getJSONObject(i).getJSONObject("media").getString("m"));
                adapter.notifyDataSetChanged();//notifies the attached observers that the data has been changed

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}