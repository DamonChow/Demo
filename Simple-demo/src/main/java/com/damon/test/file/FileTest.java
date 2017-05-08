package com.damon.test.file;

import java.io.File;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/3/31 14:10
 */
public class FileTest {

    public static void main(String[] args) {
        File file = new File("E:\\web\\supplier-pic\\upload\\activity\\20160329144248424_1691.jpg");

        System.out.println(file.length()/1024);
        System.out.println(file.exists());
    }
}
