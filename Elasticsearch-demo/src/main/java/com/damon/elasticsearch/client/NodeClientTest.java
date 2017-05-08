package com.damon.elasticsearch.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/1/4 15:40
 */
public class NodeClientTest {

    private static Client client;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void init() {
        Node node = NodeBuilder.nodeBuilder()
                .clusterName("es1")
                .local(true)
                .settings(Settings.settingsBuilder().put("http.enabled", false))
                .client(true).node();

        client = node.client();
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
