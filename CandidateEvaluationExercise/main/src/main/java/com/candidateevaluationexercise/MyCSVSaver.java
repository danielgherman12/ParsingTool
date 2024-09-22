package com.candidateevaluationexercise;

import java.util.*;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;



// The MyCSVSaver class implements the FileSaver interface, providing functionality 
// to save data in CSV format. This class uses OpenCSV to handle the writing of 
// CSV files, ensuring that the data is formatted correctly.
public class MyCSVSaver implements FileSaver{

    @Override
    public void save(List<List<String>> data, String filePath, String fileName) {
        // Attempts to save the provided data to a CSV file at the specified path and name.
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath + fileName))) {


            // Iterates through each row of data and write it to the CSV file.
            for (List<String> row : data) {
                writer.writeNext(row.toArray(new String[0])); // Converts List<String> to String[] for writing.
            }

            // Informs the user that the data has been saved successfully.
            System.out.println("Data saved as CSV successfully");
            System.out.println("Saved too:" +  filePath + fileName);

        // Catch block to handle IOException.
        } catch (IOException e) {

            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while saving the CSV file. Please check the file path and try again.");
        }
    }
}
