package com.example.jsontesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    public class ImageDownloader extends  AsyncTask<String,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls){
            try{
                URL url = new URL(urls[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(in);

                return myBitmap;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                final ArrayList<String> movieDetails = new ArrayList<String>();

                JSONObject jsonObject = new JSONObject(s);
                String movie = jsonObject.getString("Search");
                JSONArray arr = new JSONArray(movie);
                for (int i = 0; i < 1; i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    downloadImage(jsonPart.getString("Poster"));
                    movieDetails.add(jsonPart.getString("Title"));
                    movieDetails.add(jsonPart.getString("Year"));
                }
                Log.i("search", movieDetails.get(0));
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, movieDetails){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(Color.WHITE);

                        // Generate ListView Item using TextView
                        return view;
                    }
                };

                listView.setAdapter(arrayAdapter);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    ImageView imageView;

    public void downloadImage(String url){
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute(url).get();

            imageView.setImageBitmap(myImage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    DownloadTask task;
    ListView listView;

    public void getMovie(View view){
        TextView textView = (TextView) findViewById(R.id.movieName);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.VISIBLE);
        task = new DownloadTask();
        task.execute("http://www.omdbapi.com/?s=" + textView.getText().toString()    + "&apikey=86774b92");
//        Log.i("movie", textView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.moviePoster);
        listView = findViewById(R.id.movieDetails);
    }
}
