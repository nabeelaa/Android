package edu.gatech.seclass.sdpscramble.design;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Home", "Profile", "Player Statistics", "Scramble Statistics"};

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new ProfileFragment();
        } else if (position == 2) {
            return new PlayerStatisticsFragment();
        } else {
            return new ScrambleStatisticsFragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}


