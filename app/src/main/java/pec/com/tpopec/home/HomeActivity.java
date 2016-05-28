package pec.com.tpopec.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import pec.com.tpopec.R;
import pec.com.tpopec.SplashActivity;
import pec.com.tpopec.gcm.RegistrationIntentService;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.general.RegistrationConstants;

public class HomeActivity extends AppCompatActivity implements NewCompaniesFragment.OnFragmentInteractionListener,
        AppliedCompaniesFragment.OnFragmentInteractionListener, AnnoucementsFragment.OnFragmentInteractionListener{

    //Declaring All The Variables Needed

    private MySharedPreferences sp;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "HomeActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = new MySharedPreferences(this);

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new CompaniesFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_announcements) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new AnnoucementsFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_companies) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new CompaniesFragment()).commit();
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        mDrawerToggle.syncState();


        Intent intent = getIntent();
        if(intent.getStringExtra(Constants.KEY_SID) != null){
            sp.setSid(intent.getStringExtra(Constants.KEY_SID));
            sp.setPassword(intent.getStringExtra(Constants.KEY_PASSWORD));
            sp.setBranch(intent.getStringExtra(Constants.KEY_BRANCH));
            sp.setStudentName(intent.getStringExtra(Constants.KEY_STUDENT_NAME));
            sp.setStudentProgramme(intent.getStringExtra(Constants.KEY_STUDENT_PROGRAMME));
        }

        if(sp.getGcmToken() == null){
            registerClient(getApplicationContext());
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String sentToken = intent.getStringExtra(RegistrationConstants.REGISTRATION_TOKEN);
                if (!sentToken.equals("")) {
                    sp.setGcmToken(intent.getStringExtra(RegistrationConstants.REGISTRATION_TOKEN));
                } else {
                    sp.setGcmToken(null);
                    Toast.makeText(HomeActivity.this, "Unable to register for push notifications.", Toast.LENGTH_LONG).show();
                }
            }
        };

        RelativeLayout drawerHeader = (RelativeLayout) mNavigationView.getHeaderView(0);
        TextView userName = (TextView) drawerHeader.findViewById(R.id.user_name);
        userName.setText(sp.getStudentName());
        TextView userSid = (TextView)drawerHeader.findViewById(R.id.user_sid);
        userSid.setText(sp.getSid());

    }

    /**
     * Calls the GCM API to register this client if not already registered.
     * @throws IOException
     */
    public void registerClient(Context context) {
        // Get the sender ID
        String senderId = context.getString(R.string.gcm_defaultSenderId);
        //String stringId = stringIdentifierField.getText().toString();
        if (!("".equals(senderId))) {

            // Register with GCM
            Intent intent = new Intent(context, RegistrationIntentService.class);
            intent.putExtra(RegistrationConstants.SENDER_ID, senderId);
            intent.putExtra(Constants.KEY_SID, sp.getSid());
            intent.putExtra(Constants.KEY_BRANCH, sp.getBranch());
            //intent.putExtra(RegistrationConstants.STRING_IDENTIFIER, stringId);
            if (checkPlayServices()) {
                startService(intent);
            }

        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.logout){
            sp.setSid(null);
            sp.setPassword(null);
            sp.setBranch(null);
            sp.setGcmToken(null);
            sp.setStudentName(null);
            sp.setStudentProgramme(null);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
