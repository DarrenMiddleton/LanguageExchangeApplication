package com.voyd.kclexchange;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ResourcesActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        TextView barTitle = (TextView) findViewById(R.id.bar_title);
        barTitle.setText("Resources");
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

            builder.setMessage("Tap any of the links to go to the website detailed. The website " +
                    "will open in a new window so you can explore it separate form the exchange").setTitle("Help");


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

    public void onClickLink1(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://how-to-learn-any-language.com/e/languages/index.html"));
        startActivity(browserIntent);
    }

    public void onClickLink2(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kcl.ac.uk/artshums/depts/mlc/study/modules/descrip/index.aspx"));
        startActivity(browserIntent);
    }

    public void onClickLink3(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kcl.ac.uk/artshums/depts/mlc/lrc/LRCindex.aspx"));
        startActivity(browserIntent);
    }

    public void onClickLink4(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.duolingo.com/"));
        startActivity(browserIntent);
    }

    public void onClickLink5(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbc.co.uk/languages/"));
        startActivity(browserIntent);
    }
}

/*
Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kcl.ac.uk/artshums/depts/mlc/lrc/LRCindex.aspx"));
startActivity(browserIntent);

Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://how-to-learn-any-language.com/e/languages/index.html"));
startActivity(browserIntent);
*/
