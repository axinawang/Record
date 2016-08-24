package com.xunbaola.record;


import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.xunbaola.record.camera.RecordCameraActivity;
import com.xunbaola.record.camera.RecordCameraFrament;
import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Photo;
import com.xunbaola.record.domain.Record;
import com.xunbaola.record.utils.PictureUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class RecordFragment extends Fragment {
    private static final boolean DEBUG = false;
    private static final String TAG = "RecordFragment";
    private static final String DIALOG_IMAGE1 = "image1";
    private static final String DIALOG_IMAGE2 = "image2";
    private static final String DIALOG_IMAGE3 = "image3";
    public static final String EXTRA_RECORD_ID = "com.xunbaola.record.record_uuid";
    public static final String EXTRA_RECORD_POSITION = "com.xunbaola.record.record_position";
    private static final String DIALOG_DATE_TIME = "date_time";
    //private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE_TIME = 0;
    private static final int REQUEST_PHOTO = 1;
    //private static final int REQUEST_TIME =1 ;
    private Record mRecord;//保存记录
    //private int mPosition;//记录的位置
    private EditText mTitle;//标题
    private EditText mDetail;//详情
    private CheckBox mRecordSolved;//是否处理好记录
    private Button mRecordDate;//记录日期时间
    private TitleTextWatcher mTitleTextWatcher;
    private DetailTextWatcher mDetailTextWatcher;
    private SolvedOnCheckedChangeListener mSolvedOnCheckedChangeListener;
    private ImageButton mPhoto1Button;
    private ImageButton mPhoto2Button;
    private ImageButton mPhoto3Button;
    private ImageView mPhoto1View;
    private ImageView mPhoto2View;
    private ImageView mPhoto3View;



    /**
     * 创建实例，设置参数
     *
     * @param recordId uuid
     * @return recordFragment
     */
    public static RecordFragment newInstance(UUID recordId, int position) {
        RecordFragment fragment = new RecordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECORD_ID, recordId);
        bundle.putInt(EXTRA_RECORD_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     *
     */
    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mRecordDate.setText(sdf.format(mRecord.getDate()));
    }
    /**
     * 在记录片段被创建时，获取一个记录对象
     *
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID uuid = (UUID) getArguments().getSerializable(EXTRA_RECORD_ID);
        //mPosition = getArguments().getInt(EXTRA_RECORD_POSITION);
        mRecord = RecordLab.get(getActivity()).getRecord(uuid);
        Log.d(TAG, "oncreate record'title= " + mRecord.getTitle() + "fragment= " + this);
    }


    /**
     * 在创建视图对象时，把记录布局反射成视图对象，返回给调用者
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //第三个参数是false，是因为将通过代码添加视图
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
        //getActivity().setTitle(mRecord.getTitle());
        mTitle = (EditText) view.findViewById(R.id.record_title);
        mTitleTextWatcher = new TitleTextWatcher();

        mDetail = (EditText) view.findViewById(R.id.record_detail);
        mDetailTextWatcher=new DetailTextWatcher();
        mRecordDate = (Button) view.findViewById(R.id.record_date);
        updateDate();
        // mRecordDate.setEnabled(false);
        mRecordDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeFragment fragment = DateTimeFragment.newInstance(mRecord.getDate());
                fragment.setTargetFragment(RecordFragment.this, REQUEST_DATE_TIME);
                fragment.show(getActivity().getSupportFragmentManager(), DIALOG_DATE_TIME);
            }
        });

        mRecordSolved = (CheckBox) view.findViewById(R.id.record_solved);
        mSolvedOnCheckedChangeListener=new SolvedOnCheckedChangeListener();
        mPhoto1Button= (ImageButton) view.findViewById(R.id.record1_imageButton);
        mPhoto2Button= (ImageButton) view.findViewById(R.id.record2_imageButton);
        mPhoto3Button= (ImageButton) view.findViewById(R.id.record3_imageButton);
        mPhoto1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RecordCameraActivity.class);
                i.putExtra(RecordCameraFrament.EXTRA_RECORD_PICTURE_TITLE,mTitle.getText()+"_1");
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });
        mPhoto2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RecordCameraActivity.class);
                i.putExtra(RecordCameraFrament.EXTRA_RECORD_PICTURE_TITLE,mTitle.getText()+"_2");
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });
        mPhoto3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), RecordCameraActivity.class);
                i.putExtra(RecordCameraFrament.EXTRA_RECORD_PICTURE_TITLE,mTitle.getText()+"_3");
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });
        mPhoto1View= (ImageView) view.findViewById(R.id.record_photo1_imageView);
        mPhoto2View= (ImageView) view.findViewById(R.id.record_photo2_imageView);
        mPhoto3View= (ImageView) view.findViewById(R.id.record_photo3_imageView);
        mPhoto1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p=mRecord.getPhoto1();
                if (p==null) return;
                android.support.v4.app.FragmentManager fm=getActivity().getSupportFragmentManager();
                String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path,p.getDegree()).show(fm,DIALOG_IMAGE1);
            }
        });mPhoto2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p=mRecord.getPhoto2();
                if (p==null) return;
                android.support.v4.app.FragmentManager fm=getActivity().getSupportFragmentManager();
                String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path,p.getDegree()).show(fm,DIALOG_IMAGE2);
            }
        });mPhoto3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p=mRecord.getPhoto3();
                if (p==null) return;
                android.support.v4.app.FragmentManager fm=getActivity().getSupportFragmentManager();
                String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path,p.getDegree()).show(fm,DIALOG_IMAGE3);
            }
        });
//假如没有照相机，禁用拍照功能
        PackageManager pm=getActivity().getPackageManager();
        boolean hasCamera=pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)||
                Build.VERSION.SDK_INT<Build.VERSION_CODES.GINGERBREAD;
        if (!hasCamera){
            mPhoto1Button.setEnabled(false);
            mPhoto2Button.setEnabled(false);
            mPhoto2Button.setEnabled(false);
        }
        registerForContextMenu(mPhoto1View);
        registerForContextMenu(mPhoto2View);
        registerForContextMenu(mPhoto3View);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.record_photo_item_context,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_delete_photo1 :
                if (mRecord.getPhoto1() != null) {
                    mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto1().getFilename()).getAbsolutePath(),1);
                    showPhoto(mRecord.getPhoto1(),mPhoto1View);
                    Log.d(TAG," R.id.menu_item_delete_photo1");
                    return true;
                }
            case R.id.menu_item_delete_photo2 :
                if (mRecord.getPhoto2() != null) {
                    mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto2().getFilename()).getAbsolutePath(),2);
                    showPhoto(mRecord.getPhoto2(),mPhoto2View);
                    Log.d(TAG," R.id.menu_item_delete_photo2");
                    return true;
                }
            case R.id.menu_item_delete_photo3 :
                if (mRecord.getPhoto3() != null) {
                    mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto3().getFilename()).getAbsolutePath(),3);
                    showPhoto(mRecord.getPhoto3(),mPhoto3View);
                    Log.d(TAG," R.id.menu_item_delete_photo3");
                    return true;
                }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_record, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE_TIME) {
            Date date = (Date) data.getSerializableExtra(DateTimeFragment.EXTRA_DATE_TIME);
            mRecord.setDate(date);
            updateDate();
        }else if (requestCode==REQUEST_PHOTO){
            String filename=data.getStringExtra(RecordCameraFrament.EXTRA_PHOTO_FILENAME);
            int degree=data.getIntExtra(RecordCameraFrament.EXTRA_PHOTO_DEGREE,0);
            if (filename!=null){
               char endChar= filename.charAt(filename.length()-5);
                Photo p=new Photo(filename,degree);
                switch (endChar)
                {
                    case '1':
                        if (mRecord.getPhoto1() != null) {
                            mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto1().getFilename()).getAbsolutePath(),1);
                        }
                        mRecord.setPhoto1(p);showPhoto(p,mPhoto1View);break;
                    case '2':
                        if (mRecord.getPhoto2() != null) {
                            mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto2().getFilename()).getAbsolutePath(),2);
                        }
                        mRecord.setPhoto2(p);showPhoto(p,mPhoto2View);break;
                    case '3':
                        if (mRecord.getPhoto3() != null) {
                            mRecord.deletePhoto(getActivity().getFileStreamPath(mRecord.getPhoto3().getFilename()).getAbsolutePath(),3);
                        }
                        mRecord.setPhoto3(p);showPhoto(p,mPhoto3View);break;
                    default:Log.e(TAG,"错误：照片文件名不是以1、2、3结尾");

                }
                Log.i(TAG,"record: "+mRecord.getTitle()+"has a photo:"+filename);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "向上按钮得到响应");
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_delete_record:
                RecordLab recordLab = RecordLab.get(getActivity());
                recordLab.deleteRecord(mRecord);
                //mRecord = null;
                updateActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto(mRecord.getPhoto1(),mPhoto1View);
        showPhoto(mRecord.getPhoto2(),mPhoto2View);
        showPhoto(mRecord.getPhoto3(),mPhoto3View);
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhoto1View);
        PictureUtils.cleanImageView(mPhoto2View);
        PictureUtils.cleanImageView(mPhoto3View);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        saveRecords();
    }


    @Override
    public void onResume() {
        super.onResume();
        mTitle.setText(mRecord.getTitle());
        mDetail.setText(mRecord.getDetail());
        mRecordSolved.setChecked(mRecord.isSolved());

        mTitle.addTextChangedListener(mTitleTextWatcher);
        mDetail.addTextChangedListener(mDetailTextWatcher);
        mRecordSolved.setOnCheckedChangeListener(mSolvedOnCheckedChangeListener);
    }
    private void saveRecords() {
        if (RecordLab.get(getActivity()).saveRecords()) {
            Toast.makeText(getActivity(), getText(R.string.data_save_success).toString() + new Random().nextInt(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), getText(R.string.data_save_fail).toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void showPhoto(Photo p,ImageView photoView){
        BitmapDrawable b=null;
        if (p != null) {
            String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            b= PictureUtils.getScaledDrawable(getActivity(),path);
            int degree=p.getDegree();
            if (degree==90) {
                b=new BitmapDrawable(getActivity().getResources(),PictureUtils.rotateBitmap(b.getBitmap(),degree));
            }

        }
        photoView.setImageDrawable(b);
    }
    private void updateActivity() {
        RecordPagerActivity activity = (RecordPagerActivity) getActivity();

        ViewPager pager =activity.getViewPager();
        FragmentStatePagerAdapter adapter = ((FragmentStatePagerAdapter) pager.getAdapter());
        int count = pager.getAdapter().getCount();
        Log.d(TAG, "count:" + count);
        if (count == 0) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                NavUtils.navigateUpFromSameTask(getActivity());
                return;
            }
        }
        adapter.notifyDataSetChanged();
    }
    private class TitleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged----->s= " + s + " fragment =" + RecordFragment.this);
            mRecord.setTitle(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private class DetailTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged----->s= " + s + " fragment =" + RecordFragment.this);
            mRecord.setDetail(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private class SolvedOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mRecord.setSolved(isChecked);
        }
    }
}
