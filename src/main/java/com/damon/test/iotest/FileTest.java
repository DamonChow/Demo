package com.damon.test.iotest;

import java.io.*;

import org.apache.commons.io.IOUtils;

/**
 * 功能：
 * Created by ZhouJW on 2015/7/6 15:21.
 */
public class FileTest {
    static String operation="~/uploadFile/";

    public static void main(String[] args) {
        //upload();

        downloadOperation();
    }

    private static void upload() {
        File dd = new File("C:\\Users\\ASUS\\Desktop\\wechat.txt");
        OutputStream os = null;
        FileReader fr = null;
        try {
            String canonicalPath = dd.getCanonicalPath();
            System.out.println(canonicalPath);

            fr = new FileReader(dd);
            File dir = new File(operation);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(operation + "test.txt");
            os = new FileOutputStream(file);
            IOUtils.copy(fr, os);

        } catch (Exception e) {
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void downloadOperation() {
        InputStream is = null;
        try {
            File dir = new File(operation);

            File[] files = dir.listFiles();
            if (files != null && files.length>0){
                File file = files[0];
                String name = file.getName();
                System.out.println("文件名：" + name);
                is = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
