package com.xunbaola.record.camera;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.xunbaola.record.SingleFragmentActivity;

/**
 * Created by Administrator on 2016/8/11.
 */
public class RecordCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
      String pictureTitle= getIntent().getStringExtra(RecordCameraFrament.EXTRA_RECORD_PICTURE_TITLE);
        return RecordCameraFrament.newInstance(pictureTitle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }
}
