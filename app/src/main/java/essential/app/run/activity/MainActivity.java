package essential.app.run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import essential.app.run.R;
import essential.app.run.util.AllFunction;
import essential.app.run.util.SharedPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity;
    ImageButton button_start_run;
    TextView total_kilometer, total_kilometer_static, total_time,
            total_time_static, total_pace, total_pace_static, run_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_start_run:
                startActivity(new Intent(activity, RunningActivity.class));
                break;
            case R.id.run_history:
                startActivity(new Intent(activity, RunHistory.class));
                break;
        }
    }

    private void initialize() {
        activity = this;
        button_start_run = findViewById(R.id.button_start_run);
        total_kilometer = findViewById(R.id.total_kilometer);
        total_kilometer_static = findViewById(R.id.total_kilometer_static);
        total_time = findViewById(R.id.total_time);
        total_time_static = findViewById(R.id.total_time_static);
        total_pace = findViewById(R.id.total_pace);
        total_pace_static = findViewById(R.id.total_pace_static);
        run_history = findViewById(R.id.run_history);
    }

    private void initializeListener() {
        button_start_run.setOnClickListener(this);
        run_history.setOnClickListener(this);
    }

    private void setData(){
        total_kilometer.setText(AllFunction.getTotalDistanceCovered(activity));
        total_time.setText(AllFunction.getTotalTimeInHours(activity));
        total_pace.setText(AllFunction.getAvaragePace(activity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
