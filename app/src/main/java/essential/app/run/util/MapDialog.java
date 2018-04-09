package essential.app.run.util;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import essential.app.run.R;
import essential.app.run.dataModel.RunData;

public class MapDialog extends Dialog {
    AppCompatActivity activity;
    SupportMapFragment mapFragment;
    RunData runData;
    public MapDialog(@NonNull AppCompatActivity activity, RunData runData) {
        super(activity);
        this.activity = activity;
        this.runData = runData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_map);
        initializeViews();
        setDataToView();
    }

    private void initializeViews() {
        mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.map_custom_dialog_for_map);
    }

    private void setDataToView() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap googleMap) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AllFunction.drawMap(activity, googleMap, runData);
                    }
                }, 100);
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        try {
            activity.getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
