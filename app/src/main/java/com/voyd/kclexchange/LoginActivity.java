package com.voyd.kclexchange;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements LocationListener {

    private Toolbar toolbar;
    private String stringUpdateGps = "0";
    private boolean userfill;
    private EditText txtLogin, txtPassword;
    private String password, UniqueCode;
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 6;
    private String stringIp;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                }
            }
            else{
                getGPS();
                Config.permissionrequest = true;
            }
        }
        else{
            getGPS();
            Config.permissionrequest = true;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("GPS STRING ", stringUpdateGps);

        userfill = true;
        txtLogin = (EditText) findViewById(R.id.txtKNumber);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        new RetrieveOnlineStatusTask().execute((Void) null);
    }

    //----------------------------------------------------------------

    public void onClickLogin(View view) {
        //check for good login info

        String stringLogin = txtLogin.getText().toString().trim();
        String stringPassword = txtPassword.getText().toString().trim();
        UniqueCode = stringLogin;
        password = stringPassword;
        userChecker();

        if (!isValidLogin(stringLogin) && stringPassword.isEmpty()) {
            txtLogin.setError("Please enter a valid King's ID (e.g. K1234567) Or fill in a password");
        } else if (userfill == false) {
            txtLogin.setError("Incorrect Password Or Login");
        } else if(Config.permissionrequest) {
            getGPS();
        }

    }

    public void onClickNewUser(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void onClickForgotPass(View view) {
        Intent i = new Intent(this,RecoveryActivity.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Config.permissionrequest = true;
                    getGPS();


                } else {

                }
                return;
            }

        }
    }

    private UserInformation userChecker() {
        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            UserInformation userInformation1;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Password, password);
                params.put(Config.Key_ID, UniqueCode);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_CheckLogin, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;

            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(RetrieveUser(s)!=null){
                    userInformation1 = RetrieveUser(s);
                    if(stringIp == null){
                        stringIp = "No Ip found";
                    }
                    else {
                        userInformation1.updateIp(stringIp);
                    }
                    userInformation1.updateGPS(stringUpdateGps);
                    userInformation1.updateStudent(LoginActivity.this);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("userinfo", userInformation1);
                    Log.d("IP", stringIp);
                    Log.d("GPS", stringUpdateGps);
                    System.out.print(userInformation1.getFirstName());
                    DataStore.setCurrentUser(userInformation1);

                    startActivity(intent);
                    finish();
                } else {
                    txtLogin.setError("Incorrect Password Or Login");
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Fetching...", "Wait...", false, false);
            }

            public UserInformation getUserInformation1() {
                return userInformation1;
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();


        return getUsers.getUserInformation1();
    }

    private UserInformation RetrieveUser(String json) {
        UserInformation userInformation2 = null;
        try {
            if (json != null) {
                Log.d("JSON", json);
                JSONArray userInfo = new JSONArray(json);
                JSONObject userObject = userInfo.getJSONObject(0);
                Log.d("AAA", userInfo.toString());
                Log.d("RESULT", userObject.getString("UniqueCode"));
                if (!userObject.getString("UniqueCode").equals("JSON")) {
                    userInformation2 = new UserInformation(userObject.getString("UniqueCode"), userObject.getString("FirstName"), userObject.getString("Image"), userObject.getString("LastName"),
                            userObject.getString("Email"), userObject.getString("password"), userObject.getString("Age"), userObject.getString("Gender"), userObject.getString("TeachingLanguage"),
                            userObject.getString("PracticeLanguage"), userObject.getString("PersonalInterests"), userObject.getString("Friends"), userObject.getString("GPS"), userObject.getString("Stats"),
                            userObject.getString("IP"));
                } else {
                    txtLogin.setError("Incorrect Password Or Login");
                }
            }
        } catch (JSONException e) {}
        return userInformation2;
    }

    /**
     * Checks to see if what the user has inputted as a login is a valid set of characters that matches
     * a King's student User ID.
     *
     * @param loginString
     * @return
     */
    private boolean isValidLogin(String loginString) {
        String loginPattern = "^[Kk]{1}[0-9]{7}$";

        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(loginString);
        return matcher.matches();
    }

    /**
     * To test any inputted king's ID in the login to see if it passes
     *
     * @param kingsIDHolder
     * @return
     */
    public boolean getIsValidKingsID(String kingsIDHolder) {
        if (isValidLogin(kingsIDHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("ResourceType")
    public void getGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        stringUpdateGps = location.getLatitude() + ", " + location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class RetrieveOnlineStatusTask extends AsyncTask<Void, Void, BufferedReader> {

        @Override
        protected BufferedReader doInBackground(Void... arg0) {
            URL url;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL("http://ip2country.sourceforge.net/ip2c.php?format=JSON");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append('\r');
                }
                bufferedReader.close();
                inputStreamReader.close();
                JSONObject json_data = new JSONObject(buffer.toString());
                stringIp = json_data.getString("ip");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
