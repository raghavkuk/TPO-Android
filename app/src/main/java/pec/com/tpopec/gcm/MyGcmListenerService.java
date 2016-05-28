package pec.com.tpopec.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import pec.com.tpopec.R;
import pec.com.tpopec.general.RegistrationConstants;
import pec.com.tpopec.home.HomeActivity;

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

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        long[] pattern = {500, 500};
        mBuilder.setVibrate(pattern);
        notificationManager.notify(1, mBuilder.build());


        Intent downstreamMessageIntent = new Intent(RegistrationConstants.NEW_DOWNSTREAM_MESSAGE);
        downstreamMessageIntent.putExtra(RegistrationConstants.SENDER_ID, from);
        downstreamMessageIntent.putExtra(RegistrationConstants.EXTRA_KEY_BUNDLE, data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(downstreamMessageIntent);
    }
}
