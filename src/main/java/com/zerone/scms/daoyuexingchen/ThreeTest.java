package com.zerone.scms.daoyuexingchen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreeTest {
    public static void main(String[] args) {
        String content = "work 1-(800) 555.1212 #1234";
        String content2 = "1-800-555-1212";
        String pattern = "(8{1})(0{2})(\\D*)(5{3})(\\D*)(((1212)(\\D*)(1234)|(1212)))";
        Re re = new Re();
        System.out.println(re.regReplace(content, pattern));
        System.out.println(re.regReplace(content2, pattern));
    }

}

class Re {
    /**
     * @param content 字符串
     * @param pattern 正则表达式
     * @return 返回匹配的字符串
     */
    String regReplace(String content, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        String result = null;
        if (m.find()) {
            result = m.group();
        }
        return result;
    }
}
