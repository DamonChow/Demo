package com.damon.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**

 * Created by Damon on 2017/5/11.
 */
public class KafkaUtil {

    public final static String TOPIC = "damon";

    private static KafkaProducer<String, String> producer;

    private static KafkaConsumer<String, String> consumer;

    public static KafkaProducer<String, String> getKafkaProducer() {
        if (producer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "0");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producer = new KafkaProducer<String, String>(props);
        }
        return producer;
    }


    public static KafkaConsumer<String, String> getKafkaConsumer() {
        if(consumer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "12");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer<String, String>(props);
        }

        return consumer;
    }
}
