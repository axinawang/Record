package com.xunbaola.record;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class RecordActivity extends SingleFragmentActivity {

    private static final String TAG =RecordActivity.class.getName() ;

    /**
     * 创建带参数的recordFragment,参数中包装了recordId
     * @return
     */
    @Override
    protected Fragment createFragment() {
        UUID recordId= (UUID) getIntent().getSerializableExtra(RecordFragment.EXTRA_RECORD_ID);
        return RecordFragment.newInstance(recordId);
    }



    @Override
    protected void onStart() {
        Log.i(TAG,"RecordActivity onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"RecordActivity onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG,"RecordActivity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG,"RecordActivity onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG,"RecordActivity onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"RecordActivity onDestroy");
        super.onDestroy();
    }
}
