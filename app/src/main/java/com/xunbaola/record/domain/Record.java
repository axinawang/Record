package com.xunbaola.record.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Record {
    private static final String JSON_UUID="uuid";
    public static final String JSON_UUID1 = JSON_UUID;
    private static final String JSON_TITLE="title";
    private static final String JSON_DETAIL="detail";
    private static final String JSON_DATE="date";
    private static final String JSON_SOLVED="solved";

    private UUID mUUID;
    private String mTitle;
    private String mDetail;
    private Date mDate;//记录产生的时间
    private boolean mSolved;//记录是否处理过
    public Record(){
        mUUID=UUID.randomUUID();
        mDate=new Date();
    }
    public Record(JSONObject json) throws JSONException {
        mUUID=UUID.fromString(json.getString(JSON_UUID));
        if (json.has(JSON_TITLE)) mTitle=json.getString(JSON_TITLE);
        if (json.has(JSON_DETAIL)) mDetail=json.getString(JSON_DETAIL);
        mDate=new Date(json.getLong(JSON_DATE));
        mSolved=json.getBoolean(JSON_SOLVED);
    }

    /**
     * 获取唯一标识符
     * @return UUID
     */
    public UUID getUUID() {
        return mUUID;
    }

    /**
     * 获取标题
     * @return 标题
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * 获取详情
     * @return 详情
     */
    public String getDetail() {
        return mDetail;
    }

    /**
     * 设置详情
     * @param detail 详情
     */
    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json=new JSONObject();
        json.put(JSON_UUID,mUUID);

        json.put(JSON_TITLE,mTitle);
        json.put(JSON_DETAIL,mDetail);
        json.put(JSON_DATE,mDate.getTime());
        json.put(JSON_SOLVED,mSolved);
        return json;
    }
}
