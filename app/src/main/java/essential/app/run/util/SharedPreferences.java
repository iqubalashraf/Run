package essential.app.run.util;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import essential.app.run.dataModel.RunData;
import essential.app.run.dataModel.RunDataList;

/**
 * Created by ashrafiqubal on 08/04/18.
 */

public class SharedPreferences {
    private static final String RUN_IDENTIFIER = "essential.app.run.RUN_IDENTIFIER";

    public static RunDataList runData(Context context) {
        final android.content.SharedPreferences prefs = context.getSharedPreferences(RUN_IDENTIFIER,
                Context.MODE_PRIVATE);
        String temp = prefs.getString("runData", "");
//        temp = "{\"arrayList\:" + temp + "}";
        RunDataList runDataList = new RunDataList();
        if (!temp.equalsIgnoreCase("")) {
            runDataList = new Gson().fromJson(temp, RunDataList.class);
        }
        return runDataList;
    }

    public static void runData(Context context, RunData runData) {
        final android.content.SharedPreferences prefs = context.getSharedPreferences(RUN_IDENTIFIER,
                Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        String temp = prefs.getString("runData", "");
        if (temp.equalsIgnoreCase("")) {
            RunDataList runDataList = new RunDataList();
            runDataList.getArrayList().add(runData);
            editor.putString("runData", new Gson().toJson(runDataList));
        } else {
            RunDataList runDataList = new Gson().fromJson(temp, RunDataList.class);
            runDataList.getArrayList().add(runData);
            editor.putString("runData", new Gson().toJson(runDataList));
        }
        editor.apply();
    }
    public static void runData(Context context, ArrayList<RunData> arrayList) {
        final android.content.SharedPreferences prefs = context.getSharedPreferences(RUN_IDENTIFIER, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString("runData", new Gson().toJson(arrayList));
        editor.apply();
    }
    public static void runData(Context context, RunDataList runDataList) {
        final android.content.SharedPreferences prefs = context.getSharedPreferences(RUN_IDENTIFIER, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString("runData", new Gson().toJson(runDataList));
        editor.apply();
    }
}
