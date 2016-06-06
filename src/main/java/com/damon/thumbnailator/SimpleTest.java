package com.damon.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * 功能：裁剪图片
 *
 * @author Damon
 * @since 2016/3/16 15:34
 */
public class SimpleTest {

    public static void main(String[] args) {

        File file = new File("C:\\Users\\KM\\Desktop\\图片\\GG.jpg");
        File deskFile = new File("C:\\Users\\KM\\Desktop\\图片\\GGdad.jpg");

        try {
            Thumbnails.of(file)
                    .size(800,800)
                    .outputQuality(1f)
                    .keepAspectRatio(true).toFile(deskFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
