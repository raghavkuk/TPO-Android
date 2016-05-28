package pec.com.tpopec.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import pec.com.tpopec.R;
import pec.com.tpopec.general.Common;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.general.RegistrationConstants;

/**
 * Created by Raghav on 14-05-2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private String sid, branch;
    private MySharedPreferences sp;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        sid = intent.getStringExtra(Constants.KEY_SID);
        branch = intent.getStringExtra(Constants.KEY_BRANCH);
        String token = "";

        Intent regCompleteIntent = new Intent(RegistrationConstants.REGISTRATION_COMPLETE);

        try {
            // Initially this call goes out to the network to retrieve the token, subsequent
            // calls are local.
            String string_identifier = extras.getString(RegistrationConstants.STRING_IDENTIFIER);

            token = InstanceID.getInstance(this)
                    .getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "GCM Registration Token: " + token);

            // Register token with app server.
            sendRegistrationToServer(token, string_identifier);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            regCompleteIntent.putExtra(RegistrationConstants.SENT_TOKEN_TO_SERVER, true);
            regCompleteIntent.putExtra(RegistrationConstants.REGISTRATION_TOKEN, token);
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration
            // data on a third-party server, this ensures that we'll attempt the update at a later
            // time.
            regCompleteIntent.putExtra(RegistrationConstants.SENT_TOKEN_TO_SERVER, false);
        }

        Log.d(TAG, "Sending the broadcast");
        regCompleteIntent.putExtra(RegistrationConstants.EXTRA_KEY_TOKEN, token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(regCompleteIntent);
    }

    /**
     * Register a GCM registration token with the app server
     * @param token Registration token to be registered
     * @param string_identifier A human-friendly name for the client
     * @return true if request succeeds
     * @throws IOException
     */
    private void sendRegistrationToServer(final String token, String string_identifier){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"register-gcm-token.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = Common.stringToJsonobj(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Unable to send token");
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(RegistrationConstants.REGISTRATION_TOKEN, token);
                params.put(Constants.KEY_SID, sid);
                params.put(Constants.KEY_BRANCH, branch);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

//        Bundle registration = createRegistrationBundle(token, string_identifier);
//
//        GoogleCloudMessaging.getInstance(this).send(
//                GcmUtil.getServerUrl(getString(R.string.gcm_defaultSenderId)),
//                String.valueOf(System.currentTimeMillis()), registration);
    }

    /**
     * Creates the registration bundle and fills it with user information
     * @param token Registration token to be registered
     * @param string_identifier A human-friendly name for the client
     * @return A bundle with registration data.
     */
    private Bundle createRegistrationBundle(String token, String string_identifier) {
        Bundle registration = new Bundle();

        // Create the bundle for registration with the server.
        registration.putString(RegistrationConstants.ACTION, RegistrationConstants.REGISTER_NEW_CLIENT);
        registration.putString(RegistrationConstants.REGISTRATION_TOKEN, token);
        registration.putString(RegistrationConstants.STRING_IDENTIFIER, string_identifier);
        return registration;
    }
}
