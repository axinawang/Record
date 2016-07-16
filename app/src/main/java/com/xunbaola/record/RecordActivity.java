package com.xunbaola.record;

import android.app.Fragment;
import android.util.Log;

public class RecordActivity extends SingleFragmentActivity {

    private static final String TAG =RecordActivity.class.getName() ;

    @Override
    protected Fragment createFragment() {
        return new RecordFragment();
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
