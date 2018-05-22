package com.damon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018/5/22 15:40
 */
public class DamonUtil {

    public static void main(String[] args) {
        int width = 300;
        int height = 300;
        String format = "png";
        String content = "a";
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Map<EncodeHintType, Object> config = new HashMap<>();
            config.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            config.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, config);
            MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
            byte[] bytes = stream.toByteArray();
        } catch (Exception e) {
//            log.error("二维码生成失败，", e);
        }
    }
}
