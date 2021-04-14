package com.example.rafwallet.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rafwallet.fragments.PrihodiFragment;
import com.example.rafwallet.fragments.RashodiFragment;
import com.example.rafwallet.fragments.StanjeFragment;

public class PagerAdapterStanja extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 2;
    public static final int FRAGMENT_1 = 0;

    public PagerAdapterStanja(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case FRAGMENT_1: fragment = new PrihodiFragment(); break;
            default: fragment = new RashodiFragment(); break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case FRAGMENT_1 : return "Prihodi";
            default: return "Rashodi";
        }
    }
}
