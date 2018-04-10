package essential.app.run.dataModel;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 08/04/18.
 */

public class RunData {
    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    private long unix_time = 0;
    private float distance = 0;
    private float pace = 0;
    private int time = 0;

    private String date_of_running = "";
    private String day_of_running = "";
    private String avg_pace = "";
    private String total_run = "";
    private String total_time = "";

    public RunData(ArrayList<Double> latitude, ArrayList<Double> longitude, long unix_time, float distance,
                   float pace, int time, String date_of_running, String day_of_running, String avg_pace,
                   String total_run, String total_time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unix_time = unix_time;
        this.distance = distance;
        this.pace = pace;
        this.time = time;
        this.date_of_running = date_of_running;
        this.day_of_running = day_of_running;
        this.avg_pace = avg_pace;
        this.total_run = total_run;
        this.total_time = total_time;
    }

    public RunData(long unix_time, float distance, float pace, int time,
                   ArrayList<Double> latitude, ArrayList<Double> longitude) {

        this.unix_time = unix_time;
        this.distance = distance;
        this.pace = pace;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RunData(long unix_time, float distance, float pace, int time) {
        this.unix_time = unix_time;
        this.distance = distance;
        this.pace = pace;
        this.time = time;
    }

    public String getDate_of_running() {
        return date_of_running;
    }

    public void setDate_of_running(String date_of_running) {
        this.date_of_running = date_of_running;
    }

    public String getDay_of_running() {
        return day_of_running;
    }

    public void setDay_of_running(String day_of_running) {
        this.day_of_running = day_of_running;
    }

    public String getAvg_pace() {
        return avg_pace;
    }

    public void setAvg_pace(String avg_pace) {
        this.avg_pace = avg_pace;
    }

    public String getTotal_run() {
        return total_run;
    }

    public void setTotal_run(String total_run) {
        this.total_run = total_run;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public ArrayList<Double> getLatitude() {
        return latitude;
    }

    public void setLatitude(ArrayList<Double> latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Double> getLongitude() {
        return longitude;
    }

    public void setLongitude(ArrayList<Double> longitude) {
        this.longitude = longitude;
    }

    public long getUnix_time() {
        return unix_time;

    }

    public void setUnix_time(long unix_time) {
        this.unix_time = unix_time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getPace() {
        return pace;
    }

    public void setPace(float pace) {
        this.pace = pace;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
