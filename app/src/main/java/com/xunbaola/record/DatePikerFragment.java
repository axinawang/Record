package com.xunbaola.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

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



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.i(TAG,this.toString());
        mDate= (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        DatePicker datePicker= (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate=new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
               //更新参数，用来在旋转时保存选择的值
                getArguments().putSerializable(EXTRA_DATE,mDate);
            }
        });
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
