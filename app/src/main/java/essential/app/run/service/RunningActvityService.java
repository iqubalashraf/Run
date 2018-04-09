package essential.app.run.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import essential.app.run.R;
import essential.app.run.activity.RunningActivity;

/**
 * Created by ashrafiqubal on 07/04/18.
 */

public class RunningActvityService extends Service {
    private static final int NOTIFICATION_ID = 100;
    public static int SITUATION = 0;
    public static UpdateUiData updateUiData = null;
    private final int SITUATION_RUNNING = 0;
    private final int SITUATION_PAUSE = 1;
    private final int SITUATION_STOP = 2;
    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            switch (SITUATION) {
                case SITUATION_RUNNING:
                    customHandler.postDelayed(this, 1000);
                    if (updateUiData != null)
                        updateUiData.updateUI();
                    break;
                case SITUATION_PAUSE:
                    customHandler.postDelayed(this, 1000);
                    break;
                case SITUATION_STOP:
                    stopSelf();
                    break;
            }
        }
    };

    public RunningActvityService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        showForegroundNotification("Get Set Run is tracking your run");
        return START_STICKY;
    }

    public void startTimer() {
        try {
            customHandler.postDelayed(updateTimerThread, 999);
        } catch (Exception e) {
                e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent workIntent) {

        return null;
    }

    private int getNotificationIcon() {
        return R.drawable.ic_launcher_foreground;
    }

    private void showForegroundNotification(String contentText) {
        try {
            Intent notifyIntent = new Intent(getApplicationContext(), RunningActivity.class);
            notifyIntent.setAction("android.intent.action.MAIN");
            notifyIntent.addCategory("android.intent.category.LAUNCHER");
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(contentText)
                    .setSmallIcon(getNotificationIcon())
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(icon)
                    .build();
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            startForeground(NOTIFICATION_ID, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface UpdateUiData {
        void updateUI();
    }
}
