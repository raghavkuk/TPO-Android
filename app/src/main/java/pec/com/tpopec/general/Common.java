package pec.com.tpopec.general;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
}
