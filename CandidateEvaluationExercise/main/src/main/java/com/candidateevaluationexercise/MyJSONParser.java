package com.candidateevaluationexercise;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;



// The MYJSONParser class implements the FileParser interface, providing functionality 
// to parse JSON files into a list of lists. This class uses the JSON.simple library 
// to handle the reading and parsing of JSON data.
public class MyJSONParser implements FileParser{
    @Override
    public List<List<String>> parse(String filePath) {
        // Creates an ObjectMapper instance for JSON parsing.
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<String>> result = new ArrayList<>(); // Initialize the result list

        File file = new File(filePath); // Creates a File object

        // Checks if the file is empty.
        if (file.length() == 0) {
            return new ArrayList<>(); // Return an empty list for an empty file
        }

        try {
            // Parse the JSON file into a Map
            Map<String, Object> jsonMap = objectMapper.readValue(new File(filePath), Map.class);

            // Process the map into your desired list format
            for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                List<String> row = new ArrayList<>();
                row.add(entry.getKey()); // Add the key to the row

                // Handle different value types
                if (entry.getValue() instanceof List) {
                    List<?> values = (List<?>) entry.getValue();
                    for (Object value : values) {
                        row.add(value.toString());
                    }
                } else {
                    row.add(entry.getValue().toString());
                }

                result.add(row); // Add the row to the result list
            }

        // Catch block to handle IOException.
        } catch (IOException e) {

            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while parsing the JSON file. Please check the file path and format.");

            return null; // Returns null to signify that the parsing process failed
        }

        return result; // Return the parsed data
    }
}
