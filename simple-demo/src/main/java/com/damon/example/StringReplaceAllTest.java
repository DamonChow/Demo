package com.damon.example;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/21 10:34
 */
public class StringReplaceAllTest {

    public static void main(String[] args) {
        String source = "国民党AAA国民党";
        String key = "国民党";
        source = source.replaceAll(key, "***");
        System.out.println(source);
        String lowerStr = source.toLowerCase();
        StringBuffer sb = new StringBuffer("");
        int fromIndex = 0;
        int endIndex = 0;
        while ((endIndex = lowerStr.indexOf(key.toLowerCase(), fromIndex)) >= 0) {
            sb.append(source.substring(fromIndex, endIndex)).append("***");
            fromIndex = endIndex + key.length();
        }
        sb.append(source.substring(fromIndex));
        System.out.println(sb.toString());
    }
}
