package essential.app.run.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import essential.app.run.util.AllFunction;
import essential.app.run.util.SharedPreferences;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(AllFunction.writeFile(new Gson().toJson(SharedPreferences.runData(context)))){
            Toast.makeText(context, "Backup Created", Toast.LENGTH_SHORT).show();
        }
    }
}
