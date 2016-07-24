package com.xunbaola.record;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/24.
 */
public class DateTimeFragment extends DialogFragment {
    public static final String EXTRA_DATE_TIME="com.xunbaola.record.datetime";
    private Button mRecordDate;//记录日期
    private Button mRecordTime;//记录时间
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME =1 ;
    private Date mDate;//包括时间日期
    public static DateTimeFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_DATE_TIME,date);
        DateTimeFragment fragment=new DateTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mRecordDate.setText(sdf.format(mDate));
        sdf = new SimpleDateFormat("HH:mm");
        mRecordTime.setText(sdf.format(mDate));
    }
    private void sendResult(int resultCode){
        Fragment f =getTargetFragment();
        if (f==null){
            return;
        }
        Intent i=new Intent();
        i.putExtra(EXTRA_DATE_TIME,mDate);
        f.onActivityResult(getTargetRequestCode(),resultCode,i);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate= (Date) getArguments().getSerializable(EXTRA_DATE_TIME);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date_time,null);
        mRecordDate = (Button) v.findViewById(R.id.record_date);
        // updateDate();
        // mRecordDate.setEnabled(false);
        mRecordDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePikerFragment fragment=DatePikerFragment.newInstance(mDate);
                fragment.setTargetFragment(DateTimeFragment.this,REQUEST_DATE);
                fragment.show(getActivity().getSupportFragmentManager(),DIALOG_DATE);
            }
        });
        //设置时间
        mRecordTime = (Button) v.findViewById(R.id.record_time);
        updateDate();//更新时间和日期
        // mRecordDate.setEnabled(false);
        mRecordTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePikerFragment fragment=TimePikerFragment.newInstance(mDate);
                fragment.setTargetFragment(DateTimeFragment.this,REQUEST_TIME);
                fragment.show(getActivity().getSupportFragmentManager(),DIALOG_TIME);
            }
        });

        return  new AlertDialog.Builder(getActivity()).setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK) return;
        if (requestCode==REQUEST_DATE){
            Date date= (Date) data.getSerializableExtra(DatePikerFragment.EXTRA_DATE);
            mDate=date;
            updateDate();
        }else if (requestCode==REQUEST_TIME){
            Date date= (Date) data.getSerializableExtra(TimePikerFragment.EXTRA_TIME);
           mDate=date;
            updateDate();
        }
    }
}
