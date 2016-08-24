package com.xunbaola.record.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/18.
 */
public class Photo {
    private static final String JSON_FILENAME="filename";
    private static final String JSON_ORIENTATION="degree";
    private String mFilename;
    private int mDegree;
    public Photo(String filename){
        mFilename=filename;
    }
    public Photo(JSONObject json) throws JSONException {
        mFilename=json.getString(JSON_FILENAME);
        mDegree=json.getInt(JSON_ORIENTATION);
    }

    public Photo(String filename, int degree) {
        mFilename=filename;
        mDegree=degree;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json=new JSONObject();
        json.put(JSON_FILENAME,mFilename);
        json.put(JSON_ORIENTATION,mDegree);
        return json;
    }

    public String getFilename() {
        return mFilename;
    }

    public int getDegree() {
        return mDegree;
    }
}
