package com.voyd.kclexchange;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class RecoveryActivity extends AppCompatActivity {

    Toolbar toolbar;
    String stringName;
    EditText txtKnumber;
    Button btnDone;
    TextView txtExplain;
    String stringEmail, UniqueCode, stringIp, NewPassword;
    private static String letterList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Recover Password");

        txtKnumber = (EditText) findViewById(R.id.txtKnumber1);
        btnDone = (Button) findViewById(R.id.btnDone);
        txtExplain = (TextView) findViewById(R.id.txtExplain);


        btnDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NewPassword = codeGenerator(7);
                stringEmail = (txtKnumber.getText().toString().trim())+"@kcl.ac.uk";
                PasswordRenew();
                emailSend();
                txtKnumber.setVisibility(View.GONE);
                btnDone.setVisibility(View.GONE);
                txtExplain.setText("An email has been successfully sent with your new password!\n" + "Please change it as soon as possible.");

            }
        });
    }


    private void emailSend()
    {
        stringEmail = (txtKnumber.getText().toString().trim())+"@kcl.ac.uk";
        UniqueCode = txtKnumber.getText().toString().trim();
        stringName = txtKnumber.getText().toString().trim();


        String subject = "KCLexchange Reset Password";
        String message =
                "Dear user " + stringName + "," + "\n" + "\n"
                        + "So you misplaced your KCLexchange password? Smart." + "\n"
                        + "\n"
                        + "No problem. Just use this temporary password:" + "\n"
                        + "\n"
                        + NewPassword + "\n"
                        + "\n"
                        + "to sign into your account, then change it to something else straight away."+ "\n"
                        + "\n"
                        + "This is an automated email please don't reply.\n "
                        + "If you have received this email in error please notify the sender immediately. The email is intended for the exclusive use of the addressee only. " + "\n"
                        + "Copyright, Void©. All rights reserved.";

        EmailSender sendEmail = new EmailSender(this, stringEmail, subject, message);

        sendEmail.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private String codeGenerator(int codeLength)
    {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++)
            codeBuilder.append(letterList.charAt(random.nextInt(letterList.length())));
        return codeBuilder.toString();
    }





    private UserInformation PasswordRenew()
    {
        class GetUsers extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            UserInformation userInformation1;

            @Override
            protected String doInBackground(Void... v)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_ID, UniqueCode);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_PasswordRenewal, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;

            }

            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                if(RetrieveUser(s)!=null)
                {
                    userInformation1 = RetrieveUser(s);
                    if(stringIp == null)
                    {
                        stringIp = "No Ip found";
                    }
                    else {
                        userInformation1.updateIp(stringIp);
                    }
                    userInformation1.updatePassword(NewPassword);
                    userInformation1.updateStudent(RecoveryActivity.this);
                    Log.d("IP", stringIp);
                    System.out.print(userInformation1.getFirstName());
                    DataStore.setCurrentUser(userInformation1);
                    finish();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RecoveryActivity.this, "Generating...", "Sending...", false, false);
            }

            public UserInformation getUserInformation1() {
                return userInformation1;
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();



        return getUsers.getUserInformation1();
    }



    private UserInformation RetrieveUser(String json)
    {
        UserInformation userInformation2 = null;
        try
        {
            if (json != null)
            {
                Log.d("JSON", json);
                JSONArray userInfo = new JSONArray(json);
                JSONObject userObject = userInfo.getJSONObject(0);
                Log.d("AAA", userInfo.toString());
                Log.d("RESULT", userObject.getString("UniqueCode"));
                if (!userObject.getString("UniqueCode").equals("JSON"))
                {
                    userInformation2 = new UserInformation(userObject.getString("UniqueCode"), userObject.getString("FirstName"), userObject.getString("Image"), userObject.getString("LastName"),
                            userObject.getString("Email"), userObject.getString("password"), userObject.getString("Age"), userObject.getString("Gender"), userObject.getString("TeachingLanguage"),
                            userObject.getString("PracticeLanguage"), userObject.getString("PersonalInterests"), userObject.getString("Friends"), userObject.getString("GPS"), userObject.getString("Stats"),
                            userObject.getString("IP"));
                }
            }
        } catch (JSONException e) {}
        return userInformation2;
    }
}