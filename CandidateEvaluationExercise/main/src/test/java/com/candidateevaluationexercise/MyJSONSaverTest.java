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

public class MyJSONSaverTest {
    private MyJSONSaver jsonSaver; // Instance of MyJSONSaver for testing
    private String testDir; // Directory for test files

    @Before
    public void setUp() {
        jsonSaver = new MyJSONSaver(); // Initialize the JSON saver instance
        testDir = "src\\test\\test_files"; // Specify your test directory
        new File(testDir).mkdirs(); // Create the test directory if it doesn't exist
    }

    @Test
    public void testValidJSONSaving() throws IOException {
        // Test saving valid data to a JSON file
        String fileName = "testOutput.json";
        List<List<String>> data = Arrays.asList(
                Arrays.asList("John Doe", "30", "john.doe@example.com"),
                Arrays.asList("Jane Smith", "25", "jane.smith@example.com")
        );

        
        jsonSaver.save(data, testDir + "\\", fileName); // Save the data to a JSON file

        assertTrue("JSON file should be created", Files.exists(Paths.get(testDir + "\\" + fileName)));

        
        String content = new String(Files.readAllBytes(Paths.get(testDir + "\\" + fileName))); // Reads the contents of the file to verify correctness

        // Check if the actual JSON content contains the expected values
        assertTrue("JSON content should contain expected values",
                content.contains("John Doe") && content.contains("30") && content.contains("john.doe@example.com") &&
                content.contains("Jane Smith") && content.contains("25") && content.contains("jane.smith@example.com"));

        // Clean up
        Files.delete(Paths.get(testDir + "\\" + fileName));
    }

    //Do not need to add invalidJson testing as it tests the file at the beginning of the program.
}