package com.damon.mongodb;

import com.alibaba.fastjson.JSON;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/5/25 14:39
 */
public class SimpleTest {

    private Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    MongoDatabase db;

    @Before
    public void before() {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase("test");
    }

    @Test
    public void insert() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        db.getCollection("restaurants").insertOne(
                new Document("address",
                        new Document()
                                .append("street", "2 Avenue")
                                .append("zipcode", "10075")
                                .append("building", "1480")
                                .append("coord", Arrays.asList(-73.9557413, 40.7720266)))
                                .append("borough", "Manhattan")
                                .append("cuisine", "Italian")
                                .append("grades", Arrays.asList(
                                        new Document()
                                                .append("date", format.parse("2014-10-01T00:00:00Z"))
                                                .append("grade", "A")
                                                .append("score", 11),
                                        new Document()
                                                .append("date", format.parse("2014-01-16T00:00:00Z"))
                                                .append("grade", "B")
                                                .append("score", 17)))
                        .append("name", "Vella")
                        .append("restaurant_id", "41704620"));
    }

    @Test
    public void find() {
        FindIterable<Document> restaurants = db.getCollection("restaurants").find();
        restaurants.map(new Function<Document, Object>() {

            @Override
            public Object apply(Document document) {
                logger.info("xx{}",document.values());
                logger.info(document.toJson());
                return null;
            }
        });
    }


}
