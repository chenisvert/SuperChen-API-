package com.example.superchen.utils;

public class TextUtil {


   public String getChinese(String source) {

        char[] chars = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            int length = String.valueOf(aChar).getBytes().length;
            System.out.println("char:" + aChar + ";" + "length:" + length);
            if (length == 3) {
                stringBuilder.append(aChar);
            }
        }

        return stringBuilder.toString();
    }

    //通过ASCII表完成字符的匹配
    static String getAlphabet(String source) {
        char[] chars = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar > 127 || aChar < 0) {
                stringBuilder.append(aChar);
            }
        }
        return stringBuilder.toString();
    }
}
