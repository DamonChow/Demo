package com.damon.elasticsearch;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 功能：简单测试
 *
 * @author Damon
 * @since 2016/1/4 11:44
 */
public class SimpleTest {
    private static Client client;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void init() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "es1").build();
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.2.20.110"), 9300));
        } catch (UnknownHostException e) {
            log.error("error：：：：" + e.getMessage(), e);
        }
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void get() {
        GetResponse response = client.prepareGet("megacorp", "employee", "1")
                .setOperationThreaded(false)//不同的线程执行
                .get();

        log.info("response=" + response.getSource());
    }

    @Test
    public void createIndex() throws IOException {
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("user", "kimchy")
                                .field("postDate", new Date())
                                .field("message", "trying out Elasticsearch")
                                .endObject()
                ).get();

        log.info("response info = " + response.toString());
        log.info("分割线----------------------------------");

        //索引名称
        String _index = response.getIndex();
        log.info("index is " + _index);
        //类型名称
        String _type = response.getType();
        log.info("type is " + _type);
        //id
        String _id = response.getId();
        log.info("id is " + _id);
        //如果是第一次索引这个文档返回1,每次操作加1
        long _version = response.getVersion();
        log.info("version is " + _version);

        //新建返回true，被修改返回false
        boolean created = response.isCreated();
        log.info("是否新建：" + created);
    }

    @Test
    public void DeleteIndex() {
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1")
                .get();

        //索引名称
        String _index = response.getIndex();
        log.info("index is " + _index);
        //类型名称
        String _type = response.getType();
        log.info("type is " + _type);
        //id
        String _id = response.getId();
        log.info("id is " + _id);
        //如果是第一次索引这个文档返回1,每次操作加1
        long _version = response.getVersion();
        log.info("version is " + _version);

        boolean found = response.isFound();
        log.info("返回索引是否存在：" + found);// 返回索引是否存在
        Set<String> headers = response.getHeaders();
        log.info("返回响应头：" + headers);// 返回响应头
    }

    @Test
    public void testJson() {
        //1.自定义string的json格式
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        log.info("自定义string的json格式：" + jsonString);

        //2.使用map
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        log.info("使用map：" + jsonMap);

        //3.使用序列号的bean构造json
        //初始化json mapper，创建一次，重复使用
        ObjectMapper mapper = new ObjectMapper();
        try {
            //生成json
            byte[] json = mapper.writeValueAsBytes(new Object());
            log.info("使用序列号的bean构造json：{}", json);
        } catch (IOException e) {
            log.error("error:" + e.getMessage(), e);
        }

        //4.使用Elasticsearch 提供的帮助类
        //同样可以使用startArray(String) and endArray()
        //field方法，可以使用类型 numbers, dates甚至是XContentBuilder 对象
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("user", "kimchy")
                    .field("postDate", new Date())
                    .field("message", "trying out Elasticsearch")
                    .endObject();
            String json = builder.string();
            log.info("使用Elasticsearch 提供的帮助类：{}", json);
        } catch (IOException e) {
            log.error("error:" + e.getMessage(), e);
        }
    }
}
