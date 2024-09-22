package com.candidateevaluationexercise;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyCSVSaverTest {
    private MyCSVSaver csvSaver; // Instance of MyCSVSaver for testing
    private String testDir; // Directory for test files

    @Before
    public void setUp() {
        csvSaver = new MyCSVSaver(); // Initialize the CSV saver instance
        testDir = "src\\test\\test_files"; // Specify your test directory
        new File(testDir).mkdirs(); // Create the test directory if it doesn't exist
    }


    @Test
    public void testValidCSVSaving() throws IOException {
        // Tests to see if data was saved as it should be
        String fileName = "testOutput.csv";
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Name", "Age", "Email"),
                Arrays.asList("John Doe", "30", "john.doe@example.com")
        );

        csvSaver.save(data, testDir, fileName); // Save the data to a CSV file

        assertTrue("CSV file should be created", Files.exists(Paths.get(testDir + fileName)));

        // Read the contents of the file to verify correctness
        List<String> lines = Files.readAllLines(Paths.get(testDir + fileName));
        
        assertEquals("There should be 2 lines in the CSV file", 2, lines.size());
        assertEquals("First line should be header", "Name,Age,Email", lines.get(0).replace("\"", "")); // Remove quotes for comparison
        assertEquals("Second line should be data", "John Doe,30,john.doe@example.com", lines.get(1).replace("\"", "")); // Remove quotes for comparison

        // Clean up
        Files.delete(Paths.get(testDir + fileName));
    }
}