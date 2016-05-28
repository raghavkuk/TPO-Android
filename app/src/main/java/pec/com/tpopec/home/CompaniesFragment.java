package pec.com.tpopec.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pec.com.tpopec.R;

/**
 * Created by Raghav on 23-05-2016.
 */
public class CompaniesFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeViewPagerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.companies_fragment, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPagerAdapter = new HomeViewPagerAdapter(getFragmentManager());

        /**
         *Set an Apater for the View Pager
         */
        //tabLayout.setTabTextColors(R.color.grey, R.color.white);
        viewPager.setAdapter(viewPagerAdapter);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

}
