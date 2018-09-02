package com.voyd.kclexchange;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class EditProfileActivity extends AppCompatActivity{

    Toolbar toolbar;
    private EditText txtName, txtRegisterPassword, txtRegisterConfirmPassword,
            txtAge, txtAbout;
    private RadioButton radioBtnMale, radioBtnFemale;
    private RadioGroup radioGroupGender;
    private Button btnRemoveTeach, btnRemoveLearn;
    private String stringName, stringEmail, stringPassword, stringConfirmPassword,
            stringAge, stringAbout, stringLangTeach, stringLangLearn, stringGender, stringImage;

    private ImageView imgUser;
    private boolean imgChanged = false;

    private ArrayList<Spinner> spinnerLangLearn = new ArrayList<>();
    private ArrayList<Spinner> spinnerLangTeach = new ArrayList<>();
    private ArrayList<Spinner> spinnerLevelLearn = new ArrayList<>();
    private ArrayList<Spinner> spinnerLevelTeach = new ArrayList<>();
    private ArrayList<LinearLayout> spinnerContLearn = new ArrayList<>();
    private ArrayList<LinearLayout> spinnerContTeach = new ArrayList<>();

    private Boolean booleanName = true;
    private Boolean booleanPassword = true;
    private Boolean booleanConfirmPassword = true;
    private Boolean booleanGenderSelected = true;
    private Boolean booleanAge = true;

    private static final int RESULT_LOAD_IMAGE = 1;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Edit profile");

        txtName = (EditText) findViewById(R.id.txtName);
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

        loadValues();
    }

    //UI generating section
    //----------------------------------------------------------------

    public void loadValues(){
        String[] sTeaching = userInformation.getTeachingLanguage();

        for(int i = 0; i < sTeaching.length; i++){
            for(int j = 0; j < DataStore.getLanguages().length; j++){
                if(DataStore.getLanguages()[j].equals(sTeaching[i])){
                    addLangTeach();
                    spinnerLangTeach.get(spinnerLangTeach.size()-1).setSelection(j);

                    btnRemoveTeach.setEnabled(true);
                    btnRemoveTeach.setBackgroundResource(R.drawable.button_rounded);
                    btnRemoveTeach.setTextColor(Color.argb(255, 00, 00, 00));
                    break;
                }
            }

            i++;

            for(int j = 0; j < DataStore.getLanguageLevels().length; j++){
                if(DataStore.getLanguageLevels()[j].equals(sTeaching[i])){
                    spinnerLevelTeach.get(spinnerLevelTeach.size()-1).setSelection(j);
                    break;
                }
            }
        }


        String[] sPractice = userInformation.getPracticeLanguage();

        for(int i = 0; i < sPractice.length; i++){
            for(int j = 0; j < DataStore.getLanguages().length; j++){
                if(DataStore.getLanguages()[j].equals(sPractice[i])){
                    addLangLearn();
                    spinnerLangLearn.get(spinnerLangLearn.size()-1).setSelection(j);

                    btnRemoveLearn.setEnabled(true);
                    btnRemoveLearn.setBackgroundResource(R.drawable.button_rounded);
                    btnRemoveLearn.setTextColor(Color.argb(255, 00, 00, 00));
                    break;
                }
            }

            i++;

            for(int j = 0; j < DataStore.getLanguageLevels().length; j++){
                if(DataStore.getLanguageLevels()[j].equals(sPractice[i])){
                    spinnerLevelLearn.get(spinnerLevelLearn.size()-1).setSelection(j);
                    break;
                }
            }
        }


        txtName.setText(userInformation.getFirstName());
        txtRegisterPassword.setText(userInformation.getPassword());
        txtRegisterConfirmPassword.setText(userInformation.getPassword());

        if(userInformation.getGender().equals("Female")){
            radioBtnFemale.setChecked(true);
        } else {
            radioBtnMale.setChecked(true);
        }

        txtAge.setText(userInformation.getAge());
        txtAbout.setText(userInformation.getPersonalInterests());

        byte[] decodedString = Base64.decode(userInformation.getImageString(), 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgUser.setImageBitmap(decodedByte);
    }

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

    public void onClickSave(View v) {

        stringName = txtName.getText().toString();
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

        if (booleanName && booleanPassword && booleanConfirmPassword && booleanGenderSelected) {

            userInformation.updateFirstName(txtName.getText().toString());
            userInformation.updatePassword(txtRegisterPassword.getText().toString());
            stringGender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
            userInformation.updateGender(stringGender);
            userInformation.updateAge(txtAge.getText().toString());
            userInformation.updatePersonal(txtAbout.getText().toString());

            userInformation.updateTeaching(stringLangTeach);
            userInformation.updatePractice(stringLangLearn);

            if(imgChanged){
                BitmapDrawable drawable = (BitmapDrawable) imgUser.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bb = bos.toByteArray();
                stringImage = Base64.encodeToString(bb, 0);

                userInformation.updateImage(stringImage);
            }

            userInformation.updateStudent(EditProfileActivity.this);

            DataStore.setCurrentUser(userInformation);

            Intent intent = new Intent(getApplicationContext(), ProfileOwnActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("userinfo", userInformation);
            startActivity(intent);
            //finish();
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

            imgChanged = true;

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
}
