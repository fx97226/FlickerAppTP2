package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Iterator;
import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    private Vector<String> vector = new Vector<String>();
    private Context context_list;

    public MyAdapter(Context cont){
        context_list=cont;
    }

    public void add(String url){
        this.vector.add(url);
    }

    /* public void print(){
         Iterator myIterator= vector.iterator();

         while (myIterator.hasNext()){
             Log.i("JFL","Addig to adapter url : "+myIterator.next());
         }

         Log.i("JFL","TODO");

     }*/
    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       /* Iterator myIterator= vector.iterator();
        while (myIterator.hasNext()){
            Log.i("JFL","Addig to adapter url : "+myIterator.next());
        }*/
        // Check if an existing view is being reused, otherwise inflate the view


       /* if (convertView == null) {
            convertView = LayoutInflater.from(context_list).inflate(R.layout.activity_list, parent, false);
        }
        // Lookup view for data population
        TextView txtxv = (TextView) convertView.findViewById(R.id.textlistview);//creates an item template similar to the selected one
        // Populate the data into the template view using the data object
        txtxv.setText(getItem(position).toString());*/
        // Return the completed view to render on screen

        if (convertView == null) {
            convertView = LayoutInflater.from(context_list).inflate(R.layout.bitmaplayout, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.imagetp);


        Response.Listener<Bitmap> rep_listener = img::setImageBitmap;

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(img.getContext()).getRequestQueue();

        ImageRequest rq= new ImageRequest(getItem(position).toString(),rep_listener,400,400, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JFL","Image load Error:");

            }
        });

        queue.add(rq);
        return convertView;
    }
}