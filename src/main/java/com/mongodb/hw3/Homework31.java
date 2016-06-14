package com.mongodb.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.*;

import static com.mongodb.m101j.util.Helpers.printJson;

public class Homework31 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        System.out.println(collection.count());
        
        List<Document> allHws = collection.aggregate(Arrays.asList(
                new Document("$unwind", "$scores"),
                new Document("$match", new Document("scores.type", "homework")),
                new Document("$sort", new Document("_id", 1).append("scores.score", 1))
        )).into(new ArrayList<Document>());
        printJson(allHws);
        
        for (int i = 0; i < allHws.size(); i++) {
            if (i % 2 == 0) {
                Document hw = allHws.get(i);
                collection.updateOne(eq("_id", hw.get("_id")),
                        Updates.pull("scores", hw.get("scores")));
            }
        }
        printJson(collection.find().into(new ArrayList<Document>()));
        
        List<Document> output = collection.aggregate(Arrays.asList(
                new Document("$unwind", "$scores"),
                new Document("$group", new Document("_id", "$_id")
                        .append("average", new Document("$avg", "$scores.score"))),
                new Document("$sort", new Document("average", -1)),
                new Document("$limit", 1)
        )).into(new ArrayList<Document>());
        printJson(output);
        
        client.close();
    }
}
