package com.neo.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ratel on 2021/6/28.
 */
public class StringUtil extends StringUtils {
    private static Pattern Line_Pattern = Pattern.compile("_(\\w)");
    private static Pattern Hump_Pattern = Pattern.compile("[A-Z]");

    public StringUtil() {
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static String xor(String content) {
        content = change(content);
        String[] b = content.split(" ");
        int a = 0;

        for(int i = 0; i < b.length; ++i) {
            a ^= Integer.parseInt(b[i], 16);
        }

        if(a < 10) {
            StringBuffer sb = new StringBuffer();
            sb.append("0");
            sb.append(a);
            return sb.toString();
        } else {
            return Integer.toHexString(a);
        }
    }

    public static String change(String content) {
        String str = "";

        for(int i = 0; i < content.length(); ++i) {
            if(i % 2 == 0) {
                str = str + " " + content.substring(i, i + 1);
            } else {
                str = str + content.substring(i, i + 1);
            }
        }

        return str.trim();
    }

    public static String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] = (char)(chars[0] + 32);
        return String.valueOf(chars);
    }

    public static String byteToString(byte[] bytes) {
        StringBuilder strBuilder = new StringBuilder();

        for(int i = 0; i < bytes.length && bytes[i] != 0; ++i) {
            strBuilder.append((char)bytes[i]);
        }

        return strBuilder.toString();
    }

    public static String processEmpty(String str) {
        return isEmpty(str)?"":str;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String formatStr(int str, int nums, String blankStr) {
        return !"".equals(blankStr) && !"0".equals(blankStr)?"":String.format("%" + blankStr + nums + "d", new Object[]{Integer.valueOf(str)});
    }

    public static String objectToStr(Object obj) {
        return obj == null?null:(obj instanceof Integer?((Integer)obj).toString():(obj instanceof Double?((Double)obj).toString():(obj instanceof Float?((Float)obj).toString():(obj instanceof BigDecimal ?((BigDecimal)obj).toString():(obj instanceof String?(String)obj:null)))));
    }

    public static int countStr(String str, String s) {
        int count;
        for(count = 0; str.indexOf(s) != -1; ++count) {
            str = str.substring(str.indexOf(s) + 1, str.length());
        }

        return count;
    }

    public static String formatPrintData(String str, String formatLength) {
        String[] dataArr = str.split(",");
        String[] format = formatLength.split(",");

        for(int i = 0; i < dataArr.length; ++i) {
            dataArr[i] = String.format(format[i], new Object[]{str2object(dataArr[i], format[i].substring(format[i].length() - 1))});
        }

        return StringUtils.join(dataArr, "");
    }

    private static Object str2object(String s, String formatSource) {
        return "d".equals(formatSource)?new Integer(s):("f".equals(formatSource)?new BigDecimal(s):s);
    }

    public static String ifEmpty(String str, String replace) {
        return str != null && !"".equals(str)?str:replace;
    }

    public static boolean isNullOrUndefined(String str) {
        return "null".equals(str) || "undefined".equals(str);
    }

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = Line_Pattern.matcher(str);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String humpToLine(String str) {
        Matcher matcher = Hump_Pattern.matcher(str);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(3.3D);
        System.out.println("2019.12".substring(5, 7));
        System.out.println("01,02".contains("01"));
        String recMonth = "201901";
        recMonth = recMonth.substring(0, 4) + "." + recMonth.substring(4, 6);
        System.out.println(recMonth);
        String lineToHump = lineToHump("f_parent_no_leader");
        System.out.println(lineToHump);
        System.out.println(humpToLine(lineToHump));
    }
}
