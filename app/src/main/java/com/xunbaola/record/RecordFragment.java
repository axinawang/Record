package com.xunbaola.record;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;

import java.util.UUID;


public class RecordFragment extends Fragment {
    private static final String TAG=RecordFragment.class.getName();
    public static final String EXTRA_RECORD_ID="com.xunbaola.record.record_uuid";
    private Record mRecord;//保存记录
    private EditText mTitle;//标题
    private EditText mDetail;//详情
    private CheckBox mRecordSolved;//是否处理好记录
    private Button mRecordDate;//记录时间

    @Override
    public void onAttach(Context context) {
        Log.i(TAG,"onAttach");
        super.onAttach(context);

    }
    /**
     * 在记录片段被创建时，创建一个记录对象
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        UUID uuid= (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_RECORD_ID);

        mRecord = RecordLab.get(getActivity()).getRecord(uuid);
    }

    /**
     * 在创建视图对象时，把记录布局反射成视图对象，返回给调用者
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        //第三个参数是false，是因为将通过代码添加视图
        View v = inflater.inflate(R.layout.fragment_record, container, false);

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
        mRecordDate.setText(mRecord.getDate().toString());
        mRecordDate.setEnabled(false);
        mRecordSolved= (CheckBox) v.findViewById(R.id.record_solved);
        mRecordSolved.setChecked(mRecord.isSolved());
        mRecordSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override//不需要注解
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecord.setSolved(isChecked);
            }
        });






        return v;
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
