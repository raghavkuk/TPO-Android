package pec.com.tpopec.general;

import android.widget.ProgressBar;

/**
 * Created by Raghav on 14-05-2016.
 */
public class GcmUtil {

    static ProgressBar progressBar;

    public static String getServerUrl(String senderId) {
        return senderId + "@gcm.googleapis.com";
    }
}
