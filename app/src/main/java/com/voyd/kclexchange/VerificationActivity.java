package com.voyd.kclexchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class VerificationActivity extends AppCompatActivity {

    private String fName;
    private String lName;
    private String uniqueCode;
    private String Email ;
    private String age;
    private String password ;
    private String gender;
    private String practicingLanguages;
    private String teachingLanguages;
    private String personalInterest;
    private String image;
    private String ip;
    private String code;
    private String gps;
    private EditText txtVerificationInput;
    private Button btnVefSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        txtVerificationInput = (EditText) findViewById(R.id.txtVerificationInput);
        btnVefSubmit = (Button) findViewById(R.id.btnVefSubmit);

        Intent intent = getIntent();
        fName = intent.getExtras().getString("fname");
        lName = intent.getExtras().getString("lname");
        uniqueCode = intent.getExtras().getString("uniqueCode");
        Email = intent.getExtras().getString("Email");
        age = intent.getExtras().getString("age");
        password= intent.getExtras().getString("password");
        gender = intent.getExtras().getString("gender");
        practicingLanguages = intent.getExtras().getString("practicingLanguages");
        teachingLanguages = intent.getExtras().getString("teachingLanguages");
        personalInterest = intent.getExtras().getString("personalInterest");
        ip = intent.getExtras().getString("ip");
        image = intent.getExtras().getString("image");
        code = intent.getExtras().getString("code");
        gps = intent.getExtras().getString("gps");


        btnVefSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checks the code
                if(txtVerificationInput.getText().toString().trim().equalsIgnoreCase(code)){
                    Log.d("GPS", gps);
                    if(ip!=null)
                        Log.d("ip", ip);

                    addStudent();
                    Intent intent = new Intent(getApplicationContext(),  LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    txtVerificationInput.setError("Not the correct code try again");
                }

            }
        });
    }

    public void addStudent(){

        class AddStudent extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(VerificationActivity.this, "Adding", "Wait", false, false);
            }
            /*protected void onPostExecute(String s){
                super.onPostExecute(s);
            }*/

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Name, fName);
                params.put(Config.Key_ID, uniqueCode);
                params.put(Config.Key_Email, Email);
                params.put(Config.Key_Age, age);
                params.put(Config.Key_LName, lName);
                params.put(Config.Key_Gender, gender);
                params.put(Config.Key_TeachingLanguage, teachingLanguages);
                params.put(Config.Key_PracticeLanguage, practicingLanguages);
                params.put(Config.Key_PersonalInterests, personalInterest);
                params.put(Config.Key_IP, ip);
                params.put(Config.Key_Image, image);
                params.put(Config.Key_GPS, gps);
                params.put(Config.Key_Password, password);
                params.put(Config.Key_Friends, "");
                params.put(Config.Key_Stats, "points,0,meetings,0,messages,0,help,0");

                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_AddUser, params);
                return res;
            }
        }
        AddStudent as = new AddStudent();
        as.execute();
    }

}
