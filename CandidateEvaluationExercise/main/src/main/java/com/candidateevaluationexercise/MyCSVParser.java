package com.candidateevaluationexercise;

import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;



// The MyCSVParser class implements the FileParser interface, providing functionality 
// to parse CSV files into a list of lists. This class uses the OpenCSV library 
// to handle the reading and parsing of CSV data.
public class MyCSVParser implements FileParser{
    private int expectedColumns = -1; // To track the expected number of columns

    // Method to parse a CSV file and return its contents as a list of lists.
    @Override
    public List<List<String>> parse(String filePath) {
        List<List<String>> listOfLists = new ArrayList<>(); // Initialize the result list


        try (Reader reader = new FileReader(filePath)) {
            // Parse the CSV file with header
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            for (CSVRecord record : records) {
                // Set the expected number of columns based on the first record
                if (expectedColumns == -1) {
                    expectedColumns = record.size();
                }

                // Check for empty rows or invalid data
                if (isRecordEmpty(record) || !isValidRecord(record)) {

                    System.out.println("Invalid CSV: contains empty or malformed rows.");
                    return null; // Return null for invalid CSV
                }

                List<String> list = new ArrayList<>();

                record.forEach(list::add);
                listOfLists.add(list); // Add the List<String> to the main list
            }

         //Catch block to handle IOException.
        } catch (IOException e) {

            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while reading the CSV file: " + e.getMessage());
            
            return null; // Returns null to signify that the parsing process failed

        }
        return listOfLists; // Return the parsed data
    }

    // Helper method to check if a record is empty
    private boolean isRecordEmpty(CSVRecord record) {
        for (String value : record) {
            if (value != null && !value.trim().isEmpty()) {
                return false; // Record is not empty
            }
        }

        return true; // Record is empty
    }

    // Helper method to check if a record matches the expected format.
    private boolean isValidRecord(CSVRecord record) {
        return record.size() == expectedColumns; // Ensures the record has the expected number of columns
    }
}
