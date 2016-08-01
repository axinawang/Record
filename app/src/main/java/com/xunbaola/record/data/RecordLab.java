package com.xunbaola.record.data;

import android.content.Context;
import android.util.Log;

import com.xunbaola.record.R;
import com.xunbaola.record.domain.Record;
import com.xunbaola.record.utils.RecordJsonSerializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/13.
 */
public class RecordLab {
    private static final String TAG = "RecordLab";
    private static RecordLab sRecordLab;//数据中心
    private Context mAppContext;//应用环境
    private ArrayList<Record> mRecords;//记录数组
    private String mFileName;
    private RecordJsonSerializer mSerializer;
    private RecordLab(Context context){
        mAppContext=context;
       mFileName= mAppContext.getResources().getText(R.string.data_file_name).toString();
        Log.d(TAG,"data filename:"+mFileName);
       

        mSerializer=new RecordJsonSerializer(mAppContext,mFileName);
        try {
            mRecords=mSerializer.loadRecords();
        } catch (Exception e)  {
            e.printStackTrace();
            mRecords=new ArrayList<Record>();
            Log.e(TAG,"Error loading records: " ,e);
        }
        /*for (int i=0;i<100;i++){
            Record r=new Record();
            r.setTitle("Record #"+i);
            r.setSolved(i%2==0);
            mRecords.add(r);
        }*/
    }

    /**
     * 获取单例RecordLab
     * @param context 上下文环境
     * @return
     */
    public static RecordLab get(Context context){
        if (sRecordLab==null)
        {
            //applicationcontext具有全局性，而单例也是全局性的，互相匹配
            sRecordLab=new RecordLab(context.getApplicationContext());
        }
        return sRecordLab;
    }
    public ArrayList<Record> getRecords(){
        return mRecords;
    }
    public Record getRecord(UUID uuid){
        for (Record r:mRecords) {
            if (r.getUUID().equals(uuid)){return r;}
        }
        return null;
    }
    public void addRecord(Record record){
        mRecords.add(record);
    }
    public boolean saveRecords(){
        try {
            mSerializer.saveRecords(mRecords);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
