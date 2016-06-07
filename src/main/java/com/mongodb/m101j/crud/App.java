/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.m101j.crud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

public class App {
    
    public static void main( String[] args ) {
        //MongoClient client = new MongoClient(); // localhost 27017
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);
        //MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        
        // databases and collections are lightweight and immutable
        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> coll = db.getCollection("test");
    }
}
