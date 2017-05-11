package com.damon.kafka.example;

import com.damon.kafka.KafkaUtil;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

/**
 * Created by Damon on 2017/5/11.
 */
public class MyProducer {

    @Test
    public void producer() throws Exception {
            Producer<String, String> producer = KafkaUtil.getProducer();
        for (int i=0;i<1000;i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaUtil.TOPIC, String.valueOf(i), "this is message" + i);
            producer.send(record, (metadata, e) -> {
                if (e != null)
                    e.printStackTrace();
                System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
            });
            Thread.sleep(50);
        }
    }
}
