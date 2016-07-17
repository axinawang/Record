package com.xunbaola.record;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xunbaola.record.data.RecordLab;
import com.xunbaola.record.domain.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RecordListFragment extends ListFragment {
    private static final String TAG = RecordListFragment.class.getName();
    private ArrayList<Record> mRecords;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.records_title);
        mRecords= RecordLab.get(this.getActivity()).getRecords();
        //创建一个数组适配器，供listfragment内置的listview用
        RecordAdapter adapter=new RecordAdapter(mRecords);
        setListAdapter(adapter);
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
        Intent i=new Intent(getActivity(),RecordActivity.class);
        i.putExtra(RecordFragment.EXTRA_RECORD_ID,r.getUUID());
        startActivity(i);
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
            dateTextView.setText(r.getDate().toString());
            solvedCheckBox.setChecked(r.isSolved());
            return convertView;
        }
    }
}
