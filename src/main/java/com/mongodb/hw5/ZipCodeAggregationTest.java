package com.mongodb.hw5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.m101j.util.Helpers;

import static org.bson.Document.parse;

/**
 * https://docs.mongodb.com/manual/tutorial/aggregation-zip-code-data-set/
 */
public class ZipCodeAggregationTest {
   
   public static void main(String[] args) {
      MongoClient client = new MongoClient();
      MongoDatabase database = client.getDatabase("test");
      MongoCollection<Document> collection = database.getCollection("zips");
      
      List<Document> pipeline = Arrays.asList(
               parse("{ $group: { _id: '$state', totalPop: { $sum: '$pop'} } }"),
               parse("{ $match: {totalPop: { $gte: 10000000 } } }"),
               parse("{ $sort: {totalPop: -1 } }"));
      
      List<Document> results = collection.aggregate(pipeline).into(new ArrayList<>());
      
      Helpers.printJson(results);
      
      client.close();
   }
}
