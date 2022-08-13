package com.example.superchen;


import com.example.superchen.utils.DateUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class DateTest {
    public static void main(String[] args) {

        System.out.println(RandomStringUtils.randomNumeric(2));
        System.out.println(DateUtils.getDate("yyyy-MM-dd HH:mm:ss HH:mm:ss"));
        String address = "中国|浙江省|杭州市|移动";
        String str = address.substring(7,10);
        System.out.println(str);

    }


}
