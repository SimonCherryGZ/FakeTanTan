package com.simoncherry.faketantan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simon on 2016/10/16.
 */

public class RegUtil {

    public static boolean PhoneNumberCheck(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
