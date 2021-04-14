package com.example.bookfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookDetails extends AppCompatActivity {
    String name, url, rate, person_num, username, book_id;
    LinearLayout rating_layout, signin_layout;
    boolean loggedIn;
    EditText email, password;
    TextView submit;
    int person;
    double rating_total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        name = getIntent().getStringExtra("name");

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView read = (TextView) findViewById(R.id.read);
        TextView download = (TextView) findViewById(R.id.download);
        final TextView rating = (TextView) findViewById(R.id.rating);
        rating_layout = (LinearLayout) findViewById(R.id.rating_layout);
        signin_layout = (LinearLayout) findViewById(R.id.signin_layout);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/bookTest/book_finder.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String res = response.toString();
                        //Creating a shared preference
                        if (res.contains("list")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(res);
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject productObject = jsonArray.getJSONObject(i);

                                    book_id = productObject.getString("id");
                                    url = productObject.getString("book_link");
                                    rate = productObject.getString("rating");
                                    rating_total = Double.parseDouble(rate);
                                    person_num = productObject.getString("reviewer_number");
                                    person = Integer.parseInt(person_num);

                                    rating.setText(rate + " (" + person_num + ")");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(BookDetails.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("name", name);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
        requestQueue.add(stringRequest);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        submit = (TextView) findViewById(R.id.submit_user);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String em = email.getText().toString();
                final String pass = password.getText().toString();

                if (em.equals("")) {
                    Toast.makeText(BookDetails.this, "Email field is empty!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(BookDetails.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/bookTest/login.php",
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    String res = response.toString();
                                    //Creating a shared preference
                                    if (res.equals("success")) {
                                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        //Adding values to editor
                                        editor.putBoolean("isLoggedin", true);
                                        editor.putString("loggedin_data", em);
                                        //editor.putString(Config.NAME_SHARED_PREF, email);

                                        //Saving values to editor
                                        editor.commit();

                                        signin_layout.setVisibility(View.GONE);
                                        rating_layout.setVisibility(View.VISIBLE);

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/bookTest/profile.php",
                                                new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        String res = response.toString();
                                                        //Creating a shared preference
                                                        if (res.contains("list")) {
                                                            JSONObject jsonObject = null;
                                                            try {
                                                                jsonObject = new JSONObject(res);
                                                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                                    JSONObject productObject = jsonArray.getJSONObject(i);

                                                                    username = productObject.getString("fullname");
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            Toast.makeText(BookDetails.this, "Failed to get user!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        //You can handle error here if you want
                                                    }
                                                }) {

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                //Adding parameters to request
                                                params.put("email", em);

                                                //returning parameter
                                                return params;
                                            }
                                        };

                                        //Adding the string request to the queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
                                        requestQueue.add(stringRequest);
                                    } else {
                                        Toast.makeText(BookDetails.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put("email", em);
                            params.put("password", pass);

                            //returning parameter
                            return params;
                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFile().execute(url, name + ".pdf");
                Toast.makeText(BookDetails.this, "Download successful!", Toast.LENGTH_SHORT).show();
                Toast.makeText(BookDetails.this, "Saved to - storage/world of book/", Toast.LENGTH_SHORT).show();
            }
        });

        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        final EditText comment = (EditText) findViewById(R.id.comment_text);
        TextView add = (TextView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = simpleRatingBar.getRating() + "";
                double ratig1 = Double.parseDouble(rating);
                String com = comment.getText().toString();
                String fn = username;
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                person++;
                String pp = String.valueOf(person);
                rating_total = ratig1 + rating_total;
                double avg = rating_total/person;
                String average = String.valueOf(avg);

                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("rating", rating));
                nameValuePairs.add(new BasicNameValuePair("comment", com));
                nameValuePairs.add(new BasicNameValuePair("review_person", fn));
                nameValuePairs.add(new BasicNameValuePair("review_date", formattedDate));
                nameValuePairs.add(new BasicNameValuePair("book_id", book_id));
                nameValuePairs.add(new BasicNameValuePair("rating_total", average));
                nameValuePairs.add(new BasicNameValuePair("person", pp));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.100.5/bookTest/give_review.php");
                    //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    String msg = "Reviewed Successfully";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    comment.setText("");
                    simpleRatingBar.setRating(0);

                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }
            }
        });
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


    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean("isLoggedin", false);
        final String em = sharedPreferences.getString("loggedin_data", "");

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            signin_layout.setVisibility(View.GONE);
            rating_layout.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/bookTest/profile.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            String res = response.toString();
                            //Creating a shared preference
                            if (res.contains("list")) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(res);
                                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject productObject = jsonArray.getJSONObject(i);

                                        username = productObject.getString("fullname");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(BookDetails.this, "Failed to get user!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put("email", em);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(BookDetails.this);
            requestQueue.add(stringRequest);
        }
    }
}
