package com.damon.bean;

import java.io.Serializable;

/**
 * 功能：二元
 *
 * @author Damon
 * @since 2018/3/7 15:49
 */
public class Pair<A, B>  implements Serializable{

    private static final long serialVersionUID = -8268630341653008685L;

    private final A first;

    private final B second;

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
