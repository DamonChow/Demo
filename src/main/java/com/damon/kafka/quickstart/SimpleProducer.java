/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.damon.kafka.quickstart;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer {

    private static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

    private final Producer<String, String> producer;

    public final static String TOPIC = "linlin";

    private SimpleProducer() {
        Properties props = new Properties();
        // 此处配置的是kafka的端口
        props.put("metadata.broker.list", "127.0.0.1:9092");
        props.put("bootstrap.servers", "127.0.0.1:2181");
        // 配置key的序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 配置value的序列化类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("request.required.acks", "-1");

        producer = new KafkaProducer(props);
    }

    void produce() {
        int messageNo = 5000;
        final int COUNT = 10000;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new ProducerRecord(TOPIC, key, data));
            System.out.println(data);
            messageNo++;
        }
    }

    public static void main(String[] args) {
        new SimpleProducer().produce();
    }
}