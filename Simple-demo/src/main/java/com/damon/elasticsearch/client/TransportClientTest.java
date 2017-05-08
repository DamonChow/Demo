package com.damon.elasticsearch.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 功能：简单测试
 *
 * @author Damon
 * @since 2016/1/4 11:44
 */
public class TransportClientTest {
    private static Client client;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void init() {
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.2.20.110"), 9300));
        } catch (UnknownHostException e) {
            log.error("错误：" + e.getMessage(), e);
        }
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void test() {
        System.out.println(123);
    }
}
