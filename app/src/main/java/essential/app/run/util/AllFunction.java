package essential.app.run.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import essential.app.run.R;
import essential.app.run.dataModel.RunData;
import essential.app.run.dataModel.RunDataList;

/**
 * Created by ashrafiqubal on 07/04/18.
 */

public class AllFunction {
    public static final String dateFormat = "hh:mm";
    public static final String DATE_FORMAT_ddMMyy = "ddMMyy";
    public static final String DATE_FORMAT_yyyy_dash_MM_dash_dd = "yyyy-MM-dd";
    public static final String DATE_FORMAT_dd_dash_MMM_dash_yyyy = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_dd_dash_MM_dash_yyyy = "dd-MM-yyyy";
    public static final String DATE_FORMAT_dd_mdash_MMM = "dd—MMM";
    public static final String DATE_FORMAT_dd_space_MMM = "dd MMM";
    public static final String DATE_FORMAT_dd_slash_mm_slash_yyyy = "dd/MM/yyyy";
    public static final String DATE_FORMAT_EEE_space_comma_d_space_LLL = "EEE, d LLL";
    public static final String DATE_FORMAT_d_space_LLL = "d LLL";
    public static final String DATE_FORMAT_d_space_LLL_space_yyyy = "d LLL yyyy";
    public static final String DATE_FORMAT_dd = "dd";
    public static final String DATE_FORMAT_MMMM_yyyy = "MMMM yyyy";
    public static final String DATE_FORMAT_MM_YYYY = "MM";
    public static final String DATE_FORMAT_EEEE = "EEEE";
    public static final String DATE_FORMAT_dd_space_MM_space_yyyy = "dd MM yyyy";
    //    public static int MARATHON_TILL_EARTH = 1800;
    public static final String DATE_FORMAT_dd_space_MMM_space_yyyy = "dd MMM yyyy";
    public static final String DATE_FORMAT_dd_dot_MMM_dot_yyyy = "dd.MMM.yyyy";
    public static final String DATE_FORMAT_yyyy_dash_MM_dash_dd_space_HH_colon_mm_colon_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_dd_dash_MMM_dash_yyyy_comma_HH_colon_mm_colon_am_pm = "dd-MMM-yyyy, HH:mm a";
    public static final String DATE_FORMAT_MMM_space_dd_comma_space_yyyy_space_HH_colon_mm_colon_ss_space_a = "MMM dd, yyyy HH:mm:ss a";

    public static String getFormatedDistance(float distanceCovered, boolean isUnitInKm) {
        String distance = "00.00";
        if (isUnitInKm) {
            distanceCovered = distanceCovered / 1000;
            distance = new BigDecimal(distanceCovered).setScale(2, BigDecimal.ROUND_FLOOR).toString();
            if (distanceCovered < 10) {
                return "0" + distance;
            }
            return distance;
        } else {
            distance = new BigDecimal(distanceCovered * 0.000621371).setScale(2, BigDecimal.ROUND_FLOOR).toString();
            if (distanceCovered * 0.000621371 < 10) {
                return "0" + distance;
            }
            return distance;
        }
    }

    public static String getFormattedTime(int time) {

        int hour  = time/3600;
        int min = (time/60)%60;
        int sec = time%60;

        if(hour == 0){
            return String.format(Locale.ENGLISH, "%02d:%02d", min, sec);
        }else {
            return String.format(Locale.ENGLISH,"%02d:%02d:%02d", hour, min, sec);
        }

    }

    public static double calculatePace(int distanceCovered, int time) {
        try {
            return (time * 16.666666667) / distanceCovered;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getPaceToDisplay(int distanceCovered, int time) {
        double temp = time * 16.666666667;
        int t1 = (int)temp/distanceCovered;
        int t2 = (int) temp%distanceCovered;
        return String.format(Locale.ENGLISH, "%02d'%02d", t1, t2);
    }

    public static String getTotalTimeInHours(Context context) {
        int totalDuration = 0;
        RunDataList runDataList = SharedPreferences.runData(context);
        ArrayList<RunData> arrayList = runDataList.getArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            totalDuration = totalDuration + arrayList.get(i).getTime();
        }
        return formatNumber((double) totalDuration / 3600);
    }

    public static String getTotalDistanceCovered(Context context) {
        float totalDistance = 0;
        RunDataList runDataList = SharedPreferences.runData(context);
        ArrayList<RunData> arrayList = runDataList.getArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            totalDistance = totalDistance + arrayList.get(i).getDistance();
        }
        return getFormatedDistance(totalDistance, true);
    }

    public static String getAvaragePace(Context context) {
        float totalDistance = 0;
        int totalDuration = 0;
        double totalPace = 0;
        RunDataList runDataList = SharedPreferences.runData(context);
        ArrayList<RunData> arrayList = runDataList.getArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            totalDistance = totalDistance + arrayList.get(i).getDistance();
            totalDuration = totalDuration + arrayList.get(i).getTime();
        }
        totalPace = calculatePace((int) totalDistance, totalDuration);
        if (totalPace < 10) {
            return "0" + new BigDecimal(totalPace).setScale(2, BigDecimal.ROUND_FLOOR).toString();
        } else {
            return new BigDecimal(totalPace).setScale(2, BigDecimal.ROUND_FLOOR).toString();
        }
    }

    public static String convertUnixTimeToFormattedTime(long unixTime) {
        Date date = new Date(unixTime);
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_yyyy_dash_MM_dash_dd_space_HH_colon_mm_colon_ss);

        return changeDateFormat(df.format(date), DATE_FORMAT_yyyy_dash_MM_dash_dd_space_HH_colon_mm_colon_ss, "dd-MMM-yyyy, hh:mm a");
    }

    public static String changeDateFormat(String time, String fromFormat,
                                          String toFormat) {
        if (time != null && !time.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
            Date date = null;
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            SimpleDateFormat sdf1 = new SimpleDateFormat(toFormat);
            return sdf1.format(date.getTime());
        }
        return "";
    }

    public static String getDayFromDate(long unix_time) {
        String day = "";
        try {
            Date dt1 = new Date(unix_time);
            DateFormat format2 = new SimpleDateFormat(DATE_FORMAT_EEEE);
            day = format2.format(dt1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day;
    }


    public static String formatNumber(double number) {
        if (number < 10) {
            return "0" + new BigDecimal(number).setScale(2, BigDecimal.ROUND_FLOOR).toString();
        } else {
            return new BigDecimal(number).setScale(2, BigDecimal.ROUND_FLOOR).toString();
        }
    }

    public static void drawMap(Activity activity, GoogleMap mMap, RunData runData) {
        ArrayList<LatLng> list = new ArrayList<>();
        int size = runData.getLatitude().size();
        for (int i = 0; i < size; i++) {
            LatLng latLng = new LatLng(runData.getLatitude().get(i), runData.getLongitude().get(i));
            list.add(latLng);
        }
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setMaxZoomPreference(17);
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        activity, R.raw.mapstyle_retro));
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(getBound(list), (int) convertDpToPixel(10, activity));
        mMap.animateCamera(cu);
        mMap.moveCamera(cu);
        if (!list.isEmpty()) {
            mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(activity.getResources().getInteger(R.integer.strok_width_for_map))
                    .color(Color.BLUE)

            );
        }
    }

    private static LatLngBounds getBound(ArrayList<LatLng> lists) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng list : lists) {
            builder.include(list);
        }
        return builder.build();
    }

    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    private static SecretKey generateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(Constant.cipher_password.getBytes(), "AES");
    }

    private static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance(Constant.encryption_algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            UnsupportedEncodingException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance(Constant.encryption_algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }

    public static boolean writeFile( String msg) {
        try {
            SecretKey secret = generateKey();
            byte[] bytes = encryptMsg(msg, secret);
            File root = new File(Environment.getExternalStorageDirectory(), Constant.folder_name);
            if (!root.exists()) {
                if (root.mkdirs()) {
                    File gpxfile = new File(root, Constant.back_up_file);
                    try (FileOutputStream fos = new FileOutputStream(gpxfile.getAbsolutePath())) {
                        fos.write(bytes);
                    }
                }
            } else {
                File gpxfile = new File(root, Constant.back_up_file);
                try (FileOutputStream fos = new FileOutputStream(gpxfile.getAbsolutePath())) {
                    fos.write(bytes);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFile() {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard + File.separator + Constant.folder_name, Constant.back_up_file);
        try {
            SecretKey secret = generateKey();
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            return decryptMsg(bytesArray, secret);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
