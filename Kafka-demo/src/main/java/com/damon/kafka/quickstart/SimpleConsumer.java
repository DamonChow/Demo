package com.damon.kafka.quickstart;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This example shows how to subscribe and consume messages using providing {@link }.
 */
public class SimpleConsumer {

    private KafkaConsumer<String, String> consumer;

    private SimpleConsumer() {
        Properties props = new Properties();
        /*// zookeeper 配置
        props.put("zookeeper.connect", "127.0.0.1:2181");
        // group 代表一个消费组
        props.put("group.id", "damon_consumer");
        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("rebalance.max.retries", "5");
        props.put("rebalance.backoff.ms", "1200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
*/
        props.put("bootstrap.servers", "localhost:9092");
        //消费者的组id
        props.put("group.id", "GroupA");//这里是GroupA或者GroupB
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        //从poll(拉)的回话处理时长
        props.put("session.timeout.ms", "30000");
        //poll的数量限制
        //props.put("max.poll.records", "100");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String, String>(props);
    }

    void consume() {
        //订阅主题列表topic
        consumer.subscribe(Arrays.asList(SimpleProducer.TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                //　正常这里应该使用线程池处理，不应该在这里处理
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value()+"\n");

        }
    }

    public static void main(String[] args) {
        new SimpleConsumer().consume();
    }
}
