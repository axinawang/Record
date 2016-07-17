package com.xunbaola.record;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;

import java.util.ArrayList;
import java.util.UUID;

public class RecordPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Record> mRecords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mRecords= RecordLab.get(this).getRecords();
        FragmentManager fm =getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            @Override
            public int getCount() {
                return mRecords.size();
            }

            @Override
            public Fragment getItem(int position) {
                Record record=mRecords.get(position);
                return RecordFragment.newInstance(record.getUUID());
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Record record=mRecords.get(position);
                if (record.getTitle()!=null){
                    setTitle(record.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置在record listview点击后要显示的record
        UUID uuid= (UUID) getIntent().getSerializableExtra(RecordFragment.EXTRA_RECORD_ID);
        for (int i=0;i< mRecords.size();i++){
            if (mRecords.get(i).getUUID().equals(uuid)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
