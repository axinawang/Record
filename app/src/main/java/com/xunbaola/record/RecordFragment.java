package com.xunbaola.record;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
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
import android.widget.Toast;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class RecordFragment extends Fragment {
    private static final boolean DEBUG = false;
    private static final String TAG = RecordFragment.class.getName();
    public static final String EXTRA_RECORD_ID = "com.xunbaola.record.record_uuid";
    public static final String EXTRA_RECORD_POSITION = "com.xunbaola.record.record_position";
    private static final String DIALOG_DATE_TIME = "date_time";
    //private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE_TIME = 0;
    //private static final int REQUEST_TIME =1 ;
    private Record mRecord;//保存记录
    //private int mPosition;//记录的位置
    private EditText mTitle;//标题
    private EditText mDetail;//详情
    private CheckBox mRecordSolved;//是否处理好记录
    private Button mRecordDate;//记录时间
    private TitleTextWatcher mTitleTextWatcher;
    private DetailTextWatcher mDetailTextWatcher;
    private SolvedOnCheckedChangeListener mSolvedOnCheckedChangeListener;
    private View mRootView;



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
        // mRecordTime.setText(mRecord.getDate().toString());
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
        mRootView = inflater.inflate(R.layout.fragment_record, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
        //getActivity().setTitle(mRecord.getTitle());
        mTitle = (EditText) mRootView.findViewById(R.id.record_title);
        mTitleTextWatcher = new TitleTextWatcher();

        mDetail = (EditText) mRootView.findViewById(R.id.record_detail);
        mDetailTextWatcher=new DetailTextWatcher();
        mRecordDate = (Button) mRootView.findViewById(R.id.record_date);
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

        mRecordSolved = (CheckBox) mRootView.findViewById(R.id.record_solved);
        mSolvedOnCheckedChangeListener=new SolvedOnCheckedChangeListener();

        return mRootView;
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
