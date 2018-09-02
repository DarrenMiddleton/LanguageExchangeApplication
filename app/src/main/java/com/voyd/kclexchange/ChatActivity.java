package com.voyd.kclexchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created as a temporary chat system.
 */
public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText txtInput;
    private LinearLayout linLayMessages;
    private String otherUnique;
    private String CombinedUnique1, CombinedUnique2;
    private String[] messages;
    private String messagesWhole;
    private String msgTemp;
    private TextView txtTemp;
    private String CurrentMessage;
    private Button btnSend;
    private String otherName;
    private String name;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Chat");

        txtInput = (EditText) findViewById(R.id.txtInput);
        linLayMessages = (LinearLayout) findViewById(R.id.linLayMessages);
        btnSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");
        otherUnique = intent.getExtras().getString("profileUnique");
        otherName = intent.getExtras().getString("profileFname");
        name = userInformation.getFirstName();

        CombinedUnique1 = otherUnique + userInformation.getUniqueCode();
        CombinedUnique2 = userInformation.getUniqueCode() + otherUnique;
        CurrentMessage = defaultfill();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (linLayMessages.getChildCount() != 0) {
                                    if(btnSend.isEnabled())
                                        fillMessageRoom();
                                } else {
                                    defaultfill();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    /**
     * Method that retrieves previous messages
     */
    private String defaultfill() {

        class GetUsers extends AsyncTask<Void, Void, String> {

            String s;

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

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                this.s = s;
                super.onPostExecute(s);

                showResult(s);

                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            public String getjson() {
                return s;
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
        return getUsers.getjson();
    }



    /**
     * Method that retrieves previous messagess
     */
    private void fillMessageRoom() {

        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Msgsearch123, params);
                Log.d("Chat Activity", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals(CurrentMessage)){

                } else {
                    linLayMessages.removeAllViews();
                    showResult(s);
                }

                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method to generate the objects for the search result
     *
     * @param json String that contains the json array
     */
    private void showResult(String json) {
        try {
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            messages = search.getJSONObject(0).getString("Messages").split(",-,");
            generateDefaultText(messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------
    public void generateDefaultText(String[] messages) {

        for(int i = 0; i< messages.length; i++){
            if(messages[i].equals(userInformation.getUniqueCode())){
                String msgTemp = messages[i+1];
                TextView txtTemp = new TextView(this);
                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.RIGHT;
                params.setMargins(40, 4, 10, 4);

                int paddingDP = 10;
                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                int paddingPx = (int)(paddingDP * density);

                TextView txtTempName = new TextView(getApplicationContext());
                LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                paramsName.weight = 1.0f;
                paramsName.gravity = Gravity.RIGHT;
                txtTempName.setText(name);
                txtTempName.setTextColor(Color.DKGRAY);
                txtTempName.setLayoutParams(paramsName);
                linLayMessages.addView(txtTempName);

                txtTemp.setBackgroundResource(R.drawable.bg_rounded);
                txtTemp.setTextSize(18);
                txtTemp.setLayoutParams(params);
                txtTemp.setGravity(Gravity.RIGHT);
                linLayMessages.addView(txtTemp);
                txtTemp.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
            } else {
                String msgTemp = messages[i+1];
                TextView txtTemp = new TextView(this);
                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.LEFT;
                params.setMargins(10,4,40,4);

                int paddingDP = 10;
                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                int paddingPx = (int)(paddingDP * density);

                TextView txtTempName = new TextView(getApplicationContext());
                LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                paramsName.weight = 1.0f;
                paramsName.gravity = Gravity.LEFT;
                txtTempName.setText(otherName);
                txtTempName.setTextColor(Color.DKGRAY);
                txtTempName.setLayoutParams(paramsName);
                linLayMessages.addView(txtTempName);

                txtTemp.setBackgroundResource(R.drawable.bg_rounded);
                txtTemp.setTextSize(18);
                txtTemp.setLayoutParams(params);
                linLayMessages.addView(txtTemp);
                txtTemp.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
            }

            i++;
        }
    }


    public void onClickSend(View v){
        if(!txtInput.getText().toString().equals("")){
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.button_rounded_inactive);
            txtTemp = new TextView(this);
            fillMessageRoom12();
            userInformation.setStat("messages", userInformation.getStat("messages") + 1);
            userInformation.updateStudentNoDialog(ChatActivity.this);
        }


        //add msgTemp to message log
    }

    /**
     * Method that retrieves previous messages
     */
    private void fillMessageRoom12() {
        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Msgsearch123, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showResult(s);

                msgTemp = txtInput.getText().toString();

                txtTemp.setText(msgTemp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.gravity = Gravity.RIGHT;
                params.setMargins(40, 4, 10, 4);

                int paddingDP = 10;
                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                int paddingPx = (int)(paddingDP * density);

                txtTemp.setBackgroundResource(R.drawable.bg_rounded);
                txtTemp.setTextSize(18);
                txtTemp.setLayoutParams(params);
                txtTemp.setGravity(Gravity.RIGHT);

                TextView txtTempName = new TextView(getApplicationContext());
                LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
                paramsName.weight = 1.0f;
                paramsName.gravity = Gravity.RIGHT;
                txtTempName.setText(name);
                txtTempName.setTextColor(Color.DKGRAY);
                txtTempName.setLayoutParams(paramsName);
                linLayMessages.addView(txtTempName);

                linLayMessages.addView(txtTemp);
                txtTemp.setPadding(paddingPx, paddingPx,paddingPx,paddingPx);
                txtInput.setText("");
                messagesWhole = "";
                if(messages!=null){
                    if(messages.length == 60){
                        String[] messagesTemp = messages;

                        for(int i = 0; i < 58; i++){
                            messagesTemp[i] = messages[i+2];
                        }

                        messagesTemp[58] = userInformation.getUniqueCode();
                        messagesTemp[59] = msgTemp;

                        for(int i = 0; i < 60; i++){
                            messagesWhole += messagesTemp[i];
                            messagesWhole += ",-,";
                        }
                    } else {
                        for(int i = 0; i < messages.length; i++){
                            messagesWhole += messages[i];
                            messagesWhole += ",-,";
                        }
                        messagesWhole += userInformation.getUniqueCode();
                        messagesWhole += ",-,";
                        messagesWhole += msgTemp;
                    }
                } else {
                    messagesWhole += userInformation.getUniqueCode();
                    messagesWhole += ",-,";
                    messagesWhole += msgTemp;
                }
                sendMessagesToServer(messagesWhole);
                btnSend.setEnabled(true);
                btnSend.setBackgroundResource(R.drawable.button_rounded);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                linLayMessages.removeAllViews();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Method that retrieves previous messages
     */
    private void sendMessagesToServer(final String messageWhole) {
        class GetUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("Combine", CombinedUnique1);
                params.put("Combine2", CombinedUnique2);
                params.put("Message", messagesWhole);
                params.put("Status", userInformation.getUniqueCode());
                params.put("StatusTo", otherUnique);
                params.put("Read", otherUnique);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Msgsubmit, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;
            }

            /**
             * After the response is given from the database
             * @param s json String
             */
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                showResult(s);
                //timerMethod();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    public void onClickLocateContact(View v){

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Here you can chat with a speaker, to exchange or to plan a meeting." +
                    "Although we'd suggest you try to meet in person to get more experience " +
                    "understanding a variety of accents, genders, and dialects of speech.").setTitle("Help");

            builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {}});

            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textDialog = (TextView)dialog.findViewById(android.R.id.message);
            textDialog.setGravity(Gravity.CENTER);

            return true;
        }

        if (id == android.R.id.home) {
            DataStore.setCurrentUser(userInformation);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

}
