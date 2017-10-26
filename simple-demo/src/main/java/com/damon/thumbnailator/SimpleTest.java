package com.damon.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 功能：裁剪图片
 *
 * @author Damon
 * @since 2016/3/16 15:34
 */
public class SimpleTest {

    @Test
    public void main() {

        File file = new File("C:\\Users\\KM\\Desktop\\图片\\GG.jpg");
        File deskFile = new File("C:\\Users\\KM\\Desktop\\图片\\GGdad.jpg");

        try {
            Thumbnails.of(file)
                    .size(800, 800)
                    .outputQuality(1f)
                    .keepAspectRatio(true).toFile(deskFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String imageUrl = "http://images2015.cnblogs.com/blog/443934/201611/443934-20161102211617783-991618094.png";
        try {
            URL url = new URL(imageUrl);
            System.out.println(url);
            BufferedImage bufferedImage = Thumbnails.of(url).asBufferedImage();
            System.out.println(bufferedImage.getWidth());   // 源图宽度
            System.out.println(bufferedImage.getHeight());   // 源图高度

            WritableRaster raster = bufferedImage.getRaster();

            Raster data = bufferedImage.getData();
//            data.get
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        InputStream murl = null;
        try {
            URL url = new URL("http://images2015.cnblogs.com/blog/443934/201611/443934-20161102211617783-991618094.png");
            murl = url.openStream();
            BufferedImage sourceImg = ImageIO.read(murl);
            System.out.println(sourceImg.getWidth());   // 源图宽度
            System.out.println(sourceImg.getHeight());   // 源图高度
            URLConnection conn = url.openConnection();

            System.out.println(conn.getRequestProperties());
            int size = conn.getContentLength();
            System.out.println(size);
            System.out.println(conn.getContentType());
            System.out.println(conn.getContent());
            System.out.println(conn.getExpiration());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != murl) {
                try {
                    murl.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
