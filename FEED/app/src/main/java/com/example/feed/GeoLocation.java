package com.example.feed;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

class GeoLocation implements LocationListener {

    Context context;

    public GeoLocation(Context c) {
        this.context = c;
    }

    public Location getLocation() {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        boolean onGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (onGPS) {

            if (ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                return null;
            }

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);

            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            return l;
        }
        else{
            Toast.makeText(context,"Enable GPS",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
