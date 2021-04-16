package com.example.bookfirebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Geetobitan extends AppCompatActivity {
    ArrayList<SongModel> arrayList;
    ListView geetobitan_list;
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

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONGeetobitanList().execute(IpConfig.ip + "bookTest/song_list.php");
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

                    if (productObject.getString("song_category").equals(songCat)) {
                        arrayList.add(new SongModel(
                                productObject.getString("id"),
                                productObject.getString("song_name"),
                                productObject.getString("song_pdf"),
                                productObject.getString("song_category")
                        ));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            GeetobitanAdapter adapter = new GeetobitanAdapter(
                    getApplicationContext(), R.layout.song_item, arrayList
            );
            geetobitan_list.setAdapter(adapter);
            geetobitan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    final String link = arrayList.get(i).getSong_pdf();
                    final String name = arrayList.get(i).getSong_name();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Geetobitan.this);

                    builder.setMessage("Choose what you want to do...");
                    builder.setTitle("Select Option");

                    //This will not allow to close dialogbox until user selects an option
                    builder.setCancelable(true);
                    builder.setPositiveButton("View PDF", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(browserIntent);
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Download", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new DownloadFile().execute(link, name + ".pdf");
                            Toast.makeText(Geetobitan.this, "Download successful!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Geetobitan.this, "Saved to - storage/world of book/", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    //alert.setTitle("AlertDialogExample");
                    alert.show();
                }
            });
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "world of book");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloaderGeetobitan.downloadFile(fileUrl, pdfFile);
            return null;
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

class FileDownloaderGeetobitan {
    private static final int MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
