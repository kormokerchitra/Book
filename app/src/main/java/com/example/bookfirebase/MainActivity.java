package com.example.bookfirebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    int c = 0;
    String songCat = "1";
    private String JSON_STRING;
    ListView category_list;

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
    TextView sample_text;
    TextView sign_up;
    ArrayList<CategoryModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title1 = (TextView) findViewById(R.id.title1);
        final TextView title2 = (TextView) findViewById(R.id.title2);

        final ImageView category_icon = (ImageView) findViewById(R.id.category_icon);
        final ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        final ImageView profile_icon = (ImageView) findViewById(R.id.profile_icon);
        final ImageView notation_icon = (ImageView) findViewById(R.id.notation_icon);
        final ImageView about_icon = (ImageView) findViewById(R.id.about_icon);

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
                new ReadJSON().execute("http://192.168.100.6/bookTest/category_list.php");
                new ReadJSONGeetobitanList().execute("http://192.168.100.6/bookTest/song_list.php");
            }
        });

        final LinearLayout menu_id = (LinearLayout) findViewById(R.id.menu_id);
        menu_id.setVisibility(View.GONE);
        final LinearLayout category_menu = (LinearLayout) findViewById(R.id.category_menu);
        final LinearLayout user_menu = (LinearLayout) findViewById(R.id.user_menu);
        final LinearLayout profile_menu = (LinearLayout) findViewById(R.id.profile_menu);
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


        /// Notation section

        geetobitan_list = (ListView) findViewById(R.id.list_geetobitan);
        swarabitan_list = (ListView) findViewById(R.id.list_swarabitan);
        geetobitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swarabitan.setBackgroundResource(R.drawable.button_back_right_inactive);
                swarabitan.setTextColor(Color.parseColor("#757677"));
                geetobitan.setBackgroundResource(R.drawable.button_back_left_active);
                geetobitan.setTextColor(Color.parseColor("#159EFF"));
                songCat = "1";
                geetobitan_list.setVisibility(View.VISIBLE);
                swarabitan_list.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONGeetobitanList().execute("http://192.168.100.6/bookTest/song_list.php");
                    }
                });
            }
        });

        swarabitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geetobitan.setBackgroundResource(R.drawable.button_back_left_inactive);
                geetobitan.setTextColor(Color.parseColor("#757677"));
                swarabitan.setBackgroundResource(R.drawable.button_back_right_active);
                swarabitan.setTextColor(Color.parseColor("#159EFF"));
                songCat = "2";
                swarabitan_list.setVisibility(View.VISIBLE);
                geetobitan_list.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadJSONSwarabitanList().execute("http://192.168.100.6/bookTest/song_list.php");
                    }
                });
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
                        swarabitanList = songList.toArray(swarabitanList);
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
}