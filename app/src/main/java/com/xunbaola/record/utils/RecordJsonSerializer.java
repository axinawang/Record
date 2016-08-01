package com.xunbaola.record.utils;

import android.content.Context;

import com.xunbaola.record.domain.Record;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/1.
 */
public class RecordJsonSerializer {
    private Context mContext;
    private String mFileName;
    public RecordJsonSerializer(Context context,String fileName){
        mContext=context;
        mFileName=fileName;
    }
    public void saveRecords(ArrayList<Record> records)throws JSONException,IOException{
        JSONArray array=new JSONArray();
        for (Record record:records){
            array.put(record.toJson());
        }
        Writer writer=null;
        try {
            OutputStream out=mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
           if (writer!=null){
               writer.close();
           }
        }
    }
    public ArrayList<Record> loadRecords() throws IOException, JSONException {
        ArrayList<Record> records=new ArrayList<Record>();
        BufferedReader reader=null;
        try {
            InputStream in=mContext.openFileInput(mFileName);
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line=null;
            while ((line=reader.readLine())!=null){
                //Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            //parse the JSON using JSONTokener
            JSONArray array=(JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i=0;i<array.length();i++){
                Record record=new Record(array.getJSONObject(i));
                records.add(record);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null) reader.close();
        }
        return records;
    }
}
