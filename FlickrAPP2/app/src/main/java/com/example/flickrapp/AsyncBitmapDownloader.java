package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private final AppCompatActivity imagev;


    public AsyncBitmapDownloader(AppCompatActivity v){
        this.imagev = v;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap b= null;
        try {
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                b = BitmapFactory.decodeStream(in);
                Log.i("ASyncbitmamdownloader", b.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } finally {

        }
        return b;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView i = this.imagev.findViewById(R.id.image);
        i.setImageBitmap(bitmap);
    }
}