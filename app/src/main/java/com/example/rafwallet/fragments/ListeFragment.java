package com.example.rafwallet.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.rafwallet.R;
import com.example.rafwallet.viewpager.PagerAdapterStanja;
import com.google.android.material.tabs.TabLayout;

public class ListeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public ListeFragment(){
        super(R.layout.fragment_liste);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);

        viewPager.setAdapter(new PagerAdapterStanja(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
