package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView l = findViewById(R.id.list);

        adapter= new MyAdapter(this);
        l.setAdapter(adapter);

        AsyncTask<String,Void, JSONObject> task = new AsyncFlickrJSONDataForList(adapter);
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json",null,null);
    }
}