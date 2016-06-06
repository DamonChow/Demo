package com.damon.test;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/3/21 16:20
 */
public class IntegerDemo {

    public static void main(String[] args) {
        int a = 2;

        switch (a) {
            case 3:
                System.out.println(333);
                break;
            case 1:
                System.out.printf("1111");;
                break;
            case 2:
            default:
                System.out.println("default");
                break;
        }
    }
}
