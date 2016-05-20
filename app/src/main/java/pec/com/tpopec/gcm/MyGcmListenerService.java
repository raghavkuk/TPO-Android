package pec.com.tpopec.gcm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import pec.com.tpopec.R;
import pec.com.tpopec.general.RegistrationConstants;

/**
 * Created by Raghav on 14-05-2016.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        Log.d(TAG, data.toString());

        String message = data.getString("message");
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.pec_logo)
                .setContentTitle("TPO PEC")
                .setContentText(message);
        notificationManager.notify(1, mBuilder.build());


        Intent downstreamMessageIntent = new Intent(RegistrationConstants.NEW_DOWNSTREAM_MESSAGE);
        downstreamMessageIntent.putExtra(RegistrationConstants.SENDER_ID, from);
        downstreamMessageIntent.putExtra(RegistrationConstants.EXTRA_KEY_BUNDLE, data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(downstreamMessageIntent);
    }
}
