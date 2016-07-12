package com.damon.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
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
                                .append("street", "4 Avenue")
                                .append("zipcode", "15")
                                .append("building", "80")
                                .append("coord", Arrays.asList(-11.9557413, 49.7720266)))
                                .append("borough", "DC.")
                                .append("cuisine", "USA")
                                .append("grades", Arrays.asList(
                                        new Document()
                                                .append("date", format.parse("2014-10-01T00:00:00Z"))
                                                .append("grade", "A")
                                                .append("score", 10),
                                        new Document()
                                                .append("date", format.parse("2014-01-16T00:00:00Z"))
                                                .append("grade", "A")
                                                .append("score", 11)))
                        .append("name", "Damon")
                        .append("restaurant_id", "4935220"));
    }

    @Test
    public void find() {
        FindIterable<Document> restaurants = db.getCollection("restaurants").find();
        restaurants.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info("单个文档的values为：{}", document.values());
                logger.info("单个文档的信息json为：{}", document.toJson());
            }
        });
    }

    @Test
    public void updateByName() {
        db.getCollection("restaurants")
                .updateOne(new Document("name", "Damon"),
                        new Document("$set", new Document("borough", "W DC."))
                                .append("$currentDate", new Document("lastModified", true)));

        findByName();
    }

    @Test
    public void findByName() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", "Damon");
        FindIterable<Document> restaurants = db.getCollection("restaurants").find(basicDBObject);
        restaurants.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                logger.info("单个文档的values为：{}", document.values());
                logger.info("单个文档的信息json为：{}", document.toJson());
            }
        });
    }

    @Test
    public void deleteOne() {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", "Damon");
        db.getCollection("restaurants").deleteOne(basicDBObject);

        findByName();
    }

}