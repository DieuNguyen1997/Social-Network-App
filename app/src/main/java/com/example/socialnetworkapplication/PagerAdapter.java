package com.example.socialnetworkapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTap;
    public PagerAdapter(FragmentManager fm, int numberOfTab) {
        super(fm);
        this.mNoOfTap = numberOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                RequestFriendFragment requestFriendFragment = new RequestFriendFragment();
                return requestFriendFragment;
            case 2:
                EventFragment eventFragment = new EventFragment();
                return eventFragment;
            case 3:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;
            case 4:
                AccountFragment accountFragment = new AccountFragment();
                return accountFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTap;
    }
}
