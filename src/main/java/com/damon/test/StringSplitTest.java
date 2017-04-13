package com.damon.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Damon on 2017/4/13.
 */
public class StringSplitTest {
    
    private static final Logger logger = LoggerFactory.getLogger(StringSplitTest.class);

    @Test
    public void test(){
        int size = 1000000;
        String sourceStr = getOriginStr(size);
        if (size <= 100) {
            logger.info("源字符串：" + sourceStr);
        }
        //////////////String.split()表现//////////////////////////////////////////////
        logger.info("使用String.split()的切分字符串");
        long st1 = System.nanoTime();
        String [] result = sourceStr.split("\\.");
        logger.info("String.split()截取字符串用时：" + (System.nanoTime()-st1)/1000);
        logger.info("String.split()截取字符串结果个数：" + result.length);
        logger.info("---------------------------------------------");

        //////////////StringUtils.split()表现//////////////////////////////////////////////
        logger.info("使用StringUtils.split()的切分字符串");
        long st2 = System.nanoTime();
        String [] result2 = StringUtils.split(sourceStr, "\\.");
        logger.info("StringUtils.split()截取字符串用时：" + (System.nanoTime()-st2)/1000);
        logger.info("StringUtils.split()截取字符串结果个数：" + result2.length);
        logger.info("---------------------------------------------");

        //////////////StringTokenizer表现//////////////////////////////////////////////
        logger.info("使用StringTokenizer的切分字符串");
        long st3 = System.nanoTime();
        StringTokenizer token=new StringTokenizer(sourceStr,".");
        logger.info("StringTokenizer截取字符串用时:"+(System.nanoTime()-st3)/1000);
        logger.info("StringTokenizer截取字符串结果个数：" + token.countTokens());
        logger.info("---------------------------------------------");

        ////////////////////String.substring()表现//////////////////////////////////////////
        long st5 = System.nanoTime();
        int len = sourceStr.lastIndexOf(".");
        logger.info("使用String.substring()切分字符串");
        int k=0,count=0;

        for (int i = 0; i <= len; i++) {
            if(sourceStr.substring(i, i+1).equals(".")){
                if(count==0){
                    sourceStr.substring(0, i);
                }else{
                    sourceStr.substring(k+1, i);
                    if(i == len){
                        sourceStr.substring(len+1, sourceStr.length());
                    }
                }
                k=i;count++;
            }
        }
        logger.info("String.substring()截取字符串用时"+(System.nanoTime()-st5)/1000);
        logger.info("String.substring()截取字符串结果个数：" + (count + 1));
    }

    /**
     * 构造目标字符串
     * eg：10.123.12.154.154
     * @param len 目标字符串组数(每组由3个随机数组成)
     * @return
     */
    private static String getOriginStr(int len){

        StringBuffer sb = new StringBuffer();
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < len; i++){
            sb.append(random.nextInt(9)).append(random.nextInt(9)).append(random.nextInt(9));
            result.append(sb.toString());
            sb.delete(0, sb.length());
            if(i != len-1)
                result.append(".");
        }

        return result.toString();
    }
}
