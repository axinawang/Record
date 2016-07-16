package com.xunbaola.record;

import android.app.Fragment;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RecordListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RecordListFragment();
    }
}
