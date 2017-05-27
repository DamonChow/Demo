package com.damon.example;

import org.junit.Test;

/**
 * 三目运算符和ifElse对比
 *
 * Created by Damon on 2017/5/22.
 */
public class ThreeMeshOperationTest {

    @Test
    public void test() {
        double f = 0;
        final int times = 100;
        double min = 1000;
        double max = 0;
        //取100次平均。其实可以再多，但是耗时太长了，意义并不是很大。
        for(int i=0; i<times; i++){
            double df = complete();
            System.out.println("df["+i+"] = "+df);
            f +=df;
            if(min>df){
                min = df;
            }
            if(max<df){
                max = df;
            }
        }
        f /=times;
        System.out.println("------->min = "+min);
        System.out.println("------->max = "+max);
        System.out.println("------->ave = "+f);
    }

    public double complete(){
        //循环次数，有点大哦，自己悠着点调
        final int max = 100000000;

        //计算出一个布尔值，免得后面还要计算而占用时间，造成误差。
        long sum = -1;
        boolean flag = sum>0;
        //下面是If/Else（包含循环和计算）的耗时统计，真实耗时应当除去上面的基准值
        long timeS1 = System.currentTimeMillis();
        for(int i=0; i<max; i++){
            if(flag){
                sum = i+1;
            }else{
                sum = i+2;
            }
        }
        long timeE1 = System.currentTimeMillis();
        long realIfElse = timeE1 - timeS1;
        System.out.print("number=" + sum + ", timeE1=" + timeE1 + ",timeS1=" + timeS1);


        //下面是三目运算符（包含循环和计算）的耗时统计，真实耗时同样应当除去前面的基准值
        long timeS2 = System.currentTimeMillis();
        for(int i=0; i<max; i++){
            sum = flag?i+2:i+1;
        }
        long timeE2 = System.currentTimeMillis();
        long realTri = timeE2 - timeS2;
        System.out.print(",number=" + sum + ", timeE2=" + timeE2 + ",timeS2=" + timeS2);

        //返回耗时比率，以反映效率差别
        System.out.println("realIfElse="+realIfElse+",realTri="+realTri);
        double f = (double)realIfElse / realTri;
        return f;
    }
}
