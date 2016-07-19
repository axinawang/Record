package com.xunbaola.record;





import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 只能容纳单个fragment的抽象Activity
 * 复用之前创建的fragment
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG =SingleFragmentActivity.class.getName() ;
    protected abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       Log.i(TAG,this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm=getSupportFragmentManager();
        Log.i(TAG,"fm   :"+fm);
        Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
        if (fragment==null){
            fragment=createFragment();
            Log.i(TAG,"new frament before commit:"+fragment);
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
            Log.i(TAG,"new frament after commit:"+fragment);
        }else {Log.i(TAG,"existed frament:"+fragment);}
    }
}
