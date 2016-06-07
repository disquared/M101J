package com.mongodb.hw2;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.m101j.util.Helpers.printJson;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class Homework23 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");

        System.out.println(collection.count());
        Bson sort = ascending("student_id", "score");
        
        List<Document> all = collection.find()
                                       .sort(sort)
                                       .into(new ArrayList<Document>());

        int prevStudentId = -99;
        for (Document cur : all) {
            Integer studentId = cur.getInteger("student_id");
            Double score = cur.getDouble("score");
            if (!studentId.equals(prevStudentId)) {
                Bson deleteFilter = and(eq("student_id", studentId), eq("score", score));
                //collection.deleteOne(deleteFilter);
                prevStudentId = studentId;
            }
            //printJson(cur);
        }
        
        System.out.println(collection.count());
        printJson(all);
        
        client.close();
    }
}
