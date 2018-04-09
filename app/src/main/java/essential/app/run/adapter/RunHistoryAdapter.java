package essential.app.run.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import essential.app.run.R;
import essential.app.run.dataModel.RunData;
import essential.app.run.util.AllFunction;
import essential.app.run.util.MapDialog;

public class RunHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AppCompatActivity activity;
    private ArrayList<RunData> arrayList = new ArrayList<>();

    public RunHistoryAdapter(AppCompatActivity activity) {

        this.activity = activity;
    }

    public ArrayList<RunData> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<RunData> arrayList) {
        Collections.reverse(arrayList);
        this.arrayList = arrayList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderRunHistory(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_layout_run_history, parent, false));
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final RunData runData = arrayList.get(position);
        ViewHolderRunHistory holderRunHistory = (ViewHolderRunHistory) holder;
        holderRunHistory.tvDate.setText(
                AllFunction.convertUnixTimeToFormattedTime(runData.getUnix_time()));
        holderRunHistory.tvDistance.setText(AllFunction.getFormatedDistance(
                runData.getDistance(), true));
        holderRunHistory.tvPace.setText(AllFunction.formatNumber(runData.getPace()));
        holderRunHistory.tvTime.setText(AllFunction.getFormattedTime(runData.getTime()));
        holderRunHistory.tvRunninDay.setText(AllFunction.getDayFromDate(
                runData.getUnix_time()) + " Running");
        holderRunHistory.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapDialog mapDialog = new MapDialog(activity, runData);
                mapDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private class ViewHolderRunHistory extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvDistance;
        TextView tvPace;
        TextView tvTime;
        private TextView tvRunninDay;
        View v;
        ViewHolderRunHistory(View v) {
            super(v);
            this.v = v;
            tvDate = v.findViewById(R.id.date);
            tvRunninDay = v.findViewById(R.id.tv_runningDay);
            tvDistance = v.findViewById(R.id.distance);
            tvPace = v.findViewById(R.id.pace_value);
            tvTime = v.findViewById(R.id.time_value);
        }

    }
}
