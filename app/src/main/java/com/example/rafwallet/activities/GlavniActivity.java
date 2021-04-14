package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.rafwallet.R;
import com.example.rafwallet.models.MojViewModel;
import com.example.rafwallet.models.User;
import com.example.rafwallet.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GlavniActivity extends AppCompatActivity {
    public static final String GLAVNI_KEY = "glavniKey";
    public static int brojacFajlova = 1;
    private MojViewModel mojViewModel;
    private ViewPager viewPager;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);
        System.out.println("pre");
        mojViewModel = new ViewModelProvider(this).get(MojViewModel.class);
        System.out.println("Posle");
        Intent intent = getIntent();
        if (intent.getExtras() != null){
            this.user = (User) intent.getExtras().getSerializable(GLAVNI_KEY);
            mojViewModel.setUser(user);
        }
        init();
    }

    public void init(){
        initViewPager();
        initNavigation();
    }

    private void initViewPager(){
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private void initNavigation(){
        ((BottomNavigationView)findViewById(R.id.bottomNavigation)).setOnNavigationItemSelectedListener(item ->{
            switch(item.getItemId()){ // setCurrentItem okita metodu getItem iz PageAdaptera i vraca pravi fragment
                case R.id.stanje: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.unos: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.liste: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
                case R.id.profil: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_4, false); break;
            }
            return true;
        });

    }

    public MojViewModel getMojViewModel() {
        return mojViewModel;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public User getUser() {
        return user;
    }
}