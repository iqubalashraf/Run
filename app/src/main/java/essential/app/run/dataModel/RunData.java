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
