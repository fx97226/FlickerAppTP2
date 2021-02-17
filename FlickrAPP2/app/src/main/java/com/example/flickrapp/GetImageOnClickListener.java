package com.example.flickrapp;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GetImageOnClickListener implements View.OnClickListener {


    private AppCompatActivity app;

    public GetImageOnClickListener(AppCompatActivity MainActivity){
        this.app=MainActivity;
    }

    @Override
    public void onClick(View v) {
        String url = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(this.app);
        task.execute(url, null, null);
    }

}