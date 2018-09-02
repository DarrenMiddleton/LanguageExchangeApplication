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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.voyd.kclexchange.ui.SampleActivity;

import java.util.ArrayList;

public class ProfileOtherActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_profile_other);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Profile");

        txtProfileName = (TextView) findViewById(R.id.txtProfileName);
        txtProfileID = (TextView) findViewById(R.id.txtProfileID);
        txtProfileBio = (TextView) findViewById(R.id.txtProfileBio);
        imgProfileUser = (ImageView) findViewById(R.id.imgProfileImage);
        imgProfileAddRemoveContact = (ImageView) findViewById(R.id.imgAddContact);
        linLayLanguages = (LinearLayout) findViewById(R.id.linLayLanguages);
        txtAgeSex = (TextView) findViewById(R.id.txtAgeSex);

        //----------------------------------------

        Intent intent = getIntent();
        userInformation = (UserInformation) intent.getSerializableExtra("userinfo");
        Name = intent.getExtras().getString("profileFname");
        UniqueCode = intent.getExtras().getString("profileUnique");
        Age = intent.getExtras().getString("profileAge");
        Gender = intent.getExtras().getString("profileGender");
        PracticeLanguage = intent.getExtras().getString("profilePracticingLanguage");
        TeachingLanguage = intent.getExtras().getString("profileTeachingLanguage");
        Bio = intent.getExtras().getString("profilePersonalInterest");
        Image = intent.getExtras().getString("profileImage");
        Gps = intent.getExtras().getString("profileGps");


        byte[] decodedString = Base64.decode(Image, 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        txtProfileName.setText(Name);
        txtProfileID.setText(UniqueCode);
        txtProfileBio.setText(Bio);
        imgProfileUser.setImageBitmap(decodedByte);
        txtAgeSex.setText(Age + "/" + Gender.substring(0,1));

        if(userInformation.searchFriendsList(UniqueCode) == true){
            imgProfileAddRemoveContact.setImageResource(R.drawable.ic_person_remove_black_24dp);
        } else {
            imgProfileAddRemoveContact.setImageResource(R.drawable.ic_person_add_black_24dp);
        }

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

    public void onClickAddRemoveContact(View v) {
        if(userInformation.searchFriendsList(UniqueCode) == true){
            userInformation.modifyFriends(UniqueCode);
            userInformation.updateStudent(ProfileOtherActivity.this);
            imgProfileAddRemoveContact.setImageResource(R.drawable.ic_person_add_black_24dp);
            DataStore.setCurrentUser(userInformation);
        } else {
            userInformation.addFriends(UniqueCode);
            userInformation.updateStudent(ProfileOtherActivity.this);
            imgProfileAddRemoveContact.setImageResource(R.drawable.ic_person_remove_black_24dp);
            DataStore.setCurrentUser(userInformation);
        }
    }

    public void onClickMessageContact(View v){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("profileUnique", UniqueCode);
        intent.putExtra("profileFname", Name);
        intent.putExtra("profileImage", Image);
        intent.putExtra("userinfo", userInformation);
        startActivity(intent);
    }

    public void onClickMeeting(View v){
        Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
        intent.putExtra("userinfo", userInformation);
        intent.putExtra("userB", UniqueCode);
        intent.putExtra("userBname", Name);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("So this is a speaker profile, where you can see a person's basic " +
                    "information, languages, and choose to interact with them. You can press the add" +
                    "button to add them as a quick-contact, where they'll be added to you home page" +
                    "for easy access. You can also tap the chat button and talk to them to exchange" +
                    "or plan a meeting. There's also the meeting scheduler, which can be used to set" +
                    "a time, campus, and date to help remember a meeting.").setTitle("Help");

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
