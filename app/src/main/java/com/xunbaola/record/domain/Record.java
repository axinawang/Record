package com.xunbaola.record.domain;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Record {
    private UUID mUUID;
    private String mTitle;
    private String mDetail;
    private Date mDate;//记录产生的时间
    private boolean mSolved;//记录是否处理过
    public Record(){
        mUUID=UUID.randomUUID();
        mDate=new Date();
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
}
