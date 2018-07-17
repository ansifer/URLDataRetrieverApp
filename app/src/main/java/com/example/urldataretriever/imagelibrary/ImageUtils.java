package com.example.urldataretriever.imagelibrary;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.urldataretriever.BitmapWithIndex;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {

    private static final String LOG_TAG = ImageUtils.class.getSimpleName();

    public static BitmapWithIndex fetchData(String requestUrl, int i) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        BitmapWithIndex bitmapResponse = null;
        try {
            bitmapResponse = makeHttpRequest(url, i);
            if (bitmapResponse == null) {
                Log.e(LOG_TAG, "No Image Found");
            }
            else {
                return bitmapResponse;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return null;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static BitmapWithIndex makeHttpRequest(URL url, int index) throws IOException {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG, "Retrieving");
                inputStream = urlConnection.getInputStream();
                return readFromStream(inputStream, index);
            } else {
                Log.e(LOG_TAG, "No image Found, Error!");
            }
        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        return null;
    }


    //Decoding Image from Input Stream
    private static BitmapWithIndex readFromStream(InputStream inputStream, int index) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        return new BitmapWithIndex(BitmapFactory.decodeStream(inputStream,null,options), index);
    }



}
