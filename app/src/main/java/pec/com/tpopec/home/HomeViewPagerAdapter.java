package pec.com.tpopec.home;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Raghav on 08-05-2016.
 */
public class HomeViewPagerAdapter extends FragmentStatePagerAdapter{

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NewCompaniesFragment.newInstance();
            case 1:
                return AnnoucementsFragment.newInstance();
            case 2:
                return AppliedCompaniesFragment.newInstance();
            default:
                return NewCompaniesFragment.newInstance();
        }
    }


    @Override
    public int getCount() {
        return 3;           // As there are only 3 Tabs
    }

}
