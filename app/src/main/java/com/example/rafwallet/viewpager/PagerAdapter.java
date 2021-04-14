package com.example.rafwallet.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rafwallet.fragments.ListeFragment;
import com.example.rafwallet.fragments.ProfilFragment;
import com.example.rafwallet.fragments.StanjeFragment;
import com.example.rafwallet.fragments.UnosFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 4;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    public static final int FRAGMENT_4 = 3;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case FRAGMENT_1: fragment = new StanjeFragment(); break;
            case FRAGMENT_2: fragment = new UnosFragment(); break;
            case FRAGMENT_3: fragment = new ListeFragment(); break;
            default: fragment = new ProfilFragment(); break;
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
            case FRAGMENT_1 : return "Stanje";
            case FRAGMENT_2 : return "Unos";
            case FRAGMENT_3 : return "Liste";
            default: return "Profil";
        }
    }
}
