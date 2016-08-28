package com.xunbaola.record;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xunbaola.record.domain.Record;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RecordListActivity extends SingleFragmentActivity
        implements RecordListFragment.Callbacks ,RecordFragment.Callbacks{
    private static final String TAG = "RecordListActivity";




    @Override
    protected Fragment createFragment() {

        return new RecordListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onRecordSelected(Record record) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            Intent intent=new Intent(this,RecordPagerActivity.class);
            intent.putExtra(RecordFragment.EXTRA_RECORD_ID,record.getUUID());
            startActivity(intent);
        }else {
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            Fragment oldDetail=fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail=RecordFragment.newInstance(record.getUUID());
            if (oldDetail != null) {
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer,newDetail).commit();
        }
    }

    @Override
    public void onRecordUpdated(Record record) {
        FragmentManager fm=getSupportFragmentManager();
        RecordListFragment fragment= (RecordListFragment) fm.findFragmentById(R.id.fragmentContainer);
        fragment.updateUI();
    }
}
