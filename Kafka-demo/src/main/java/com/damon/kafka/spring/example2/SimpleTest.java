package com.damon.kafka.spring.example2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Damon on 2017/5/17.
 */
public class SimpleTest {

    @Test
    public void test() throws Exception {
        String configLocation = "applicationContext-example2.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        KafkaTemplate<Integer, String> kafkaTemplate = context.getBean("kafkaTemplate", KafkaTemplate.class);
        kafkaTemplate.send("annotated1", 0, "foo2");
        kafkaTemplate.flush();
        Listener listener = context.getBean("listener", Listener.class);
        assertTrue(listener.getLatch1().await(10, TimeUnit.SECONDS));
    }
}
