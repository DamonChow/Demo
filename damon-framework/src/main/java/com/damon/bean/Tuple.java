package com.damon.bean;

import java.io.Serializable;

/**
 * 功能：三元
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/3/7 15:51
 */
public class Tuple<A, B, C> implements Serializable{

    private static final long serialVersionUID = 6184069443345694059L;

    private final A first;

    private final B second;

    private final C third;

    public Tuple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }
}
