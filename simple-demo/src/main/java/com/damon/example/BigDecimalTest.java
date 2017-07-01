package com.damon.example;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zhoujiwei on 2017/6/28.
 */
public class BigDecimalTest {

    @Test
    public void test() {
        BigDecimal bigDecimal = new BigDecimal("11.234");

        String s = "125.671";
        BigDecimal b = new BigDecimal(s);
        b = b.setScale(2, BigDecimal.ROUND_DOWN); //小数位 直接舍去
        System.out.println(b);
//b=b.setScale(2, BigDecimal.ROUND_HALF_UP); //四舍五入
//BigDecimal add(BigDecimal augend)
//BigDecimal subtract(BigDecimal subtrahend)
//BigDecimal multiply(BigDecimal multiplicand)
//BigDecimal divide(BigDecimal divisor)
        BigDecimal c = b.add(new BigDecimal("763.21"));


//        BigDecimal a=new BigDecimal("2332333.666");
        BigDecimal a=new BigDecimal("2332333.0");
        DecimalFormat df=new DecimalFormat(",###,##0.##"); //保留一位小数
        System.out.println(df.format(a));
        DecimalFormat df2=new DecimalFormat(",###,##0.00"); //保留一位小数
        System.out.println(df2.format(a));
    }
}
