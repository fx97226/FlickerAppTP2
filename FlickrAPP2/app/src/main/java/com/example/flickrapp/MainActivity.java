package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.get_image);
        button.setOnClickListener(new GetImageOnClickListener(MainActivity.this));

        Button button2 = findViewById(R.id.next);
        button2.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),ListActivity.class);
            startActivity(i);
        });
    }
}