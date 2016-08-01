package com.xunbaola.record;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;
import com.xunbaola.record.utils.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RecordListFragment extends ListFragment {
    private static final String TAG = RecordListFragment.class.getName();
    private static final int REQUEST_RECORD = 1;
    private static final int REQUEST_NEW_RECORD = 2;
    private ArrayList<Record> mRecords;
    private boolean mSubTitle;
    private Button mNewRecordBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mSubTitle=false;
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.records_title);
        mRecords= RecordLab.get(this.getActivity()).getRecords();
        //创建一个数组适配器，供listfragment内置的listview用
        RecordAdapter adapter=new RecordAdapter(mRecords);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view=super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.list_fragment_record,container,false);
        mNewRecordBtn= (Button) view.findViewById(R.id.button_add_record);
        mNewRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
            }
        });
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            if (mSubTitle){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }
        return view;
    }

    /**
     * 点击列表项，执行该方法
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Record r= ((RecordAdapter) getListAdapter()).getItem(position);
        //Log.i(TAG,r.getTitle()+"was clicked");
        Intent i=new Intent(getActivity(),RecordPagerActivity.class);
        i.putExtra(RecordFragment.EXTRA_RECORD_ID,r.getUUID());
        startActivityForResult(i,REQUEST_RECORD);
    }

    @Override
    public void onResume() {
        Log.i(TAG,mRecords.size()+"");
        super.onResume();
        ((RecordAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_record_list,menu);
       MenuItem showSubtitle= menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubTitle&&showSubtitle!=null){
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_record:
                addRecord();
                return true;
            case R.id.menu_item_show_subtitle:
                if (((AppCompatActivity)getActivity()).getSupportActionBar().getSubtitle()==null){
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
                    item.setTitle(R.string.hide_subtitle);
                    mSubTitle=true;
                }else {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);
                    item.setTitle(R.string.show_subtitle);
                    mSubTitle=false;
                }

            default:return super.onOptionsItemSelected(item);
        }

    }

    private void addRecord() {
        Record record=new Record();
        RecordLab.get(getActivity()).addRecord(record);
        Intent i=new Intent(getActivity(),RecordPagerActivity.class);
        i.putExtra(RecordFragment.EXTRA_RECORD_ID,record.getUUID());
        startActivityForResult(i,REQUEST_NEW_RECORD);
    }

    private class RecordAdapter extends ArrayAdapter<Record>{

        public RecordAdapter(ArrayList<Record> records) {
            super(getActivity(), 0, records);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_record,null);
            }
            //配置视图对象
            Record r=getItem(position);
            TextView titleTextView= (TextView) (convertView.findViewById(R.id.record_list_item_title_textView));
            TextView dateTextView= (TextView) (convertView.findViewById(R.id.record_list_item_date_textView));
            CheckBox solvedCheckBox= (CheckBox)(convertView.findViewById(R.id.record_list_item_solved_checkBox));
            titleTextView.setText(r.getTitle());

            dateTextView.setText(DateTimeUtil.getDateTime(r.getDate()));
            solvedCheckBox.setChecked(r.isSolved());
            return convertView;
        }
    }
}
