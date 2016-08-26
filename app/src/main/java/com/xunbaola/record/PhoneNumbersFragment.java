package com.xunbaola.record;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/19.
 */
public class PhoneNumbersFragment extends DialogFragment {
    public static final String EXTRA_PHONE_NUMBERS="com.xunbaola.record.phone_numbers";
    private static final String TAG ="PhoneNumbersFragment" ;
    private ListView mListView;
    public static PhoneNumbersFragment newInstance(ArrayList<String> nums){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_PHONE_NUMBERS,nums);
        PhoneNumbersFragment fragment=new PhoneNumbersFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListView=new ListView(getActivity());
        final ArrayList<String> nums= (ArrayList<String>) getArguments().getSerializable(EXTRA_PHONE_NUMBERS);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return nums.size();
            }

            @Override
            public Object getItem(int position) {
                return nums.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=getActivity().getLayoutInflater().inflate(R.layout.list_item_phone_numbers,parent,false);
                TextView item= (TextView) view.findViewById(R.id.list_item_phone_numbers);
                item.setText(nums.get(position));
                return view;
            }
        });
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.d(TAG,"onItemClick");
               Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+nums.get(position)));
               startActivity(i);

           }
       });
        return mListView;
    }
}

