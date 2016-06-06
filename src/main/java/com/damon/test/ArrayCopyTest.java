package com.damon.test;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/6/6 12:39
 */
public class ArrayCopyTest {

    public static void main(String []args){
        int []a = {1,3,4,5};

        toPrint(a);
        int []aFor=new int[a.length];
        //1.for循环复制
        System.out.println("===========1.使用for复制");
        for(int i=0;i<a.length;i++){
            aFor[i]=a[i];
        }
        aFor[2]=10;//改变aFor中的值原数组中的值不变
        System.out.print("数组a：");
        toPrint(a);
        System.out.print("数组aFor：");
        toPrint(aFor);

        //**2.使用System.arraycopy(src,srcpos,dest,destpos,length);
        System.out.println("===========2.使用System.arraycopy复制\n把aFor复制给a:");
        System.arraycopy(aFor,0,a,0,a.length);
        aFor[1]=9;//改变aFor中值
        toPrint(a);
        toPrint(aFor);

        //3.使用clone复制
        System.out.println("===========3.使用clone把aFor的值复制给a");
        a=(int[])aFor.clone();
        aFor[0]=8;
        toPrint(a);
        toPrint(aFor);

        //4.使用Arrays类的copyOf和copyOfRange实现对数组复制
        System.out.println("===========4.使用Arrays.copyOf/把aFor的值复制给a");
        a=java.util.Arrays.copyOf(aFor,aFor.length+1);
        aFor[3]=11;
        toPrint(a);
        toPrint(aFor);

    }
    static void toPrint(int[] a){
        for(int aa:a){
            System.out.print(" "+aa);
        }
        System.out.println();
    }
}
