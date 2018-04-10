package essential.app.run.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.Date;

import essential.app.run.R;
import essential.app.run.dataModel.RunData;
import essential.app.run.service.RunningActvityService;
import essential.app.run.util.AllFunction;
import essential.app.run.util.FetchingLocationInBackground;
import essential.app.run.util.SharedPreferences;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by ashrafiqubal on 07/04/18.
 */

public class RunningActivity extends AppCompatActivity implements View.OnClickListener,
        RunningActvityService.UpdateUiData, FetchingLocationInBackground.LocationChangeListener {
    final String TAG = RunningActivity.class.getSimpleName();
    AppCompatActivity activity;
    ImageButton button_start_run;
    Intent forgroundService;
    FetchingLocationInBackground fetchingLocationInBackground = null;
    FusedLocationProviderClient locationClient = null;
    Location oldLocation = null;
    TextView distance, paceAvagrage, duration, textViewPause, textViewStop, textViewRun;
    float totalDistance = 0, totalPace = 0;
    int totalDuration = 0;

    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    long startTime = 0, unix_time = 0;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        initialize();
        initializeListener();
        onClick(findViewById(R.id.textViewRun));
        startForgroundService();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewPause:
                toggleViews(true);
                stopForgroundService();
                if (fetchingLocationInBackground != null)
                    fetchingLocationInBackground.stopLocationUpdates();
                break;
            case R.id.textViewStop:
                if (totalDistance > 10)
                    stopRun();
                finishActivity();
                break;
            case R.id.textViewRun:
                toggleViews(false);
                if (fetchingLocationInBackground != null)
                    fetchingLocationInBackground.startLocationUpdates();
                startForgroundService();
                break;
        }
    }

    private void initialize() {
        activity = this;
        startTime = SystemClock.elapsedRealtime();
        unix_time = new Date().getTime();
        locationClient = getFusedLocationProviderClient(activity);
        distance = findViewById(R.id.distance);
        paceAvagrage = findViewById(R.id.paceAvagrage);
        duration = findViewById(R.id.duration);
        textViewPause = findViewById(R.id.textViewPause);
        textViewStop = findViewById(R.id.textViewStop);
        textViewRun = findViewById(R.id.textViewRun);
        startFetchingLoaction();
    }

    private void initializeListener() {
        textViewPause.setOnClickListener(this);
        textViewStop.setOnClickListener(this);
        textViewRun.setOnClickListener(this);
    }

    @Override
    public void updateUI() {
        updateDistance(totalDistance);
        int time = (int) ((SystemClock.elapsedRealtime() - startTime) / 1000);
        totalDuration = time ;
        String time_to_display = AllFunction.getFormattedTime(time);
        if (time_to_display.length() >= 6) {
            duration.setTextSize(100);
        }
        duration.setText(time_to_display);
        if (totalDistance > 5) {
//            totalPace = (float) AllFunction.calculatePace((int) totalDistance, time);
            paceAvagrage.setText(AllFunction.getPaceToDisplay((int) totalDistance, time));
        }
    }

    private void startForgroundService() {
        forgroundService = new Intent(this, RunningActvityService.class);
        startService(forgroundService);
        RunningActvityService.updateUiData = this;

    }

    private void stopForgroundService() {
        try {
            stopService(forgroundService);
            RunningActvityService.updateUiData = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDistance(float currentDistanceRun) {
        distance.setText(AllFunction.getFormatedDistance(currentDistanceRun, true));
    }

    private void finishActivity() {
        if (fetchingLocationInBackground != null) {
            fetchingLocationInBackground.removeLocationUpdate();
        }
        finish();
    }

    private void startFetchingLoaction() {

        if (fetchingLocationInBackground == null) {
            fetchingLocationInBackground = new FetchingLocationInBackground(activity, locationClient, this);
            fetchingLocationInBackground.getLocationUpdate();
        } else {
            fetchingLocationInBackground.getLocationUpdate();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        calculateDistanceOnLocationFetched(location);
        latitude.add(location.getLatitude());
        longitude.add(location.getLongitude());
    }

    private void calculateDistanceOnLocationFetched(Location location) {
        if (oldLocation != null) {
            if (location.getAccuracy() < 50)
                totalDistance = totalDistance + location.distanceTo(oldLocation);
        }
        oldLocation = location;
    }

    private void toggleViews(boolean isPaused) {
        if (isPaused) {
            textViewPause.setVisibility(View.GONE);
            textViewStop.setVisibility(View.VISIBLE);
            textViewRun.setVisibility(View.VISIBLE);
        } else {
            textViewPause.setVisibility(View.VISIBLE);
            textViewStop.setVisibility(View.GONE);
            textViewRun.setVisibility(View.GONE);
        }
    }

    private void stopRun() {
        RunData runData = new RunData(unix_time, (int) totalDistance,
                (float) AllFunction.calculatePace((int) totalDistance, totalDuration),
                totalDuration, latitude, longitude);
        SharedPreferences.runData(activity, runData);
    }
}
