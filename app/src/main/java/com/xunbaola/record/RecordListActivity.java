package com.xunbaola.record;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RecordListActivity extends SingleFragmentActivity {
    private static final String TAG = "RecordListActivity";




    @Override
    protected Fragment createFragment() {

        return new RecordListFragment();
    }
}
