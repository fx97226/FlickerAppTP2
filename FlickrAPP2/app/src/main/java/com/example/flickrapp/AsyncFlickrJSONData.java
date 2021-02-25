package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject>{

    @SuppressLint("StaticFieldLeak")
    View view;

    private AppCompatActivity app;

    public AsyncFlickrJSONData(AppCompatActivity a){
        this.app=a;
    }

    @SuppressLint("WrongThread")
    @Override
    //handle the http connection the same way as we saw in the first part of the lab
    protected JSONObject doInBackground(String... urls) {
        JSONObject s = null;
        try {
            URL url = new URL(urls[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);
                String urldl= s.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
                AsyncTask<String, Void, Bitmap> task_dl= new AsyncBitmapDownloader(this.app);
                task_dl.execute(urldl,null,null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    //method that we use to acess to the data from an input stream
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
    //in this method we extract from  the Flickr API the Url of the image
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        JSONArray items = null;
        try {
            items = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i< Objects.requireNonNull(items).length(); i++)
        {
            JSONObject flickr_entry = null;
            try {
                flickr_entry = items.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String urlmedia = null;
            try {
                assert flickr_entry != null;
                urlmedia = flickr_entry.getJSONObject("media").getString("m");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("JFL", "URL media: " + urlmedia);
        }
        //Log.i("JFL", "URL media: " + urlmedia);
    }
}
