package com.damon.example;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/14 16:32
 */
public class AddressTest {

    @Test
    public void test() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}
