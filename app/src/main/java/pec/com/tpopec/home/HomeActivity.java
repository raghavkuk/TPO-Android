package pec.com.tpopec.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import pec.com.tpopec.R;
import pec.com.tpopec.SplashActivity;
import pec.com.tpopec.gcm.RegistrationIntentService;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.general.RegistrationConstants;

public class HomeActivity extends AppCompatActivity implements NewCompaniesFragment.OnFragmentInteractionListener,
        VisitedCompaniesFragment.OnFragmentInteractionListener, AnnoucementsFragment.OnFragmentInteractionListener{

    //Declaring All The Variables Needed

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeViewPagerAdapter viewPagerAdapter;
    private MySharedPreferences sp;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "HomeActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = new MySharedPreferences(this);

        Intent intent = getIntent();
        if(intent.getStringExtra(Constants.KEY_SID) != null){
            sp.setSid(intent.getStringExtra(Constants.KEY_SID));
            sp.setPassword(intent.getStringExtra(Constants.KEY_PASSWORD));
            sp.setBranch(intent.getStringExtra(Constants.KEY_BRANCH));
        }

        if(sp.getGcmToken() == null){
            registerClient(getApplicationContext());
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        final TabLayout.Tab newCompanies = tabLayout.newTab();
        final TabLayout.Tab visitedCompanies = tabLayout.newTab();
        //final TabLayout.Tab announcements = tabLayout.newTab();

        newCompanies.setText(getString(R.string.new_companies_tab));
        visitedCompanies.setText(getString(R.string.visited_companies_tab));
        //announcements.setText(getString(R.string.announcements_tab));

        tabLayout.addTab(newCompanies, 0);
        tabLayout.addTab(visitedCompanies, 1);
        //tabLayout.addTab(announcements, 2);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
            //intent.putExtra(RegistrationConstants.STRING_IDENTIFIER, stringId);
            startService(intent);
        }
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
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
