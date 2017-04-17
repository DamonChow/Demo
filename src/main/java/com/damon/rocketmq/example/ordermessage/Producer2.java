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
package com.damon.rocketmq.example.ordermessage;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Producer2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("order_message_product2");
            producer.setNamesrvAddr("127.0.0.1:9876");
            producer.start();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);

            for (int i = 0; i < 5; i++) {
                String body = dateStr + " hello rocketMQ " + i;
                Message msg = new Message("TopicOrder2", "TagA", "KEY" + i, body.getBytes());
                //发送数据:如果使用顺序消息,则必须自己实现MessageQueueSelector,保证消息进入同一个队列中去.
                SendResult sendResult = producer.send(msg, (mqs, msg1, arg) -> {
                    Integer id = (Integer) arg;
                    System.out.println("id : " + id);
                    return mqs.get(id);
                }, 1);//队列下标 //orderID是选定的topic中队列的下标

                System.out.println(sendResult + " , body : " + body);
            }
            for (int i = 0; i < 5; i++) {
                String body = dateStr + " hello rocketMQ " + i;
                Message msg = new Message("TopicOrder2", "TagA", "KEY" + i, body.getBytes());
                //发送数据:如果使用顺序消息,则必须自己实现MessageQueueSelector,保证消息进入同一个队列中去.
                SendResult sendResult = producer.send(msg, (mqs, msg12, arg) -> {
                    Integer id = (Integer) arg;
                    System.out.println("id : " + id);
                    return mqs.get(id);
                }, 2);//队列下标 //orderID是选定的topic中队列的下标
                System.out.println(sendResult + " , body : " + body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
