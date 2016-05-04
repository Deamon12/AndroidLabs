package com.example.deamon.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);


        initFrags();



    }



    private void initFrags(){

        fragmentList = new ArrayList<>();

        Fragment frag = new FragmentDBLab();
        Bundle args = new Bundle();
        args.putString("title", "SQL-Lab");
        frag.setArguments(args);
        fragmentList.add(frag);

        frag = new FragmentAsyncLab();
        args = new Bundle();
        args.putString("title", "Async-Lab");
        frag.setArguments(args);
        fragmentList.add(frag);

        frag = new FragmentStylingLab();
        args = new Bundle();
        args.putString("title", "Styling-Lab");
        frag.setArguments(args);
        fragmentList.add(frag);

        frag = new FragmentVolleyLab();
        args = new Bundle();
        args.putString("title", "Volley-Lab");
        frag.setArguments(args);
        fragmentList.add(frag);

        //TODO
        frag = new FragmentMapLab();
        args = new Bundle();
        args.putString("title", "Maps-Lab");
        frag.setArguments(args);
        fragmentList.add(frag);


        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }



    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> dataSet;

        public DemoCollectionPagerAdapter(FragmentManager fm, ArrayList dataSet) {
            super(fm);
            this.dataSet = dataSet;
        }

        @Override
        public Fragment getItem(int i) {

            return dataSet.get(i);
        }

        @Override
        public int getCount() {

            return dataSet.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dataSet.get(position).getArguments().getString("title");
        }


        public float getPageWidth(int position) {
            return 1f;
        }

    }

}
