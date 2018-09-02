package com.voyd.kclexchange;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements LocationListener {

    Toolbar toolbar;
    private EditText txtName, txtRegisterKingsID, txtRegisterPassword, txtRegisterConfirmPassword,
            txtAge, txtAbout;
    private RadioButton radioBtnMale, radioBtnFemale;
    private RadioGroup radioGroupGender;
    private Button btnRemoveTeach, btnRemoveLearn;
    private String stringName, stringKingsID, stringEmail, stringPassword, stringConfirmPassword,
            stringAge, stringAbout, stringLangTeach, stringLangLearn, stringGender, stringImage,
            stringCodeHolder;
    private String stringGPS = "0";

    private ImageView imgUser;

    private ArrayList<Spinner> spinnerLangLearn = new ArrayList<>();
    private ArrayList<Spinner> spinnerLangTeach = new ArrayList<>();
    private ArrayList<Spinner> spinnerLevelLearn = new ArrayList<>();
    private ArrayList<Spinner> spinnerLevelTeach = new ArrayList<>();
    private ArrayList<LinearLayout> spinnerContLearn = new ArrayList<>();
    private ArrayList<LinearLayout> spinnerContTeach = new ArrayList<>();

    private Boolean booleanName = true;
    private Boolean booleanKingsID = true;
    private Boolean booleanPassword = true;
    private Boolean booleanConfirmPassword = true;
    private Boolean booleanGenderSelected = true;
    private Boolean booleanAge = true;
    private static String letterList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 6;
    private static final int RESULT_LOAD_IMAGE = 1;
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
        setContentView(R.layout.activity_register);

        new RetrieveOnlineStatusTask().execute((Void) null);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Sign up");

        txtName = (EditText) findViewById(R.id.txtName);
        txtRegisterKingsID = (EditText) findViewById(R.id.txtRegisterKingsID);
        txtRegisterPassword = (EditText) findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = (EditText) findViewById(R.id.txtRegisterConfirmPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtAbout= (EditText) findViewById(R.id.txtAbout);
        imgUser = (ImageView) findViewById(R.id.imgUser);

        btnRemoveTeach = (Button) findViewById(R.id.btnRemoveLangTeach);
        btnRemoveTeach.setEnabled(false);
        btnRemoveTeach.setBackgroundResource(R.drawable.button_rounded_inactive);
        btnRemoveTeach.setTextColor(Color.argb(255, 51, 51, 51));
        btnRemoveLearn = (Button) findViewById(R.id.btnRemoveLangLearn);
        btnRemoveLearn.setEnabled(false);
        btnRemoveLearn.setBackgroundResource(R.drawable.button_rounded_inactive);
        btnRemoveLearn.setTextColor(Color.argb(255, 51, 51, 51));

        addLangTeach();
        addLangLearn();
        spinnerLangTeach.get(0).setSelection(3);
    }

    //UI generating section
    //----------------------------------------------------------------

    public void onClickAddImage(View view){
        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGallery, RESULT_LOAD_IMAGE);
    }

    private void addLangTeach(){
        spinnerContTeach.add(new LinearLayout(this));
        spinnerContTeach.get(spinnerContTeach.size()-1).setOrientation(LinearLayout.HORIZONTAL);
        spinnerContTeach.get(spinnerContTeach.size()-1).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        spinnerLangTeach.add(new Spinner(this));
        ArrayAdapter<String> adapterSpin1 = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, DataStore.getLanguages());
        spinnerLangTeach.get(spinnerLangTeach.size()-1).setAdapter(adapterSpin1);
        spinnerLangTeach.get(spinnerLangTeach.size()-1).setLayoutParams(new TableLayout.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT, 0.4f));

        spinnerLevelTeach.add(new Spinner(this));
        ArrayAdapter<String> adapterSpin2 = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, DataStore.getLanguageLevels());
        spinnerLevelTeach.get(spinnerLevelTeach.size()-1).setAdapter(adapterSpin2);
        spinnerLevelTeach.get(spinnerLevelTeach.size()-1).setLayoutParams(new TableLayout.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT, 0.6f));

        spinnerContTeach.get(spinnerContTeach.size()-1).addView(spinnerLangTeach.get(spinnerLangTeach.size() - 1));
        spinnerContTeach.get(spinnerContTeach.size()-1).addView(spinnerLevelTeach.get(spinnerLevelTeach.size() - 1));
        ((LinearLayout) findViewById(R.id.linLayLangTeach)).addView(spinnerContTeach.get(spinnerContTeach.size() - 1));
    }

    private void addLangLearn(){
        spinnerContLearn.add(new LinearLayout(this));
        spinnerContLearn.get(spinnerContLearn.size()-1).setOrientation(LinearLayout.HORIZONTAL);
        spinnerContLearn.get(spinnerContLearn.size()-1).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        spinnerLangLearn.add(new Spinner(this));
        ArrayAdapter<String> adapterSpin1 = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, DataStore.getLanguages());
        spinnerLangLearn.get(spinnerLangLearn.size()-1).setAdapter(adapterSpin1);
        spinnerLangLearn.get(spinnerLangLearn.size()-1).setLayoutParams(new TableLayout.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT, 0.4f));

        spinnerLevelLearn.add(new Spinner(this));
        ArrayAdapter<String> adapterSpin2 = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, DataStore.getLanguageLevels());
        spinnerLevelLearn.get(spinnerLevelLearn.size()-1).setAdapter(adapterSpin2);
        spinnerLevelLearn.get(spinnerLevelLearn.size()-1).setLayoutParams(new TableLayout.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT, 0.6f));

        spinnerContLearn.get(spinnerContLearn.size()-1).addView(spinnerLangLearn.get(spinnerLangLearn.size() - 1));
        spinnerContLearn.get(spinnerContLearn.size()-1).addView(spinnerLevelLearn.get(spinnerLevelLearn.size() - 1));
        ((LinearLayout) findViewById(R.id.linLayLangLearn)).addView(spinnerContLearn.get(spinnerContLearn.size() - 1));
    }

    public void onClickAddLangTeach(View v){
        addLangTeach();
        if(spinnerLangTeach.size()>1) {
            btnRemoveTeach.setEnabled(true);
            btnRemoveTeach.setBackgroundResource(R.drawable.button_rounded);
            btnRemoveTeach.setTextColor(Color.argb(255, 00, 00, 00));
        }
    }

    public void onClickRemoveLangTeach(View v){
        spinnerLangTeach.remove(spinnerLangTeach.size() - 1);
        spinnerLevelTeach.remove(spinnerLevelTeach.size() - 1);
        ((ViewGroup)spinnerContTeach.get(spinnerContTeach.size()-1).getParent()).removeView(spinnerContTeach.get(spinnerContTeach.size() - 1));
        spinnerContTeach.remove(spinnerContTeach.size() - 1);

        if(spinnerLangTeach.size()==1){
            btnRemoveTeach.setEnabled(false);
            btnRemoveTeach.setBackgroundResource(R.drawable.button_rounded_inactive);
            btnRemoveTeach.setTextColor(Color.argb(255, 51, 51, 51));
        }
    }

    public void onClickAddLangLearn(View v){
        addLangLearn();
        if(spinnerLangLearn.size()>1) {
            btnRemoveLearn.setEnabled(true);
            btnRemoveLearn.setBackgroundResource(R.drawable.button_rounded);
            btnRemoveLearn.setTextColor(Color.argb(255, 00, 00, 00));
        }
    }

    public void onClickRemoveLangLearn(View v){
        spinnerLangLearn.remove(spinnerLangLearn.size()-1);
        spinnerLevelLearn.remove(spinnerLevelLearn.size() - 1);
        ((ViewGroup)spinnerContLearn.get(spinnerContLearn.size()-1).getParent()).removeView(spinnerContLearn.get(spinnerContLearn.size() - 1));
        spinnerContLearn.remove(spinnerContLearn.size() - 1);

        if(spinnerLangLearn.size()==1){
            btnRemoveLearn.setEnabled(false);
            btnRemoveLearn.setBackgroundResource(R.drawable.button_rounded_inactive);
            btnRemoveLearn.setTextColor(Color.argb(255, 51, 51, 51));
        }
    }

    //Data handling section
    //----------------------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Config.permissionrequest = true;
                    getGPS();
    }else{}return;}}}

    /**
     * Links with EmailSender Class to send an email with the user's email and corresponding email.
     * Code generator method is used, any amount of letters that the code has to have can be changed.
     */
    private void emailSend() {

        stringName = txtName.getText().toString().trim();
        stringEmail = (txtRegisterKingsID.getText().toString().trim())+"@kcl.ac.uk";
        stringCodeHolder = setCodeHolder(4);
        String subject = "One Final Step, Please Register";
        String message =
                "Hey " + stringName + "," + "\n" + "\n"
                        + "Thanks for registering for KCLexchange! To complete your registration please use the following code " + "\n" + "\n"
                        + stringCodeHolder + "\n" + "\n"
                        + "in the application to complete your account verification to be able to use the app" + "\n" + "\n" + "\n"
                        + "This is an automated email please don't reply.\n "
                        + "If you have received this email in error please notify the sender immediately. The email is intended for the exclusive use of the addressee only. " + "\n"
                        + "Copyright, Void©. All rights reserved.";

        /*if statement to check to see if there is a code in the system and resend the same code OR
        create a new random code*/

        EmailSender sendEmail = new EmailSender(this, stringEmail, subject, message);

        sendEmail.execute();
    }

    //@Override
    public void onClickRegister(View v) {

        stringName = txtName.getText().toString();
        stringKingsID = txtRegisterKingsID.getText().toString();
        stringPassword = txtRegisterPassword.getText().toString();
        stringConfirmPassword = txtRegisterConfirmPassword.getText().toString();
        stringAge = txtAge.getText().toString();
        stringAbout = txtAbout.getText().toString();
        stringGender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        stringLangTeach = "";
        stringLangLearn = "";
        for(int i = 0; i<spinnerLangTeach.size();i++){
            stringLangTeach += spinnerLangTeach.get(i).getSelectedItem().toString()
                    +"," + spinnerLevelTeach.get(i).getSelectedItem().toString();
            if(i!=spinnerLangTeach.size()-1){
                stringLangTeach+=",";
            }
        }
        for(int i = 0; i<spinnerLangLearn.size();i++){
            stringLangLearn += spinnerLangLearn.get(i).getSelectedItem().toString()
                    +"," + spinnerLevelLearn.get(i).getSelectedItem().toString();
            if(i!=spinnerLangLearn.size()-1){
                stringLangLearn+=",";
            }
        }


        if (TextUtils.isEmpty(stringName)) {
            booleanName = false;
            txtName.setError("Forgot your Name");
            return;
        } else {booleanName = true; }

        if (!isValidLogin(stringKingsID)) {
            booleanKingsID = false;
            txtRegisterKingsID.setError("Need your K Number (e.g. K1234567)");
        } else {booleanKingsID = true; }

        if (!isValidPassword(stringPassword)) {
            booleanPassword = false;
            txtRegisterPassword.setError("You need a good Password - minimum 6 characters with at least 1 number");
        } else {booleanPassword = true; }

        if (!stringConfirmPassword.equals(stringPassword)) {
            booleanConfirmPassword = false;
            txtRegisterConfirmPassword.setError("Passwords don't match");
        } else {booleanConfirmPassword = true; }

        if (!radioBtnMale.isChecked() && !radioBtnFemale.isChecked()) {
            booleanGenderSelected = false;
            radioBtnMale.setError("Select a gender (just pretend it says \"sex\")");
        } else {booleanGenderSelected = true;}

        if (TextUtils.isEmpty(stringAge)) {
            booleanAge = false;
            txtName.setError("Forgot your Age");
            return;
        } else {booleanAge = true; }

        if (booleanName && booleanKingsID && booleanPassword && booleanConfirmPassword && booleanGenderSelected) {
            emailSend();

            BitmapDrawable drawable = (BitmapDrawable) imgUser.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bb = bos.toByteArray();
            stringImage = Base64.encodeToString(bb, 0);



            Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
            intent.putExtra("fname", stringName);
            intent.putExtra("lname", stringName);
            intent.putExtra("uniqueCode", stringKingsID);
            intent.putExtra("Email", stringEmail);
            intent.putExtra("age", stringAge);
            intent.putExtra("password", stringPassword);
            intent.putExtra("gender", stringGender);
            intent.putExtra("practicingLanguages", stringLangLearn);
            intent.putExtra("teachingLanguages", stringLangTeach);
            intent.putExtra("personalInterest", stringAbout);
            intent.putExtra("image", stringImage);
            intent.putExtra("gps", stringGPS);
            if(stringIp == null){
                stringIp = "No Ip found";
            }
            else {
                intent.putExtra("ip", stringIp);
            }

            intent.putExtra("code", stringCodeHolder);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Activity adapts to the change of the image that is being selected
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imgUser.setImageURI(selectedImage);

            BitmapDrawable drawable = (BitmapDrawable) imgUser.getDrawable();
            Bitmap bitmapOrig = drawable.getBitmap();
            Bitmap bitmapMid;
            Bitmap bitmapEnd;

            if (bitmapOrig.getWidth() >= bitmapOrig.getHeight()){
                bitmapMid = Bitmap.createBitmap(bitmapOrig, bitmapOrig.getWidth()/2 - bitmapOrig.getHeight()/2, 0,
                    bitmapOrig.getHeight(), bitmapOrig.getHeight());

            }else{
                bitmapMid = Bitmap.createBitmap(bitmapOrig, 0, bitmapOrig.getHeight()/2 - bitmapOrig.getWidth()/2,
                    bitmapOrig.getWidth(), bitmapOrig.getWidth());
            }

            bitmapEnd = Bitmap.createScaledBitmap(bitmapMid,256,256, false);
            imgUser.setImageBitmap(bitmapEnd);
        }
    }

    private boolean isValidLogin(String loginString) {
        String loginPattern = "^[Kk]{1}[0-9]{7}$";

        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(loginString);
        return matcher.matches();
    }

    public boolean getIsValidKingsID(String kingsIDHolder) {
        if (isValidLogin(kingsIDHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPassword(String passwordString) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(passwordString);
        return matcher.matches();
    }

    public boolean getIsValidPassword(String passwordHolder) {
        if (isValidPassword(passwordHolder) == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Setting a randomised code, can be used to test to see if it works too.
     * Can changed the code length by changing the parameter
     *
     * @return code generated in a String
     */
    private String codeGenerator(int codeLength) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++)
            codeBuilder.append(letterList.charAt(random.nextInt(letterList.length())));
        return codeBuilder.toString();
    }

    /**
     * Setting a randomised code, can be used to test to see if it works too.
     * Can changed the code length by changing the parameter
     *
     * @return code generated in a String
     */
    public String setCodeHolder(int length) {
        return codeGenerator(length);
    }

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

            builder.setMessage("In KCLexchange, your CEFR proficiency level helps you find people " +
                    "you feel comfortable practicing with, and lets you choose whether you want to " +
                    "challenge yourself with a higher level exchange, or practice the basics with " +
                    "a lower one. The Common European Frame of Reference for languages goes like this:\n" +
                    "A1 - Can understand and use familiar everyday expressions and very basic phrases\n" +
                    "A2 - Can understand sentences and frequently used expressions\n" +
                    "B1 - Can understand the main points of clear standard input on familiar matters\n" +
                    "B2 - Can understand the main ideas of complex text on both concrete and abstract topics\n" +
                    "C1 - Can understand a wide range of demanding, longer clauses\n" +
                    "C2 - Can understand with ease virtually everything heard or read").setTitle("Help");

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

    @SuppressWarnings("ResourceType")
    public void getGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        stringGPS = location.getLatitude() + ", " + location.getLongitude();
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
