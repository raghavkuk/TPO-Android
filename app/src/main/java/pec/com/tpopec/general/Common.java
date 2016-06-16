package pec.com.tpopec.general;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by Raghav on 10-05-2016.
 */
public class Common {

    public static JSONObject stringToJsonobj(String result)
    {
        JSONObject jObj = null;
        try {

            jObj = new JSONObject(result);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }

        return jObj;
    }

    public static boolean isNetworkConnectionAvailable(Context context){

        boolean mobileDataEnabled = false; // Assume disabled
        boolean wifiConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);

            if(wifi.isConnected())
                wifiConnected = true;

            return mobileDataEnabled||wifiConnected;

        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return false;
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
