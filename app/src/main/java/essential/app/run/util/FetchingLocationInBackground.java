package essential.app.run.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import essential.app.run.R;

/**
 * Created by ashrafiqubal on 07/04/18.
 */

public class FetchingLocationInBackground {
    final String TAG = FetchingLocationInBackground.class.getSimpleName();

    Activity activity;
    private FusedLocationProviderClient locationClient = null;
    private LocationChangeListener locationChangeListener = null;
    private LocationRequest mLocationRequest = null;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 2 secs */
    private long FASTEST_INTERVAL = 1000; /* 1 sec */
    private boolean isPaused = true;
    private LocationCallback locationCallback;

    public FetchingLocationInBackground(Activity activity,
                                        FusedLocationProviderClient locationClient,
                                        LocationChangeListener locationChangeListener) {
        this.activity = activity;
        this.locationClient = locationClient;
        this.locationChangeListener = locationChangeListener;
    }

    public void startLocationUpdates() {
        isPaused = false;
    }

    public void stopLocationUpdates() {
        isPaused = true;
    }

    public void getLocationUpdate() {
        isPaused = false;
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setSmallestDisplacement(10);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // do work here
                if (!isPaused)
                    if (locationChangeListener != null)
                        locationChangeListener.onLocationChanged(locationResult.getLastLocation());
            }
        };

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationClient.requestLocationUpdates(mLocationRequest,locationCallback , Looper.myLooper());
        }
    }

    public void removeLocationUpdate(){
        locationClient.removeLocationUpdates(locationCallback);
    }

    public interface LocationChangeListener {
        void onLocationChanged(Location location);
    }
}
