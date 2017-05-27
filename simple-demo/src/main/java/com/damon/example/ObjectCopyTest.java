package com.damon.example;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/3/3 14:27.
 */
public class ObjectCopyTest {

    public static void main(String[] args) {
        A aa = new A("a1","b1");
        A bb = aa;
        bb.setB("b2");
        System.out.println(aa);
        System.out.println(bb);
    }

    static class A {
        private String a;
        private String b;

        @Override
        public String toString() {
            return "A{" +
                    "a='" + a + '\'' +
                    ", b='" + b + '\'' +
                    '}';
        }

        public A(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public void setA(String a) {
            this.a = a;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getA() {

            return a;
        }

        public String getB() {
            return b;
        }
    }
}
