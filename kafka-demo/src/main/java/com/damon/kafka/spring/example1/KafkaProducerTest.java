package com.damon.kafka.spring.example1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Map;

/**
 * Created by Damon on 2017/5/15.
 */
public class KafkaProducerTest {

    public static final Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    public static void main(String[] args) throws Exception {
        String configLocation = "applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);

        KafkaProducerServer kafkaProducer = context.getBean("kafkaProducerServer", KafkaProducerServer.class);
        String topic = "order_topic";
        String value = "test_message";
        String role = "test";//用来生成key

        Map<String, Object> res = kafkaProducer.sndMesForTemplate(topic, value, role);
        System.out.println("测试结果如下：===============");
        String message = (String) res.get("message");
        String code = (String) res.get("code");
        System.out.println("code:" + code);
        System.out.println("message:" + message);
        while(true){
            logger.info("current time: " + new Date());
            Thread.sleep(2000);
        }
    }
}
