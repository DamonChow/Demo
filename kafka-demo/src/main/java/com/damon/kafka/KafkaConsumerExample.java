package com.damon.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * http://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
 *
 * Created by Damon on 2017/5/15.
 */
public class KafkaConsumerExample {

    /**
     * Automatic Offset Committing 自动 Offset 提交
     *
     * enable.auto.commit 意味着offset将会得到自动提交，而这个自动提交的时间间隔由 auto.commit.interval.ms 来进行控制
     *
     * 客户端通过 bootstrap.servers 的配置来连接服务器，这个配值当中可以是一个或多个broker，需要注意的是，
     * 这个配置仅仅用来让客户端找到我们的server集群，而不需要把集群当中的所有服务器地址都列上。
     *
     * 在这个例子当中，客户端作为test group的一员，订阅了foo和bar2个topic。
     *
     * 首先假设，foo和bar这2个topic，都分别有3个partitions，同时我们将上面的代码在我们的机器上起3个进程，也就是说，
     * 在test group当中，目前有了3个consumer，一般来讲，这3个consumer会分别获得 foo和bar 的各一个partitions，这是前提。
     * 3个consumer会周期性的执行一个poll的动作（这个动作当中隐含的有一个heartbeat的发送，来告诉cluster我是活的），
     * 这样3个consumer会持续的保有他们对分配给自己的partition的访问的权利，
     * 如果某一个consumer失效了，也就是poll不再执行了，cluster会在一段时间（ session.timeout.ms ）之后把partitions分配给其他的consumer。
     *
     * 反序列化的设置，定义了如何转化bytes，这里我们把key和value都直接转化为string。
     */
    public void simple() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
        }
    }

    /**
     * Manual Offset Control 手动的offset控制
     *
     * 除了周期性的自动提交offset之外，用户也可以在消息被消费了之后提交他们的offset。
     * 某些情况下，消息的消费是和某些处理逻辑相关联的，我们可以用这样的方式，手动的在处理逻辑结束之后提交offset。
     * 简要地说，在这个例子当中，我们希望每次至少消费200条消息并将它们插入数据库，之后再提交offset。如果仍然使用前面的自动提交方式，
     * 就可能出现消息已经被消费，但是插入数据库失败的情况。这里可以视作一个简单的事务封装。
     * 但是，有没有另一种可能性，在插入数据库成功之后，提交offset之前，发生了错误，或者说是提交offset本身发生了错误，那么就可能出现某些消息被重复消费的情况。
     * 人认为这段话说的莫名其妙，简单地说，采用这样的方式，消息不会被丢失，但是有可能出现重复消费。
     *
     * 上面的例子当中，我们用commitSync来标记所有的消息；在有些情况下，我们可能希望更加精确的控制offset，
     * 那么在下面的例子当中，我们可以在每一个partition当中分别控制offset的提交。
     * 注意：提交的offset应该是next message，所以，提交的时候需要在当前最后一条的基础上+1.
     *
     */
    public void manualOffset1() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
        //我们用commitSync来标记所有的消息
    }


    /**
     * 在有些情况下，我们可能希望更加精确的控制offset，那么在下面的例子当中，我们可以在每一个partition当中分别控制offset的提交。
     */
    public void manualOffset2() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));

        try {
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * Manual Partition Assignment 手动的分区分配
     *
     * 前面的例子当中，我们订阅一个topic，然后让kafka把该topic当中的不同partitions，公平的在一个consumer group内部进行分配。
     * 那么，在某些情况下，我们希望能够具体的指定partitions的分配关系。
     * 如果某个进程在本地管理了和partition相关的状态，那么它只需要获得跟他相关partition。
     * 如果某个进程自身具备高可用性，那么就不需要kafka来检测错误并重新分配partition，因为消费者进程会在另一台设备上重新启动。
     * 要使用这种模式，可以用assign方法来代替subscribe，具体指定一个partitions列表。
     *
     * 分配之后，就可以像前面的例子一样，在循环当中调用poll来消费消息。手动的分区分配不需要组协调，所以消费进程失效之后，
     * 不会引发partition的重新分配，每一个消费者都是独立工作的，即使它和其他消费者属于同一个group。
     * 为了避免offset提交的冲突，在这种情况下，通常我们需要保证每一个consumer使用自己的group id。
     *
     * 需要注意的是，手动partition分配和通过subscribe实现的动态的分区分配，2种方式是不能混合使用的。
     *
     */
    public void manualPartition () {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "foo";
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        consumer.assign(Arrays.asList(partition0, partition1));
    }

}

//Multi-threaded Processing
class KafkaConsumerRunner implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer consumer = null;

    public void run() {
        try {
            consumer.subscribe(Arrays.asList("topic"));
            while (!closed.get()) {
                ConsumerRecords records = consumer.poll(10000);
                // Handle new records
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) throw e;
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
