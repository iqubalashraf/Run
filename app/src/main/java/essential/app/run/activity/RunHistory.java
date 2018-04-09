package essential.app.run.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import essential.app.run.R;
import essential.app.run.adapter.RunHistoryAdapter;

public class RunHistory extends AppCompatActivity implements View.OnClickListener {
    final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity;
    RecyclerView run_history;
    RunHistoryAdapter runHistoryAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_history);
        initialize();
        initializeListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_run:
                startActivity(new Intent(activity, RunningActivity.class));
                break;
        }
    }

    private void initialize() {
        activity = this;
        run_history = findViewById(R.id.run_history);
        linearLayoutManager = new LinearLayoutManager(activity);
        runHistoryAdapter = new RunHistoryAdapter(activity);
        runHistoryAdapter.setArrayList(essential.app.run.util.SharedPreferences.runData(activity).getArrayList());
        run_history.setLayoutManager(linearLayoutManager);
        run_history.setAdapter(runHistoryAdapter);
    }

    private void initializeListener() {
    }

    private void setData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
