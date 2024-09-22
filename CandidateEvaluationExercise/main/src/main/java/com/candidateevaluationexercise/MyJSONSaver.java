package com.candidateevaluationexercise;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// The MyJSONSaver class implements the FileSaver interface, providing functionality 
// to save data in JSON format. This class converts a list of lists into a JSON array 
// of objects, where each object represents a row of data with column indices as keys.
public class MyJSONSaver implements FileSaver{
    @Override
    public void save(List<List<String>> data, String filePath, String fileName) {
        JSONArray jsonArray = new JSONArray(); // Creates a JSON array to hold the data

        // Iterates through each row of data to convert it into a JSON object.
        for (List<String> row : data) {
            JSONObject jsonObject = new JSONObject(); // Creates a new JSON object for each row

            // Maps each column index to its corresponding value.
            for (int i = 0; i < row.size(); i++) {
                jsonObject.put("column" + i, row.get(i)); 
            }

            jsonArray.add(jsonObject); // Adds the JSON object to the JSON array
        }
        
        // Try to write the JSON array to a file.
        try (FileWriter file = new FileWriter(filePath + fileName)) {

            // Writes the JSON array as a string to the file.
            file.write(jsonArray.toJSONString());

            // Informs the user that the data has been saved successfully.
            System.out.println("Data saved as JSON successfully"); 

        // Catch block to handle IOException.
        } catch (IOException e) {
            
            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while saving the data as JSON. Please check the file path and try again.");
        }
    }
}
