package com.example.bookfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int c = 0;
    String songCat = "1", id = "";
    private String JSON_STRING;
    ListView category_list;
    boolean loggedIn;

    String swarabitanList[] = {};
    String geetobitanList[] = {};
    ListView geetobitan_list, swarabitan_list;
    ArrayAdapter adapter, adapter1;
    List<String> songList, songList1;
    TextView login;
    TextView register, geetobitan, swarabitan;
    EditText username;
    EditText fullname;
    EditText email;
    EditText password;
    EditText con_password;
    EditText fullname_view, username_view, email_view;
    TextView sample_text;
    TextView sign_up, edit_basic;
    ArrayList<CategoryModel> arrayList;
    LinearLayout user_menu, profile_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final TextView title1 = (TextView) findViewById(R.id.title1);
        final TextView title2 = (TextView) findViewById(R.id.title2);

        final ImageView category_icon = (ImageView) findViewById(R.id.category_icon);
        final ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        final ImageView profile_icon = (ImageView) findViewById(R.id.profile_icon);
        final ImageView notation_icon = (ImageView) findViewById(R.id.notation_icon);
        final ImageView about_icon = (ImageView) findViewById(R.id.about_icon);

        fullname_view = (EditText) findViewById(R.id.fullname_view);
        username_view = (EditText) findViewById(R.id.username_view);
        email_view = (EditText) findViewById(R.id.email_view);

        final RelativeLayout category_page = (RelativeLayout) findViewById(R.id.category_page);
        category_page.setVisibility(View.VISIBLE);
        final LinearLayout verification_page = (LinearLayout) findViewById(R.id.verification_page);
        verification_page.setVisibility(View.GONE);
        final RelativeLayout profile_page = (RelativeLayout) findViewById(R.id.profile_page);
        profile_page.setVisibility(View.GONE);
        final LinearLayout notation_page = (LinearLayout) findViewById(R.id.notation_page);
        notation_page.setVisibility(View.GONE);
        final RelativeLayout about_page = (RelativeLayout) findViewById(R.id.about_page);
        about_page.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(IpConfig.ip + "bookTest/category_list.php");
                new ReadJSONGeetobitanList().execute(IpConfig.ip + "bookTest/song_list.php");
                new ReadJSONSwarabitanList().execute(IpConfig.ip + "bookTest/song_list.php");
            }
        });

        final LinearLayout menu_id = (LinearLayout) findViewById(R.id.menu_id);
        menu_id.setVisibility(View.GONE);
        final LinearLayout category_menu = (LinearLayout) findViewById(R.id.category_menu);
        user_menu = (LinearLayout) findViewById(R.id.user_menu);
        profile_menu = (LinearLayout) findViewById(R.id.profile_menu);
        final LinearLayout notation_menu = (LinearLayout) findViewById(R.id.notation_menu);
        final LinearLayout about_menu = (LinearLayout) findViewById(R.id.about_menu);
        category_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setText("World");
                title2.setText("of books");
                category_icon.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24);
                user_icon.setImageResource(R.drawable.ic_outline_verified_user_24_2);
                profile_icon.setImageResource(R.drawable.ic_outline_person_24_2);
                notation_icon.setImageResource(R.drawable.ic_baseline_queue_music_24_2);
                about_icon.setImageResource(R.drawable.ic_outline_info_24_2);
                category_menu.setBackgroundColor(Color.parseColor("#16159EFF"));
                user_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                notation_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                about_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                category_page.setVisibility(View.VISIBLE);
                verification_page.setVisibility(View.GONE);
                profile_page.setVisibility(View.GONE);
                notation_page.setVisibility(View.GONE);
                about_page.setVisibility(View.GONE);
            }
        });

        user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setText("User");
                title2.setText("verification");
                category_icon.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_2);
                user_icon.setImageResource(R.drawable.ic_outline_verified_user_24);
                profile_icon.setImageResource(R.drawable.ic_outline_person_24_2);
                notation_icon.setImageResource(R.drawable.ic_baseline_queue_music_24_2);
                about_icon.setImageResource(R.drawable.ic_outline_info_24_2);
                category_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                user_menu.setBackgroundColor(Color.parseColor("#16159EFF"));
                profile_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                notation_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                about_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                category_page.setVisibility(View.GONE);
                verification_page.setVisibility(View.VISIBLE);
                profile_page.setVisibility(View.GONE);
                notation_page.setVisibility(View.GONE);
                about_page.setVisibility(View.GONE);
            }
        });

        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setText("Profile");
                title2.setText("");
                category_icon.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_2);
                user_icon.setImageResource(R.drawable.ic_outline_verified_user_24_2);
                profile_icon.setImageResource(R.drawable.ic_outline_person_24);
                notation_icon.setImageResource(R.drawable.ic_baseline_queue_music_24_2);
                about_icon.setImageResource(R.drawable.ic_outline_info_24_2);
                category_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                user_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_menu.setBackgroundColor(Color.parseColor("#16159EFF"));
                notation_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                about_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                category_page.setVisibility(View.GONE);
                verification_page.setVisibility(View.GONE);
                profile_page.setVisibility(View.VISIBLE);
                notation_page.setVisibility(View.GONE);
                about_page.setVisibility(View.GONE);

                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);

                final String email_user = sharedPreferences.getString("loggedin_data", "");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, IpConfig.ip + "bookTest/profile.php",
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

                                            id = productObject.getString("id");
                                            String fn = productObject.getString("fullname");
                                            String un = productObject.getString("username");
                                            String email = productObject.getString("email");

                                            fullname_view.setText(fn);
                                            username_view.setText(un);
                                            email_view.setText(email);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Failed to get user!", Toast.LENGTH_SHORT).show();
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
                        params.put("email", email_user);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        notation_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setText("Geetobitan");
                title2.setText("& Swarabitan");
                category_icon.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_2);
                user_icon.setImageResource(R.drawable.ic_outline_verified_user_24_2);
                profile_icon.setImageResource(R.drawable.ic_outline_person_24_2);
                notation_icon.setImageResource(R.drawable.ic_baseline_queue_music_24);
                about_icon.setImageResource(R.drawable.ic_outline_info_24_2);
                category_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                user_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                notation_menu.setBackgroundColor(Color.parseColor("#16159EFF"));
                about_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                category_page.setVisibility(View.GONE);
                verification_page.setVisibility(View.GONE);
                profile_page.setVisibility(View.GONE);
                notation_page.setVisibility(View.VISIBLE);
                about_page.setVisibility(View.GONE);
            }
        });

        about_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setText("About");
                title2.setText("us");
                category_icon.setImageResource(R.drawable.ic_baseline_format_list_bulleted_24_2);
                user_icon.setImageResource(R.drawable.ic_outline_verified_user_24_2);
                profile_icon.setImageResource(R.drawable.ic_outline_person_24_2);
                notation_icon.setImageResource(R.drawable.ic_baseline_queue_music_24_2);
                about_icon.setImageResource(R.drawable.ic_outline_info_24);
                category_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                user_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                notation_menu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                about_menu.setBackgroundColor(Color.parseColor("#16159EFF"));
                category_page.setVisibility(View.GONE);
                verification_page.setVisibility(View.GONE);
                profile_page.setVisibility(View.GONE);
                notation_page.setVisibility(View.GONE);
                about_page.setVisibility(View.VISIBLE);
            }
        });

        final ImageView menu_display = (ImageView) findViewById(R.id.menu_display);
        menu_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c++;
                if (c % 2 == 0) {
                    menu_display.setImageResource(R.drawable.ic_baseline_menu_24);
                    menu_id.setVisibility(View.GONE);
                } else {
                    menu_display.setImageResource(R.drawable.ic_baseline_close_24);
                    menu_id.setVisibility(View.VISIBLE);
                }
            }
        });

        /// Category section

        category_list = (ListView) findViewById(R.id.list_category);

        /// Profile section


        edit_basic = (TextView) findViewById(R.id.edit_basic);
        edit_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = username_view.getText().toString();
                String fn = fullname_view.getText().toString();
                String em = email_view.getText().toString();
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("username", un));
                nameValuePairs.add(new BasicNameValuePair("fullname", fn));
                nameValuePairs.add(new BasicNameValuePair("email", em));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(IpConfig.ip + "bookTest/profile_edit.php");
                    //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    String msg = "Edited Successfully";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }
            }
        });

        final EditText new_pass = (EditText) findViewById(R.id.password_pass);
        final EditText con_pass = (EditText) findViewById(R.id.con_password_pass);

        TextView change_pass = (TextView) findViewById(R.id.edit_pass);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = new_pass.getText().toString();
                String cpass = con_pass.getText().toString();
                if (!pass.equals(cpass)) {
                    Toast.makeText(MainActivity.this, "Password don't match!", Toast.LENGTH_SHORT).show();
                } else {
                    InputStream is = null;
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("password", pass));

                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(IpConfig.ip + "bookTest/pass_change.php");
                        //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        String msg = "Password Edited Successfully";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        new_pass.setText("");
                        con_pass.setText("");

                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }
                }

            }
        });

        TextView sign_out = (TextView) findViewById(R.id.sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean("isLoggedin", false);

                //Putting blank value to email
                editor.putString("loggedin_data", "");

                //Saving the sharedpreferences
                editor.commit();

                finish();
                Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                startActivity(intent);
            }
        });

        /// User verification

        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        geetobitan = (TextView) findViewById(R.id.opt1);
        swarabitan = (TextView) findViewById(R.id.opt2);
        fullname = (EditText) findViewById(R.id.fullname);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        con_password = (EditText) findViewById(R.id.con_password);
        sample_text = (TextView) findViewById(R.id.sample_text);
        sign_up = (TextView) findViewById(R.id.sign_up);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginMethod();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMethod();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getVisibility() == View.GONE) {
                    registerMethod();
                } else {
                    loginMethod();
                }
            }
        });



        TextView submit = (TextView) findViewById(R.id.submit_user);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sign up

                if (username.getVisibility() == View.VISIBLE) {
                    String un = username.getText().toString();
                    String fn = fullname.getText().toString();
                    String em = email.getText().toString();
                    String pass = password.getText().toString();
                    String con_pass = con_password.getText().toString();

                    if (un.equals("")) {
                        Toast.makeText(MainActivity.this, "Username field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (fn.equals("")) {
                        Toast.makeText(MainActivity.this, "Full name field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (em.equals("")) {
                        Toast.makeText(MainActivity.this, "Email field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (pass.equals("")) {
                        Toast.makeText(MainActivity.this, "Password field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (con_pass.equals("")) {
                        Toast.makeText(MainActivity.this, "Confirm password field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(con_pass)) {
                        Toast.makeText(MainActivity.this, "Password not match!", Toast.LENGTH_SHORT).show();
                    } else {
                        InputStream is = null;
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("username", un));
                        nameValuePairs.add(new BasicNameValuePair("fullname", fn));
                        nameValuePairs.add(new BasicNameValuePair("email", em));
                        nameValuePairs.add(new BasicNameValuePair("password", pass));

                        try {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(IpConfig.ip + "bookTest/sign_up.php");
                            //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpClient.execute(httpPost);
                            HttpEntity entity = response.getEntity();
                            is = entity.getContent();
                            String msg = "Registered Successfully";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } catch (ClientProtocolException e) {
                            Log.e("ClientProtocol", "Log_tag");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.e("Log_tag", "IOException");
                            e.printStackTrace();
                        }
                    }
                } else {

                    //Sign in

                    final String em = email.getText().toString();
                    final String pass = password.getText().toString();
                    if (em.equals("")) {
                        Toast.makeText(MainActivity.this, "Email field is blank!", Toast.LENGTH_SHORT).show();
                    } else if (pass.equals("")) {
                        Toast.makeText(MainActivity.this, "Password field is blank!", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, IpConfig.ip + "bookTest/login.php",
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
                                            finish();
                                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
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
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(stringRequest);
                    }
                }

            }
        });


        /// Notation section

        geetobitan_list = (ListView) findViewById(R.id.list_geetobitan);
        swarabitan_list = (ListView) findViewById(R.id.list_swarabitan);
        geetobitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                songCat = "1";
                Intent intent = new Intent(getBaseContext(), Geetobitan.class);
                intent.putExtra("songCat", songCat);
                startActivity(intent);
            }
        });

        swarabitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songCat = "2";
                Intent intent = new Intent(getBaseContext(), Swarabitan.class);
                intent.putExtra("songCat", songCat);
                startActivity(intent);
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
//                progres.setVisibility(View.GONE);

                //alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new CategoryModel(
                            productObject.getString("id"),
                            productObject.getString("category")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CategoryAdapter adapter = new CategoryAdapter(
                    getApplicationContext(), R.layout.category_item, arrayList
            );
            category_list.setAdapter(adapter);
            category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id = arrayList.get(i).getId();
                    String category = arrayList.get(i).getCategory();

                    Intent intent = new Intent(getBaseContext(), CategoryBooksPage.class);
                    intent.putExtra("id", id);
                    intent.putExtra("category", category);
                    startActivity(intent);
                }
            });
        }
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
            adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.category_item, R.id.title, geetobitanList);
            geetobitan_list.setAdapter(adapter);
            geetobitan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ReadJSONSwarabitanList extends AsyncTask<String, Integer, String> {

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
                        songList1 = new ArrayList<String>(Arrays.asList(swarabitanList));
                        songList1.add(productObject.getString("book"));
                        swarabitanList = songList1.toArray(swarabitanList);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter1 = new ArrayAdapter<String>(MainActivity.this, R.layout.category_item, R.id.title, swarabitanList);
            swarabitan_list.setAdapter(adapter1);
            swarabitan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, adapter1.getItem(i).toString(), Toast.LENGTH_SHORT).show();
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

    void loginMethod() {
        register.setBackgroundResource(R.drawable.button_back_right_inactive);
        register.setTextColor(Color.parseColor("#757677"));
        login.setBackgroundResource(R.drawable.button_back_left_active);
        login.setTextColor(Color.parseColor("#159EFF"));
        fullname.setVisibility(View.GONE);
        username.setVisibility(View.GONE);
        con_password.setVisibility(View.GONE);
        fullname.setText("");
        fullname.clearFocus();
        username.setText("");
        username.clearFocus();
        email.setText("");
        email.clearFocus();
        password.setText("");
        password.clearFocus();
        con_password.setText("");
        con_password.clearFocus();
        sample_text.setText("Don't have an account?");
        sign_up.setText("Sign up");
    }

    void registerMethod() {
        register.setBackgroundResource(R.drawable.button_back_right_active);
        register.setTextColor(Color.parseColor("#159EFF"));
        login.setBackgroundResource(R.drawable.button_back_left_inactive);
        login.setTextColor(Color.parseColor("#757677"));
        fullname.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        con_password.setVisibility(View.VISIBLE);
        fullname.setText("");
        fullname.clearFocus();
        username.setText("");
        username.clearFocus();
        email.setText("");
        email.clearFocus();
        password.setText("");
        password.clearFocus();
        con_password.setText("");
        con_password.clearFocus();
        sample_text.setText("Already have an account?");
        sign_up.setText("Sign in");
    }

    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean("isLoggedin", false);
        String email = sharedPreferences.getString("loggedin_data", "");

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            user_menu.setVisibility(View.GONE);
            profile_menu.setVisibility(View.VISIBLE);
        } else {
            user_menu.setVisibility(View.VISIBLE);
            profile_menu.setVisibility(View.GONE);
        }
    }
}