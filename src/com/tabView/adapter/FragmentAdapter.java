package com.tabView.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.tabview.mlapp.fragment.AboutUsFragment;
import com.tabview.mlapp.fragment.BiographyFragment;
import com.tabview.mlapp.fragment.ContactFragment;
import com.tabview.mlapp.fragment.NewsFragment;

/**
 * Created by vageesh on 3/10/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new NewsFragment();
            case 1:

                return new AboutUsFragment();

            case 2:

                return new BiographyFragment();
            case 3 :
                return new ContactFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}
