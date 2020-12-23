package com.dip.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.inputmethod.InputMethodManager;
import android.webkit.HttpAuthHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String ,Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {
        try{
            URL url =new URL(urls[0]);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch(MalformedURLException e){
          e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}