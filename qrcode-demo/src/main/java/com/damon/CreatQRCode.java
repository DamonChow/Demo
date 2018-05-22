package com.damon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018/5/22 11:31
 */
public class CreatQRCode {

    public static void main(String[] args) {
        //定义二维码的样式
        int width = 300;
        int height = 300;
        String format = "png";
        String contents = "http://blog.csdn.net/qq_30507287";//扫描二维码时产生的连接

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);//设置二维码的容错等级
        hints.put(EncodeHintType.MARGIN, 2);//边距

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            Path file = new File("../damon/img.png").toPath();//保存的路径
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
