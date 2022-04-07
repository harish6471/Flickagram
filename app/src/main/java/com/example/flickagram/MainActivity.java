package com.example.flickagram;

import android.os.Bundle;

import com.example.flickagram.Adapter.ViewPagerAdapter;
import com.example.flickagram.Fragment.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flickagram.Network.PhotosApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private Fragment homeFragment;

    private ViewPagerAdapter pagerAdapter;
    private ViewPager2 pagerHome;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerHome = findViewById(R.id.pagerHome);
        frameLayout = findViewById(R.id.frame);


        setSupportActionBar(findViewById(R.id.toolbar));

            pagerAdapter = new ViewPagerAdapter(this);
        pagerAdapter.addFragment(new HomeFragment(() -> {
            frameLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.groupViewPager).setVisibility(View.GONE);
        }));


        pagerHome.setAdapter(pagerAdapter);

    }




   



    @Override
    public void onBackPressed() {
        frameLayout.setVisibility(View.GONE);
        findViewById(R.id.groupViewPager).setVisibility(View.VISIBLE);
        super.onBackPressed();
    }


}

