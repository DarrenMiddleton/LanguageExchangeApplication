package com.voyd.kclexchange;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voyd.kclexchange.ui.SampleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private UserInformation userInformation;
    private EditText txtSearch;
    private String stringSearch;
    private LinearLayout linLayFriendsListHolder, linMeetingHolder, linNotificationHolder;
    public ArrayList<String> jsonArray, jsonArray1, jsonArray3;
    private ImageView imgFriends;
    private TextView txtFriendsName, txtMeeting;
    private Button btnYes, btnNo;
    private ArrayList<String> uniqueCodes;
    private String CombinedUnique1, CombinedUnique2;

    @Override
    public void onRestart() {
        super.onRestart();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("userinfo", userInformation);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
        txtSearch = (EditText) findViewById(R.id.txtSearch);

        if(DataStore.getCurrentUser()!=null)
            userInformation = DataStore.getCurrentUser();

        uniqueCodes = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("KCLexchange");

        linNotificationHolder = (LinearLayout) findViewById(R.id.linNotification);

        jsonArray = new ArrayList<>();
        jsonArray1 = new ArrayList<>();
        jsonArray3 = new ArrayList<>();
        linLayFriendsListHolder = (LinearLayout) findViewById(R.id.linLayFriends);
        searchFriends();
        searchMeet();
        searchMSGNotification();
    }
    /**
     * Method to change
     * @param Status Status you want to change the meeting to (1 = yes, 2 = No)
     * @param Status1 there for future proof this method if Status1 (current status) ever changes
     * @param Unique Who the meeting for the user is with
     */
    private void UpdateStatus(final String Status, final String Status1, final String Unique) {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                Log.d("Unique", Unique);
                Log.d("Status", Status);
                HashMap<String, String> params = new HashMap<>();
                params.put("Status", Status);
                params.put("Status1", "0");
                params.put("UniqueCode", Unique);
                params.put("UniqueCodeUser", userInformation.getUniqueCode());
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun.x10host.com/ChangeMeeting.php", params);
                return res;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Searching", "Wait a sec", false, false);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();

            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * First Search that returns the UniqueID of the possible users to have a meeting with
     */
    private void searchMeet() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_ID, userInformation.getUniqueCode());
                params.put("Status", "0");
                RequestHandler rh = new RequestHandler();

                String res = rh.SendPostRequest("http://projectrun.x10host.com/GetMeetingRequest.php", params);
                Log.d("JSONFORMEET", res);
                return res;

            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                searchMeet1(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Searching", "Wait a sec", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Processes the json to get the other users UniqueCode
     * @param json String that contains the json array
     */
    private void searchMeet1(String json) {
        try {
            JSONArray search = new JSONArray(json);

            //If statement to get the opposite of the users code so it gets the other users UniqueCode
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                if (jo.getString("UserAUniqueCode").equals(userInformation.getUniqueCode())){
                    jsonArray3.add(jo.getString("UserBUniqueCode"));
                    searchMeet2(i, jo.getString("UserBUniqueCode"), jo);
                    Log.d("Jamie", jo.toString());
                    Log.d("index123", i + "");
                } else {
                    jsonArray3.add(jo.getString("UserAUniqueCode"));
                    searchMeet2(i, jo.getString("UserAUniqueCode"), jo);
                    Log.d("index123", i + "");
                    Log.d("Jamie1", jo.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses the firneds PHP to get the users data
     * @param index
     * @param jsonObject
     */
    private void searchMeet2(final int index, final String Friends, final JSONObject jsonObject) {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                RequestHandler rh = new RequestHandler();
                params.put("FriendCode", Friends);
                String res = rh.SendPostRequest(Config.URL_FindFriends, params);
                return res;
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                searchMeet3(s, index, jsonObject);

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Loading", "Wait for it...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Sends the Users data to the layout with any additional information that is needed
     * @param json
     * @param indicator
     * @param jsonObject
     */
    private void searchMeet3(String json, int indicator,final  JSONObject jsonObject) {
        try {
            jsonArray3.add(indicator, json);
            JSONArray search = new JSONArray(json);
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                addingNotificationLayout(jo, "wants to meet you on " + jsonObject.getString("DateOFMeet") + " at " + jsonObject.getString("TimeOfMeet") + " on " + jsonObject.getString("Location"), jo.getString("UniqueCode"), jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for any message Notifications
     */
    private void searchMSGNotification() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_ID, userInformation.getUniqueCode());
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest("http://projectrun.x10host.com/NotificationFetchMsg.php", params);
                return res;
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                JSONArray search = null;
                try {
                    search = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
                    for (int i = 0; i < search.length(); i++) {
                        JSONObject jo = search.getJSONObject(i);
                        Log.d("is it barry123", jo.getString("Status"));
                        if(!jo.getString("Unread").equals("NotActive")) {
                            searchMSGNotification2(jo.getString("Status"), i, jo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Searching", "Wait a sec", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }


    /**
     * Gets the Unique Code from the JSON and process the data into something useful
     * @param unique
     * @param index
     * @param jsonObject
     */
    private void searchMSGNotification2(final String unique, final int index, final JSONObject jsonObject) {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("FriendCode", unique);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_FindFriends, params);
                return res;
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                searchMSGNotification3(s, index, jsonObject);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Loading", "Wait for it...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    private void searchMSGNotification3(String json, int indicator, final JSONObject jsonObject) {
        try {
            jsonArray1.add(indicator, json);
            JSONArray search = new JSONArray(json);
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                addingNotificationLayout(jo, "has sent you a message", jo.getString("UniqueCode"), jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void addingNotificationLayout(JSONObject jo,final String chatORMeet, final String UniqueCode, JSONObject jsonObject) throws JSONException {


        linMeetingHolder = new LinearLayout(this);
        linMeetingHolder.setOrientation(LinearLayout.HORIZONTAL);
        linMeetingHolder.setWeightSum(1f);
        LinearLayout.LayoutParams linParamMeetingHolder = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linMeetingHolder.setLayoutParams(linParamMeetingHolder);


        txtMeeting = new TextView(this);
        txtMeeting.setText(jo.getString("FirstName")+ " " + chatORMeet);
        LinearLayout.LayoutParams lpTxtMeeting = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTxtMeeting.weight = 0.6f;
        txtMeeting.setTextColor(Color.BLACK);
        txtMeeting.setLayoutParams(lpTxtMeeting);
        String Unique = jo.getString("UniqueCode");
        Integer x = Integer.parseInt(Unique.substring(1));

        btnYes = new Button(this);
        btnYes.setText("Accept");
        btnYes.setId(x);
        LinearLayout.LayoutParams lpBtnYes = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtnYes.weight = 0.2f;
        btnYes.setLayoutParams(lpBtnYes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonArray1.addAll(jsonArray3);
                    for (int x = 0; x < jsonArray1.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray1.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            String u = jo.getString("UniqueCode");
                            if (jo.getString("UniqueCode").equals(UniqueCode)) {
                                if (chatORMeet.equals("has sent you a message")) {
                                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                    intent.putExtra("profileUnique", UniqueCode);
                                    intent.putExtra("profileFname", jo.getString("FirstName"));
                                    intent.putExtra("profileImage", jo.getString("Image"));
                                    intent.putExtra("userinfo", userInformation);
                                    fillMessageRoom(jo.getString("UniqueCode"));
                                    startActivity(intent);
                                } else {
                                    Log.d("ButtonUnique", jo.getString("UniqueCode"));
                                    final String s = "1";
                                    UpdateStatus(s, "0", jo.getString("UniqueCode"));

                                    userInformation.setStat("meetings", userInformation.getStat("meetings") + 1);
                                    userInformation.setStat("points", userInformation.getStat("points") + 10);
                                    userInformation.updateStudentNoDialog(HomeActivity.this);


                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("userinfo", userInformation);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnNo = new Button(this);
        btnNo.setText("Ignore");
        LinearLayout.LayoutParams lpBtnNo = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtnNo.weight = 0.2f;
        btnNo.setLayoutParams(lpBtnYes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonArray1.addAll(jsonArray3);
                    for (int x = 0; x < jsonArray1.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray1.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            String u = jo.getString("UniqueCode");
                            if (jo.getString("UniqueCode").equals(UniqueCode)) {
                                if (chatORMeet.equals("has sent you a message")) {
                                    Log.d("IAMHERE", jo.getString("FirstName"));
                                    fillMessageRoom(jo.getString("UniqueCode"));

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("userinfo", userInformation);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    UpdateStatus("2", "0", jo.getString("UniqueCode"));

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("userinfo", userInformation);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        linNotificationHolder.addView(linMeetingHolder);
        linMeetingHolder.addView(txtMeeting);
        linMeetingHolder.addView(btnYes);
        linMeetingHolder.addView(btnNo);


    }

    /**
     * Method that retrieves previous messages
     */
    private void fillMessageRoom(final String otherUnique) {

        CombinedUnique1 = otherUnique + userInformation.getUniqueCode();
        CombinedUnique2 = userInformation.getUniqueCode() + otherUnique;
        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("StatusTo", "NotActive");
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Msgsearch, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    //----------------------------------------------------------------


    /**
     * Goes through each of the users friends list and displays them
     */
    private void searchFriends(){
        if(userInformation.getFriends()[0]!="") {
            String[] string = userInformation.getFriends();
            for (int x = 0; x < userInformation.getFriends().length; x++) {
                search(string[x], x);
            }
        } else {
            TextView txtEmpty = new TextView(this);
            txtEmpty.setText("No quick-contacts added");
            txtEmpty.setGravity(Gravity.CENTER_HORIZONTAL);
            linLayFriendsListHolder.addView(txtEmpty);
        }
    }
    /**
     * Goes through each of the friends associated and gets their data
     * @param FriendsID POST variable for the php
     * @param index to place the json into an arraylist
     */
    private void search(final String FriendsID, final int index) {
        class GetUsers extends AsyncTask<Void, Void, String> {
            //ProgressDialog loading;
            //StoringIndex was placed to make sure the int was being stored
            int StoringIndex = index;
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("FriendCode", FriendsID);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_FindFriends, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                findFriends(s, StoringIndex);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(HomeActivity.this, "Loading", "Wait for it...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }
    /**
     * Adds Friends given the JSON Object
     * @param json array for the for loop to go through
     * @param indicator an array indicator
     */
    private void findFriends(String json, int indicator) {
        try {
            Log.d("json", json);
            jsonArray.add(indicator, json);
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                JSONObject jo = search.getJSONObject(i);
                addingFriendsLayout(jo, jo.getString("UniqueCode"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Programmatically creating the layout that will be used to show each of the user's friend's
     * information on a Linear Layout
     * @param jo JSON object
     * @param UniqueCode used to try and match the user's friend with that on the database
     * @throws JSONException
     */
    private void addingFriendsLayout(JSONObject jo, final String UniqueCode) throws JSONException {
        String name = jo.getString("FirstName");
        String image = jo.getString("Image");
        //creating the layout holder
        LinearLayout linLayTemp = new LinearLayout(this);
        //Creating an TextView of the friends name
        txtFriendsName = new TextView(this);
        txtFriendsName.setText(name);
        txtFriendsName.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lptxtName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lptxtName.setMargins(15,0,0,0);
        lptxtName.gravity = Gravity.CENTER_VERTICAL;
        linLayTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            if (jo.getString("UniqueCode").equals(UniqueCode)) {
                                Intent intent = new Intent(getApplicationContext(), ProfileOtherActivity.class);
                                intent.putExtra("profileUnique", jo.getString("UniqueCode"));
                                intent.putExtra("userinfo", userInformation);
                                intent.putExtra("profileFname", jo.getString("FirstName"));
                                intent.putExtra("profileLname", jo.getString("LastName"));
                                intent.putExtra("profileEmail", jo.getString("Email"));
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
                    }
                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        txtFriendsName.setLayoutParams(lptxtName);
        //Creates a ImageView of the friend's users image
        imgFriends = new ImageView(this);
        LinearLayout.LayoutParams lpImageHolder = new LinearLayout.LayoutParams(130, 130);
        byte[] decodedString = Base64.decode(image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFriends.setImageBitmap(decodedByte);
        imgFriends.setLayoutParams(lpImageHolder);
        //Formats the second Linear Layout that will hold the image and text
        linLayTemp.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linParamTemp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linParamTemp.setMargins(0, 0, 6, 6);
        linLayTemp.setLayoutParams(linParamTemp);
        //Instantiate the layout
        linLayFriendsListHolder.addView(linLayTemp);
        linLayTemp.addView(imgFriends);
        linLayTemp.addView(txtFriendsName);
    }

    //----------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Welcome to KCLexchange! Start off by typing a language or a " +
                    "friend's name into the search box - or use the options below to view your " +
                    "profile, see nearby speakers available for exchange, and explore language" +
                    "resources to help you on your way. Any notifications like upcoming meetings" +
                    "or chat messages will be displayed on this page, as well as anybody you add" +
                    "as a quick-contact.").setTitle("Help");

            builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}});

            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textDialog = (TextView)dialog.findViewById(android.R.id.message);
            textDialog.setGravity(Gravity.CENTER);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //----------------------------------------------------------------

    public void onClickProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileOwnActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickResources(View view) {
        Intent i = new Intent(this, ResourcesActivity.class);
        startActivity(i);
    }

    public void onClickMap(View view) {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickVideo(View v){
        Intent intent = new Intent(getApplicationContext(), SampleActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickSearch(View view) {
        stringSearch = txtSearch.getText().toString();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra("stringSearch", stringSearch);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);

        TextView txtMeeting = new TextView(this);
        txtMeeting.setText("beef");
        txtMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            this.finishAffinity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
