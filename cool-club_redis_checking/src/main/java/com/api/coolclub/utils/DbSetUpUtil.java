package com.api.coolclub.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/*
 * @Author Rohan_Sharma
*/

@Component
public class DbSetUpUtil {
    private static final Logger log = LoggerFactory.getLogger(DbSetUpUtil.class);

    private final MongoTemplate mongoTemplate;
    private final Resource jsonResource;
    private final Boolean loadDBJson;
    public DbSetUpUtil(MongoTemplate mongoTemplate, @Value("${dbjson.resource.location}") Resource jsonResource, @Value("${loadDBJson}") Boolean loadDBJson) {
        this.mongoTemplate = mongoTemplate;
        this.jsonResource = jsonResource;
        this.loadDBJson = loadDBJson;
    }

    public void initializeCollection() {
        try {
            if(Boolean.TRUE.equals(loadDBJson)){

                // Read JSON file content
                String jsonContent = readJsonContent(jsonResource);

                // Parse JSON array
                JSONArray jsonArray = new JSONArray(jsonContent);

                // Iterate over JSON array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject collectionObject = jsonArray.getJSONObject(i);

                    // Collection name
                    String collectionName = collectionObject.getString("collection");

                    // Check if the collection exists, create it if not
                    if (!collectionExists(collectionName)) {
                        mongoTemplate.createCollection(collectionName);
                        log.info("[initializeCollection]: Collection created: {}",collectionName);
                    }

                    // Insert data into the collection
                    JSONArray data = collectionObject.getJSONArray("data");
                    for (int k = 0; k < data.length(); k++) {
                        JSONObject dataObject = data.getJSONObject(k);
                        Document document = Document.parse(dataObject.toString());
                        mongoTemplate.insert(document, collectionName);
                    }

                    log.info("[initializeCollection]: Data inserted into: {}",collectionName);
                }
            }

        } catch (IOException e) {
            log.error("-- ERROR :[initializeCollection]",e);
        }
    }

    private String readJsonContent(Resource resource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private boolean collectionExists(String collectionName) {
        List<String> collectionNames = new ArrayList<>();
        mongoTemplate.getCollectionNames().forEach(collectionNames::add);
        return collectionNames.contains(collectionName);
    }
}
