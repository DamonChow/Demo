package com.damon.redis.pubsub.keyspace.notification;

import com.damon.redis.pubsub.TestPubSub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/12/30 11:58
 */
public class Job extends JedisPubSub {

    protected static Logger logger = LoggerFactory.getLogger(TestPubSub.class);

    // 取得订阅的消息后的处理
    public void onMessage(String channel, String message) {
        logger.info("取得订阅的消息后的处理 : 渠道为{}，消息为{}。", new Object[]{channel, message});
    }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("初始化订阅时候的处理 : 渠道为{}，subscribedChannels为{}。", new Object[]{channel, subscribedChannels});
    }
}
