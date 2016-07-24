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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePikerFragment extends DialogFragment {
    public static final String EXTRA_DATE="com.xunbaola.record.date";
    private static final String TAG ="DatePikerFragment" ;
    private Date mDate;

    public static DatePikerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        DatePikerFragment fragment=new DatePikerFragment();
        fragment.setArguments(args);
        return fragment;
    }

private void sendResult(int resultCode){
    Fragment f =getTargetFragment();
    if (f==null){
        return;
    }
    Intent i=new Intent();
    i.putExtra(EXTRA_DATE,mDate);
    f.onActivityResult(getTargetRequestCode(),resultCode,i);
}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.i(TAG,this.toString());
        mDate= (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
       final int year=calendar.get(Calendar.YEAR);
       final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final int hour=calendar.get(Calendar.HOUR_OF_DAY);
        final int minute=calendar.get(Calendar.MINUTE);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        DatePicker datePicker= (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate=new GregorianCalendar(year,monthOfYear,dayOfMonth,hour,minute).getTime();
               //更新参数，用来在旋转时保存选择的值
                getArguments().putSerializable(EXTRA_DATE,mDate);
            }
        });


        return new AlertDialog.Builder(getActivity()).setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
