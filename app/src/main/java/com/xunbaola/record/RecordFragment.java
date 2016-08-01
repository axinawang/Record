package com.xunbaola.record;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.UUID;


public class RecordFragment extends Fragment {
    private static final String TAG=RecordFragment.class.getName();
    public static final String EXTRA_RECORD_ID="com.xunbaola.record.record_uuid";
    private static final String DIALOG_DATE_TIME = "date_time";
    //private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE_TIME = 0;
    //private static final int REQUEST_TIME =1 ;
    private Record mRecord;//保存记录
    private EditText mTitle;//标题
    private EditText mDetail;//详情
    private CheckBox mRecordSolved;//是否处理好记录
    private Button mRecordDate;//记录时间

    /**
     * 创建实例，设置参数
     * @param recordId uuid
     * @return recordFragment
     */
    public static RecordFragment newInstance(UUID recordId){
        RecordFragment fragment=new RecordFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(EXTRA_RECORD_ID,recordId);
        fragment.setArguments(bundle);
        return  fragment;
    }

    /**
     *
     */
    public void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        mRecordDate.setText(sdf.format(mRecord.getDate()));
       // mRecordTime.setText(mRecord.getDate().toString());
    }
    @Override
    public void onAttach(Context context) {
        Log.i(TAG,"onAttach");
        super.onAttach(context);

    }
    /**
     * 在记录片段被创建时，获取一个记录对象
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID uuid= (UUID) getArguments().getSerializable(EXTRA_RECORD_ID);

        mRecord = RecordLab.get(getActivity()).getRecord(uuid);
    }

    /**
     * 在创建视图对象时，把记录布局反射成视图对象，返回给调用者
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        //第三个参数是false，是因为将通过代码添加视图
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            if (NavUtils.getParentActivityName(getActivity())!=null){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
        mTitle = (EditText) v.findViewById(R.id.record_title);
        mTitle.setText(mRecord.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDetail = (EditText) v.findViewById(R.id.record_detail);
        mDetail.setText(mRecord.getDetail());
        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecord.setDetail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRecordDate = (Button) v.findViewById(R.id.record_date);
       updateDate();
       // mRecordDate.setEnabled(false);
        mRecordDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeFragment fragment=DateTimeFragment.newInstance(mRecord.getDate());
                fragment.setTargetFragment(RecordFragment.this,REQUEST_DATE_TIME);
                fragment.show(getActivity().getSupportFragmentManager(),DIALOG_DATE_TIME);
            }
        });

        mRecordSolved= (CheckBox) v.findViewById(R.id.record_solved);
        mRecordSolved.setChecked(mRecord.isSolved() );
        mRecordSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override//不需要注解
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecord.setSolved(isChecked);
            }
        });






        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK) return;
        if (requestCode==REQUEST_DATE_TIME){
            Date date= (Date) data.getSerializableExtra(DateTimeFragment.EXTRA_DATE_TIME);
            mRecord.setDate(date);
            updateDate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.d(TAG,"向上按钮得到响应");
                if (NavUtils.getParentActivityName(getActivity())!=null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG,"onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG,"onPause");
        super.onPause();
        if (RecordLab.get(getActivity()).saveRecords()){
            Toast.makeText(getActivity(),getText(R.string.data_save_success).toString(),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity(),getText(R.string.data_save_fail).toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop() {
        Log.i(TAG,"onStop");
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        Log.i(TAG,"onDestroyView");
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        Log.i(TAG,"onDetach");
        super.onDetach();

    }
}
