package com.example.juan.gpslocationtracker;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by juan on 4/3/16.
 */
public class GPSTracker extends Service implements LocationListener{
    private Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetEnabled = false;
    boolean canGetLocation = false; //for GPS status
    Location location;
    double lagtitude;
    double longitude;

    private static final long Min_Dis_To_Update = 10; // 10 meters
    private static final long Min_Time_BW_Update = 1; //1 minutes
    protected LocationManager locationManager;

    public GPSTracker(Context context){
        this.mContext = context;
        getLocation();

    }

    public Location getLocation(){

        try{
            this.locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isNetEnabled && isGPSEnabled){
                //do nothing, no connection
            }else{
                canGetLocation = true;
                if(isGPSEnabled){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_Time_BW_Update,Min_Dis_To_Update,this);
                    Log.d("GPS","GPS");
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location != null){
                        lagtitude = location.getLatitude();
                        longitude = location.getLongitude();


                    }
                }else if(isNetEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Min_Time_BW_Update,Min_Dis_To_Update,this);
                    Log.d("NETWORK","NETWORK");
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location != null){
                        lagtitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }



            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return location;

    }

    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }





}
