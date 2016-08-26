package com.xunbaola.record.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Record {
    private static final String JSON_UUID="uuid";
    private static final String JSON_TITLE="title";
    private static final String JSON_DETAIL="detail";
    private static final String JSON_DATE="date";
    private static final String JSON_SOLVED="solved";
    private static final String JSON_PHOTO1="photo1";
    private static final String JSON_PHOTO2="photo2";
    private static final String JSON_PHOTO3="photo3";
    private static final String JSON_LINKMAN="linkman";



    private UUID mUUID;
    private String mTitle;
    private String mDetail;
    private Date mDate;//记录产生的时间
    private boolean mSolved;//记录是否处理过
    private Photo mPhoto1;
    private Photo mPhoto2;
    private Photo mPhoto3;
    private String mLinkman;
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
        if (json.has(JSON_PHOTO1)){
            mPhoto1=new Photo(json.getJSONObject(JSON_PHOTO1));
        }
        if (json.has(JSON_PHOTO2)){
            mPhoto2=new Photo(json.getJSONObject(JSON_PHOTO2));
        }
        if (json.has(JSON_PHOTO3)){
            mPhoto3=new Photo(json.getJSONObject(JSON_PHOTO3));
        }
        if (json.has(JSON_LINKMAN)) {
            mLinkman=json.getString(JSON_LINKMAN);
        }
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

    public Photo getPhoto1() {
        return mPhoto1;
    }

    public void setPhoto1(Photo photo1) {
        mPhoto1 = photo1;
    }

    public Photo getPhoto2() {
        return mPhoto2;
    }

    public void setPhoto2(Photo photo2) {
        mPhoto2 = photo2;
    }

    public Photo getPhoto3() {
        return mPhoto3;
    }

    public void setPhoto3(Photo photo3) {
        mPhoto3 = photo3;
    }

    public String getLinkman() {
        return mLinkman;
    }

    public void setLinkman(String linkman) {
        mLinkman = linkman;
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
        if (mPhoto1 != null) {
            json.put(JSON_PHOTO1,mPhoto1.toJson());
        }if (mPhoto2 != null) {
            json.put(JSON_PHOTO2,mPhoto2.toJson());
        }if (mPhoto3 != null) {
            json.put(JSON_PHOTO3,mPhoto3.toJson());
        }
        json.put(JSON_LINKMAN,mLinkman);
        return json;
    }
    public boolean deletePhoto(String filename ,int position){
        File file=new File(filename);
        switch (position){
            case 1:setPhoto1(null);break;
            case 2:setPhoto2(null);break;
            case 3:setPhoto3(null);break;
        }
        return file.delete();
    }
}
