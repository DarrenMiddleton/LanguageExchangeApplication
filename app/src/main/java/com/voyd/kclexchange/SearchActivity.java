package com.voyd.kclexchange;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout linLayResultsHolder;
    private LinearLayout linLaySecondSearchResultsPerPerson;
    private LinearLayout linLayThirdSearchResultsNameImageHolder;
    private TextView txtSearchResultName, txtSearchResultPersonalInfo;
    private ImageView imgProfilePic;
    private String Search;
    private String json1;
    private UserInformation userInformation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Search");

        //expands the intent
        Intent intent = getIntent();
        Search = intent.getExtras().getString("stringSearch");
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");

        barTitle.setText("Search \"" + Search + "\"");

        //method to add data to the activity
        search();

        linLayResultsHolder = (LinearLayout) findViewById(R.id.linLayResultsHolder);


    }

    /**
     * Method that initiates the retrieval of data from the server.
     */
    private void search() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Search, Search);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Search, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showResult(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchActivity.this, "Searching", "Wait a sec", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method to generate the objects for the search result
     * @param json String that contains the json array
     */
    private void showResult(String json) {
        try {
            json1 = json;
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                if(!userInformation.getUniqueCode().equals(jo.getString("UniqueCode")))
                    addingLayout(jo.getString("UniqueCode"),jo.getString("FirstName"), jo.getString("Image"), jo.getString("PersonalInterests"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a programmatic created layout for each object
     * @param Unique - Object's Unique code
     * @param Name - Object's Name
     * @param Image - Object's Image
     * @param Personal - Object's Personal
     */
    private void addingLayout(final String Unique, String Name, final String Image, String Personal) {

        //Creates a TextView for the name of the person
        txtSearchResultName = new TextView(this);
        txtSearchResultName.setText(Name);
        LinearLayout.LayoutParams lpTxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        txtSearchResultName.setTextAlignment(txtSearchResultName.TEXT_ALIGNMENT_CENTER);
        txtSearchResultName.setTextColor(Color.BLACK);

        //creats LinearLayout to hold single result
        linLaySecondSearchResultsPerPerson = new LinearLayout(this);

        //Onclick listener to go to their profile
        linLaySecondSearchResultsPerPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray profileSearch = new JSONArray(json1);
                    for (int i = 0; i < profileSearch.length(); i++) {
                        JSONObject jo = profileSearch.getJSONObject(i);
                        if (jo.getString("UniqueCode").equals(Unique)) {
                            Intent intent = new Intent(getApplicationContext(), ProfileOtherActivity.class);
                            intent.putExtra("profileUnique", jo.getString("UniqueCode"));
                            intent.putExtra("userinfo", userInformation);
                            intent.putExtra("profileFname", jo.getString("FirstName"));
                            intent.putExtra("profileLname", jo.getString("LastName"));
                            intent.putExtra("profileAge", jo.getString("Age"));
                            intent.putExtra("profileGender", jo.getString("Gender"));
                            intent.putExtra("profilePracticingLanguage", jo.getString("PracticeLanguage"));
                            intent.putExtra("profileTeachingLanguage", jo.getString("TeachingLanguage"));
                            intent.putExtra("profilePersonalInterest", jo.getString("PersonalInterests"));
                            intent.putExtra("profileImage", jo.getString("Image"));
                            intent.putExtra("profileGps", jo.getString("GPS"));
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        txtSearchResultName.setLayoutParams(lpTxtName);

        //Creates a Text for the Personal information of that user
        txtSearchResultPersonalInfo = new TextView(this);
        txtSearchResultPersonalInfo.setText(Personal);
        LinearLayout.LayoutParams lpPersonalInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        txtSearchResultPersonalInfo.setPadding(10, 0, 0, 0);
        txtSearchResultPersonalInfo.setTextColor(Color.BLACK);
        txtSearchResultPersonalInfo.setLayoutParams(lpPersonalInfo);

        //Creates a ImageView for the that users image
        imgProfilePic = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(250, 250);
        lpImageHolder.weight = 0.7f;
        imgProfilePic.setLayoutParams(lpImageHolder);
        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgProfilePic.setImageBitmap(decodedByte);


        //Formats LinearLayout that holds, a third layout and personal information
        linLaySecondSearchResultsPerPerson.setOrientation(LinearLayout.HORIZONTAL);
        linLaySecondSearchResultsPerPerson.setWeightSum(1f);
        linLaySecondSearchResultsPerPerson.setBackground(getDrawable(R.drawable.bg_rounded));
        LinearLayout.LayoutParams linParamSecond = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linParamSecond.setMargins(0, 0, 0, 20);
        linLaySecondSearchResultsPerPerson.setLayoutParams(linParamSecond);
        linLaySecondSearchResultsPerPerson.setPadding(10,10,10,10);

        //Creates a LinearLayout to hold the name and the image
        linLayThirdSearchResultsNameImageHolder = new LinearLayout(this);
        linLayThirdSearchResultsNameImageHolder.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linParamThird = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linParamThird.weight = 0.3f;
        linLayThirdSearchResultsNameImageHolder.setLayoutParams(linParamThird);

        //Instantiate the layout
        linLayResultsHolder.addView(linLaySecondSearchResultsPerPerson);
        linLaySecondSearchResultsPerPerson.addView(linLayThirdSearchResultsNameImageHolder);
        linLaySecondSearchResultsPerPerson.addView(txtSearchResultPersonalInfo);
        linLayThirdSearchResultsNameImageHolder.addView(imgProfilePic);
        linLayThirdSearchResultsNameImageHolder.addView(txtSearchResultName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Tap any of the speakers to see their profiles, and remember to see" +
                    "if you can swipe down to find even more opportunities!").setTitle("Help");

            builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}});

            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textDialog = (TextView)dialog.findViewById(android.R.id.message);
            textDialog.setGravity(Gravity.CENTER);

            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

}

