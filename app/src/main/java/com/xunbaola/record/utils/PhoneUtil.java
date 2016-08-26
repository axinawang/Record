package com.xunbaola.record.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/25.
 */
public class PhoneUtil {
    public synchronized static ArrayList<String> getPhoneByName(Context context, String name) {
        ArrayList<String> numbers = new ArrayList<String>();
        String number=null;
        ContentResolver resolver = context.getContentResolver();
        String[] projection=new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?",
                new String[]{name}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                 number= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (number!=null&&number!="") {
                    numbers.add(number);
                }
            }
        }
        return numbers;
    }
}
