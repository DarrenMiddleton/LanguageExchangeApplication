package com.voyd.kclexchange;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;

public class MeetingActivity extends AppCompatActivity {

    private Spinner spinnerCampuses;
    private static EditText txtSetTime, txtSetDate;
    private Button btnSchConfirm;
    private String[] arrayListCampuses = {"Select a Campus", "Video Chat", "Strand", "Guys", "Waterloo",
            "St Thomas", "Denmark Hill", "The Maughan Library", "Franklin-Wilkins Library", "James Clark Maxwell"};
    private String stringCampus, stringTime, stringDate, userBUnique;
    private UserInformation userInformation;
    Toolbar toolbar;
    TextView txtIntroName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Arrange Meeting");

        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
        userBUnique = intent.getExtras().getString("userB");


        ArrayAdapter<String> adapterLanguages = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayListCampuses);

        spinnerCampuses = (Spinner) findViewById(R.id.spinnerCampuses);
        spinnerCampuses.setAdapter(adapterLanguages);
        txtSetTime = (EditText) findViewById(R.id.txtSetTime);
        txtSetDate = (EditText) findViewById(R.id.txtSetDate);
        btnSchConfirm = (Button) findViewById(R.id.btnSchConfirm);
        txtIntroName = (TextView) findViewById(R.id.txtIntroName);
        txtIntroName.setText(intent.getExtras().getString("userBname"));


        txtSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog(v);
            }
        });

        txtSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog(v);
            }
        });


        btnSchConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerCampuses.getSelectedItem().toString().equals("Select a Campus")){
                    txtSetTime.setError("Select a valid campus");
                }
                else{
                    stringCampus = spinnerCampuses.getSelectedItem().toString();
                    stringTime = txtSetTime.getText().toString();
                    stringDate = txtSetDate.getText().toString();
                    Log.d("User", userInformation.getUniqueCode());
                    Log.d("Userb", userBUnique);
                    Log.d("User3", stringDate);
                    Log.d("User4", stringTime);
                    Log.d("User4", stringCampus);


                    addMeeting();


                }
            }
        });
    }

    public void timePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "ChooseTime");
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            txtSetTime.setText(hour + ":" + minute);
        }
    }

    public void datePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "ChooseDate");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            txtSetDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void addMeeting(){

        class AddMeeting extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_UniqueA, userInformation.getUniqueCode());
                params.put(Config.Key_UniqueB, userBUnique);
                params.put(Config.Key_StatusOfAccepted, "0");
                params.put(Config.Key_DateOFMeet, stringDate);
                params.put(Config.Key_TimeOfMeet, stringTime);
                params.put(Config.Key_StatusBeen, "0");
                params.put(Config.Key_Location, stringCampus);

                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_AddMeeting, params);
                return res;
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                onBackPressed();
            }
        }
        AddMeeting as = new AddMeeting();
        as.execute();
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

            builder.setMessage("Use this page to propose meetings to other speakers on campuses." +
                    "Try to talk to people first to see when they're available.").setTitle("Help");

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
