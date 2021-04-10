package com.example.bookfirebase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geetobitan extends AppCompatActivity {
    String geetobitanList[] = {};
    ListView geetobitan_list;
    List<String> songList;
    ArrayAdapter adapter;
    String songCat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geetobitan);

        songCat = getIntent().getStringExtra("songCat");

        geetobitan_list = (ListView) findViewById(R.id.list_geetobitan);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONGeetobitanList().execute("http://192.168.100.6/bookTest/song_list.php");
            }
        });
    }

    class ReadJSONGeetobitanList extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    if (songCat.equals(productObject.getString("song_category"))) {
                        songList = new ArrayList<String>(Arrays.asList(geetobitanList));
                        songList.add(productObject.getString("book"));
                        geetobitanList = songList.toArray(geetobitanList);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ArrayAdapter<String>(Geetobitan.this, R.layout.category_item, R.id.title, geetobitanList);
            geetobitan_list.setAdapter(adapter);
            geetobitan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(Geetobitan.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
