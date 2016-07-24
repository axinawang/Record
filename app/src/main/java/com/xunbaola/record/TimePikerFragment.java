package com.xunbaola.record;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

public class TimePikerFragment extends DialogFragment {
    public static final String EXTRA_TIME="com.xunbaola.record.time";
    private static final String TAG ="DatePikerFragment" ;
    private Date mDate;

    public static TimePikerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_TIME,date);
        TimePikerFragment fragment=new TimePikerFragment();
        fragment.setArguments(args);
        return fragment;
    }

private void sendResult(int resultCode){
    Fragment f =getTargetFragment();
    if (f==null){
        return;
    }
    Intent i=new Intent();
    i.putExtra(EXTRA_TIME,mDate);
    f.onActivityResult(getTargetRequestCode(),resultCode,i);
}


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.i(TAG,this.toString());
        mDate= (Date) getArguments().getSerializable(EXTRA_TIME);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        //int second=calendar.get(Calendar.SECOND);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_time,null);
        TimePicker timePicker= (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentHour(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate=new GregorianCalendar(year,month,day,hourOfDay,minute).getTime();
                //更新参数，用来在旋转时保存选择的值
                getArguments().putSerializable(EXTRA_TIME,mDate);
            }
        });

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.time_picker_title)
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
