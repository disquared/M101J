package com.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class Final7 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("photo");
        MongoCollection<Document> albums = database.getCollection("albums");
        MongoCollection<Document> images = database.getCollection("images");
        
        // create an index on the images array in the albums collection
        albums.createIndex(eq("images", 1));
        
        for (Document album : albums.find().into(new ArrayList<Document>())) {
            List<Integer> imageIds = (List<Integer>) album.get("images");
            for (int imageId : imageIds) {
                
            }
        }
        
        for (Document image : images.find().into(new ArrayList<Document>())) {
            Integer imageId = (Integer) image.get("_id");
            if (albums.count(eq("images", imageId)) == 0) {
                images.deleteOne(eq("_id", imageId));
            }
        }
        
        System.out.println(images.count());
        System.out.println(images.count(eq("tags", "sunrises")));
        
        client.close();
    }
}
