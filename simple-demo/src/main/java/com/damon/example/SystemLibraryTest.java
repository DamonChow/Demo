package com.damon.example;

import java.util.StringTokenizer;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-27 14:18
 */
public class SystemLibraryTest {

    public static void main(String[] args) {
        String property = System.getProperty("java.library.path");
        System.out.println(property);
        System.out.println("----------");
        StringTokenizer parser = new StringTokenizer(property, ":");
        while (parser.hasMoreTokens()) {
            System.out.println(parser.nextToken());
        }
    }
}
