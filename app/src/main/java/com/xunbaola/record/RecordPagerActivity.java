package com.xunbaola.record;


import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;

import java.util.ArrayList;
import java.util.UUID;

public class RecordPagerActivity extends AppCompatActivity{
    public static final String TAG="RecordPagerActivity";
    private ViewPager mViewPager;
    private MyAdapter mMyAdapter;
    private ArrayList<Record> mRecords;
    private FragmentManager mFragmentManager;

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mRecords= RecordLab.get(this).getRecords();
        mFragmentManager=getSupportFragmentManager();
        mMyAdapter=new MyAdapter(mFragmentManager);
        mViewPager.setAdapter(mMyAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG,"onPageScrolled---->position= "+position+" positionOffset="+positionOffset+" positionOffsetPixels="+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"onPageSelected(int position)---->position= "+position);
                Record record=mRecords.get(position);
                if (record.getTitle()!=null){
                    setTitle(record.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG,"onPageScrollStateChanged(int state)---->state= "+state);
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

    private class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
            public int getCount() {
                Log.d(TAG,"count :"+mRecords.size());

                return mRecords.size();
            }

        @Override
            public RecordFragment getItem(int position) {
                Record record=mRecords.get(position);
                RecordFragment fragment=RecordFragment.newInstance(record.getUUID(),position);
                return fragment;
            }

            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
    }

}
