package essential.app.run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import essential.app.run.R;
import essential.app.run.dataModel.RunData;
import essential.app.run.dataModel.RunDataList;
import essential.app.run.util.AllFunction;
import essential.app.run.util.SharedPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity;
    ImageButton button_start_run;
    TextView total_kilometer, total_kilometer_static, total_time,
            total_time_static, total_pace, total_pace_static, run_history, back_up_data;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeListener();

        /*ArrayList<RunData> arrayList = SharedPreferences.runData(activity).getArrayList();
        for (int i=0; i <arrayList.size(); i++){
            RunData runData = arrayList.get(i);
            arrayList.get(i).setDate_of_running(AllFunction.convertUnixTimeToFormattedTime(runData.getUnix_time()));
            arrayList.get(i).setDay_of_running(AllFunction.getDayFromDate(
                    runData.getUnix_time()) + " Running");
            arrayList.get(i).setAvg_pace(AllFunction.formatNumber(runData.getPace())
                    .replace(".", "\'"));
            arrayList.get(i).setTotal_run(AllFunction.getFormatedDistance(
                    runData.getDistance(), true));
            arrayList.get(i).setTotal_time(AllFunction.getFormattedTime(runData.getTime()));
        }
        RunDataList runDataList = new RunDataList();
        runDataList.setArrayList(arrayList);
        SharedPreferences.runData(activity, runDataList);*/
//        gson.fromJson(AllFunction.readFile(), RunDataList.class);
//        AllFunction.readFile();
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
            case R.id.back_up_data:
                AllFunction.writeFile(gson.toJson(SharedPreferences.runData(activity)));
                break;
        }
    }

    private void initialize() {
        activity = this;
        gson = new Gson();
        button_start_run = findViewById(R.id.button_start_run);
        total_kilometer = findViewById(R.id.total_kilometer);
        total_kilometer_static = findViewById(R.id.total_kilometer_static);
        total_time = findViewById(R.id.total_time);
        total_time_static = findViewById(R.id.total_time_static);
        total_pace = findViewById(R.id.total_pace);
        total_pace_static = findViewById(R.id.total_pace_static);
        run_history = findViewById(R.id.run_history);
        back_up_data = findViewById(R.id.back_up_data);
    }

    private void initializeListener() {
        button_start_run.setOnClickListener(this);
        run_history.setOnClickListener(this);
        back_up_data.setOnClickListener(this);

    }

    private void setData(){
        total_kilometer.setText(AllFunction.getTotalDistanceCovered(activity));
        total_time.setText(AllFunction.getTotalTimeInHours(activity));
        total_pace.setText(AllFunction.getAvaragePace(activity).replace(".", "\'"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
