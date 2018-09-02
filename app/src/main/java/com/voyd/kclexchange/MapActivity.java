package com.voyd.kclexchange;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MapActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private UserInformation userInformation;

    private static final String TAG = "MyActivity";

    private LatLng latLng;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mFragment;
    private Marker mCurrLocation;
    public ArrayList<String> jsonArray;
    public int i;

    private static final int LOCATION_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        jsonArray = new ArrayList<>();

        //Builds the map
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");
    }

    //Called when the map is created
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Calls the method to add markers on to the map
        searchLanguage();
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.e(TAG, "Client build " + mGoogleApiClient);
    }

    /**
     * Goes through each of the users practicing language and finds people associated with them
     */
    private void searchLanguage(){
        String[] string = userInformation.getPracticeLanguage();
        for(int x = 0; x<userInformation.getPracticeLanguage().length;x++){
            search(string[x], i);
            i = i+1;
            x++;
        }
    }

    /**
     * Method that goes through each practicing language and gets that users data
     * @param Search POST variable for the php
     * @param index index to place the json into an arraylist
     */
    private void search(final String Search, final int index) {

        class GetUsers extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            //yindex was placed to make sure the int was being stored
            int yindex = index;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.Key_Search, Search);
                RequestHandler rh = new RequestHandler();
                String res = rh.SendPostRequest(Config.URL_Search, params);
                Log.d("AAAA", "doInBackground: " + res);
                return res;

            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                addMarkerThroughData(s, yindex);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MapActivity.this, "Fetching...", "Wait...", false, false);
            }
        }
        GetUsers getUsers = new GetUsers();
        getUsers.execute();
    }

    /**
     * Add's Markers through the given json object
     * @param json = json array for the for loop to go through
     * @param y = array indicator
     */
    private void addMarkerThroughData(String json, int y) {
        try {
            jsonArray.add(y, json);
            JSONArray search = new JSONArray(json);
            Log.d("AAA", search.toString());
            for (int i = 0; i < search.length(); i++) {
                final JSONObject jo = search.getJSONObject(i);
                if(!jo.getString("GPS").equals("0"))
                    addMarkers(jo, jo.getString("UniqueCode"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds markers given the json object
     * @param joobject - specific json objects
     * @param UniqueCode - gives the marker a unique signature
     * @throws JSONException - Incase the json breaks
     */
    public void addMarkers(final JSONObject joobject, final String UniqueCode) throws JSONException {

        String holder = joobject.getString("GPS");
        //splits the users teaching lanugages with this
        String[] parts = holder.split(",");
        Double lat = Double.parseDouble(parts[0]);
        Double lng = Double.parseDouble(parts[1]);

        //Log.d is to verify the lng is working
        Log.d(TAG, "Long" + lng);

        //Makes the coordinate for the marker
        LatLng latlng2 = new LatLng(lat,lng);
        MarkerOptions markersO = new MarkerOptions();
        markersO.position(latlng2);
        markersO.snippet(joobject.getString("FirstName"));

        //Gets the String for the image and decodes it to a bitmap
        byte[] decodedString = Base64.decode(joobject.getString("Image"), 0);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //Gives the marker
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_info_window, null);
        ImageView imgView = (ImageView) marker.findViewById(R.id.imgperson);
        imgView.setImageBitmap(decodedByte);
        final MarkerOptions icon = markersO.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker)));
        markersO.title(joobject.getString("UniqueCode"));

        //Adds a onclick listener
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                try {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        JSONArray profileSearch = new JSONArray(jsonArray.get(x));
                        for (int i = 0; i < profileSearch.length(); i++) {
                            JSONObject jo = profileSearch.getJSONObject(i);
                            if (jo.getString("UniqueCode").equals(marker.getTitle())) {
                                Log.d("FirstName", jo.getString("FirstName"));
                                Intent intent = new Intent(getApplicationContext(), ProfileOtherActivity.class);
                                intent.putExtra("profileFname", jo.getString("FirstName"));
                                intent.putExtra("profileLname", jo.getString("LastName"));
                                intent.putExtra("profileUnique", jo.getString("UniqueCode"));
                                intent.putExtra("profileAge", jo.getString("Age"));
                                intent.putExtra("profileGender", jo.getString("Gender"));
                                intent.putExtra("profilePracticingLanguage", jo.getString("PracticeLanguage"));
                                intent.putExtra("profileTeachingLanguage", jo.getString("TeachingLanguage"));
                                intent.putExtra("profilePersonalInterest", jo.getString("PersonalInterests"));
                                intent.putExtra("profileImage", jo.getString("Image"));
                                // intent.putExtra("profileGps", jo.getString("GPS"));
                                intent.putExtra("userinfo", userInformation);
                                startActivity(intent);
                            }
                        }
                        }
                    }catch(JSONException e) {
                    e.printStackTrace();
                }
                    return true;
            }
        });
        //adds the marker onto the map
        final Marker searchLocation = mGoogleMap.addMarker(markersO);
    }

    //Generates the bitmap for the icon
    public static Bitmap createDrawableFromView(Context context, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        //Checks permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                    LOCATION_REQUEST_CODE);
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient); //Gets the last known location

        if (mLastLocation != null) {
            //place marker at last position if available

            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            MarkerOptions markerOptions;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(13).build();

            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            mCurrLocation = mGoogleMap.addMarker(markerOptions);
            Log.e(TAG, "LATLNG last" + latLng);
        }

        //Sends for location updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    //Called to open a popup box asking user for location permission
    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Called when the user click the marker pop up

        marker.showInfoWindow();
        System.out.println("Info window has been clicked!");
    }
}

