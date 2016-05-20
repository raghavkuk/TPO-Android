package pec.com.tpopec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.home.HomeActivity;

/**
 * Created by Raghav on 09-05-2016.
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_TIME_OUT = 1000;
    private MySharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = new MySharedPreferences(this);

        if (sp.getSid() != null && sp.getPassword() != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
