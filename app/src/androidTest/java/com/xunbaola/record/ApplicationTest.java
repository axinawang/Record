package com.xunbaola.record;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.xunbaola.record.utils.PhoneUtil;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void testPhoneUtil(){
        System.out.println("testPhoneUtil");
        ArrayList<String> nums=PhoneUtil.getPhoneByName(getContext(),"1890");
        for (String t:nums) {
            System.out.println(t);
        }
    }
    public void phoneUtilTest(){
        System.out.println("phoneUtilTest");
        ArrayList<String> nums=PhoneUtil.getPhoneByName(getContext(),"1890");
        for (String t:nums) {
            System.out.println(t);
        }
    }
}