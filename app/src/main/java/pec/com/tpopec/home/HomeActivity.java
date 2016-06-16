package pec.com.tpopec.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = new MySharedPreferences(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homeViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.selector_companies);
        tabLayout.getTabAt(1).setIcon(R.drawable.selector_announcements);
        tabLayout.getTabAt(2).setIcon(R.drawable.selector_applied);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle("New Companies");
                        return;
                    case 1:
                        getSupportActionBar().setTitle("Announcements");
                        return;
                    case 2:
                        getSupportActionBar().setTitle("Applications");
                        return;
                    default:
                        getSupportActionBar().setTitle("TPO");
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

        tabLayout.getTabAt(1).select();

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
