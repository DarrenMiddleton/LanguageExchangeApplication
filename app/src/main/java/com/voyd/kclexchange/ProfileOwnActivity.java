package com.voyd.kclexchange;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileOwnActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView txtProfileName, txtProfileID, txtProfileGender, txtProfileAge, txtProfileBio,
        txtAgeSex;
    private ImageView imgProfileUser, imgProfileAddRemoveContact;
    private String Name, UniqueCode, Age;
    private String Gender,PracticeLanguage, PracticeLanguageLevel;
    private String TeachingLanguage, TeachingLanguageLevel, Bio, Image ,Gps;
    private String TeachingLanguageComplete, PracticeLanguageComplete;

    private LinearLayout linLayLanguages;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_own);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Your Profile");

        txtProfileName = (TextView) findViewById(R.id.txtProfileName);
        txtProfileID = (TextView) findViewById(R.id.txtProfileID);
        txtProfileBio = (TextView) findViewById(R.id.txtProfileBio);
        imgProfileUser = (ImageView) findViewById(R.id.imgProfileImage);
        linLayLanguages = (LinearLayout) findViewById(R.id.linLayLanguages);
        txtAgeSex = (TextView) findViewById(R.id.txtAgeSex);

        //----------------------------------------

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");

        /*
         Intents are messy and I just want the profile to update after edits without
         having to rewrite, or worse, understand half of this rat nest. The Data
         Store's rough but it works without interfering with other activities
        */
        if(DataStore.getCurrentUser() != null)
            userInformation = DataStore.getCurrentUser();


        Name = userInformation.getFirstName();
        UniqueCode = userInformation.getUniqueCode();
        Age = userInformation.getAge();
        Gender = userInformation.getGender();
        PracticeLanguage = userInformation.PracticeLanguage;
        TeachingLanguage = userInformation.TeachingLanguage;
        Bio = userInformation.getPersonalInterests();
        Image = userInformation.getImageString();
        Gps = userInformation.getGPS();


        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        txtProfileName.setText(Name);
        txtProfileID.setText(UniqueCode);
        txtProfileBio.setText(Bio);
        imgProfileUser.setImageBitmap(decodedByte);
        txtAgeSex.setText(Age + "/" + Gender.substring(0,1));

        addLanguages();
    }

    //----------------------------------------------------------------

    private void addLanguages(){
        String[] sTeach = TeachingLanguage.split(",");
        String[] sPractice = PracticeLanguage.split(",");

        for(int i = 0; i < sTeach.length; i++){
            TextView vLang = new TextView(this);
            vLang.setText(sTeach[i]);
            vLang.setTextSize(20);
            vLang.setTextColor(Color.BLACK);
            i++;
            TextView vLevel = new TextView(this);
            vLevel.setText(sTeach[i]);
            vLevel.setTextSize(20);
            vLevel.setTextColor(Color.BLACK);
            vLevel.setGravity(Gravity.END);
            vLevel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            ImageView imgV = new ImageView(this);
            imgV.setBackgroundColor(Color.argb(255, 205, 205, 205));
            imgV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));

            LinearLayout cont = new LinearLayout(this);
            cont.addView(vLang);
            cont.addView(vLevel);
            linLayLanguages.addView(cont);
            linLayLanguages.addView(imgV);
        }

        for(int i = 0; i < sPractice.length; i++){
            TextView vLang = new TextView(this);
            vLang.setText(sPractice[i]);
            vLang.setTextSize(20);
            vLang.setTextColor(Color.BLACK);
            i++;
            TextView vLevel = new TextView(this);
            vLevel.setText(sPractice[i]);
            vLevel.setTextSize(20);
            vLevel.setTextColor(Color.BLACK);
            vLevel.setGravity(Gravity.END);
            vLevel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            ImageView imgV = new ImageView(this);
            imgV.setBackgroundColor(Color.argb(255, 205, 205, 205));
            imgV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));

            LinearLayout cont = new LinearLayout(this);
            cont.addView(vLang);
            cont.addView(vLevel);
            linLayLanguages.addView(cont);
            if(i != sPractice.length - 1)
                linLayLanguages.addView(imgV);
        }
    }

    public void onClickEditProfile(View v) {
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("This is your profile. There are many like it, but this one is yours." +
                    "It gives you an idea of how other speakers see you on KCLexchange, and allows " +
                    "you to edit your details when you tap the pencil under your picture.").setTitle("Help");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }
}
