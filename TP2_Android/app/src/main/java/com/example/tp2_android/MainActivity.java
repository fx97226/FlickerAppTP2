package com.example.tp2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Onclick(View view){

        EditText password= findViewById(R.id.passwd);
        EditText username= findViewById(R.id.usnm);

        String txt1= password.getText().toString();
        String txt2= username.getText().toString();
        String txt3= txt2+":"+txt1;
        final String[] res = new String[1];
         Thread t = new Thread(new Runnable(){

             public void run(){
                 URL url = null;
                 try {
                     url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                     HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                     String basicAuth = "Basic " + Base64.encodeToString(txt3.getBytes(),Base64.NO_WRAP);
                     urlConnection.setRequestProperty ("Authorization", basicAuth);

                     try {
                         InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                         JSONObject s = readStream(in);
                         res[0] = s.getString("user") + ":" +  s.getString("authenticated");
                         Log.i("JFL", s.getString("authenticated")+":"+s.getString("user"));

                     } finally {
                         urlConnection.disconnect();
                     }
                 } catch (IOException | JSONException e) {
                     e.printStackTrace();
                 }

                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         TextView rslt= findViewById(R.id.result);

                         rslt.setText(res[0]);
                     }
                 });

             }

        });
        t.start();
    }

    private JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return new JSONObject(sb.toString());
    }
}