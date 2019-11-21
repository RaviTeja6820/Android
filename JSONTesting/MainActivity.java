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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

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
            HttpURLConnection urlConnection;

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
                return " {\"Search\":[{\"Title\":\"" + e.getMessage() +  "\",\"Year\":\"2012\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Infinity War\",\"Year\":\"2018\",\"imdbID\":\"tt4154756\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Age of Ultron\",\"Year\":\"2015\",\"imdbID\":\"tt2395427\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTM4OGJmNWMtOTM4Ni00NTE3LTg3MDItZmQxYjc4N2JhNmUxXkEyXkFqcGdeQXVyNTgzMDMzMTg@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Endgame\",\"Year\":\"2019\",\"imdbID\":\"tt4154796\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg\"},{\"Title\":\"Avengers: Endgame\",\"Year\":\"2019\",\"imdbID\":\"tt4154796\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg\"},{\"Title\":\"The Avengers\",\"Year\":\"1998\",\"imdbID\":\"tt0118661\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYWE1NTdjOWQtYTQ2Ny00Nzc5LWExYzMtNmRlOThmOTE2N2I4XkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg\"},{\"Title\":\"The Avengers: Earth's Mightiest Heroes\",\"Year\":\"2010–2012\",\"imdbID\":\"tt1626038\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYzA4ZjVhYzctZmI0NC00ZmIxLWFmYTgtOGIxMDYxODhmMGQ2XkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg\"},{\"Title\":\"Ultimate Avengers\",\"Year\":\"2006\",\"imdbID\":\"tt0491703\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDFmZTkxMjktMzRiYS00YzMwLWFhZDctOTQ2N2NlOTAyZDJhXkEyXkFqcGdeQXVyNjgzNDU2ODI@._V1_SX300.jpg\"},{\"Title\":\"Ultimate Avengers II\",\"Year\":\"2006\",\"imdbID\":\"tt0803093\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZjI3MTI5ZTYtZmNmNy00OGZmLTlhNWMtNjZiYmYzNDhlOGRkL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Avengers Assemble\",\"Year\":\"2013–\",\"imdbID\":\"tt2455546\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTY0NTUyMDQwOV5BMl5BanBnXkFtZTgwNjAwMTA0MDE@._V1_SX300.jpg\"}],\"totalResults\":\"96\",\"Response\":\"True\"}";
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
                    Toast.makeText(MainActivity.this, "Works", Toast.LENGTH_LONG).show();
                }
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
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.moviePoster);
        listView = findViewById(R.id.movieDetails);
    }
}
