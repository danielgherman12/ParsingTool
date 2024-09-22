package com.candidateevaluationexercise;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class MyJSONParserTest {
    private MyJSONParser parser;

    @Before
    public void setUp() {
        // Initializes the parser instance before each test
        parser = new MyJSONParser();
    }

    @Test
    public void testValidJSONParsing() throws IOException {
        // Verify that a valid JSON file is parsed correctly.
        String validFilePath = "src\\test\\test_files\\jsonTest2.json";

        List<List<String>> result = parser.parse(validFilePath);
        assertNotNull("Parsed data should not be null for a valid JSON", result);
        assertFalse("Parsed data should not be empty for a valid JSON", result.isEmpty());
        assertTrue("Each record should have at least one value", result.get(0).size() > 0);
    }

     @Test
    public void testInvalidJSONParsing() throws IOException {
        // Ensure that parsing an invalid JSON file returns null.
        String invalidFilePath = "src\\test\\test_files\\invalidJson.json";

        List<List<String>> result = parser.parse(invalidFilePath);
        assertNull("Parsing should return null for invalid JSON", result);
    }

    @Test
    public void testEmptyJSONFile() throws IOException {
        // Check that parsing an empty JSON file returns an empty list.
        String emptyFilePath = "src\\test\\test_files\\emptyJson.json";
    
        List<List<String>> result = parser.parse(emptyFilePath);
        assertNotNull("Parsed data should not be null for an empty JSON", result);
        assertTrue("Parsed data should be empty for an empty JSON", result.isEmpty());
    }


    @Test
    public void testIOExceptionHandling() {
        // Validate that parsing a non-existent file does not return null.
        String nonExistentFilePath = "src\\test\\test_files\\nonExistent.json";

        List<List<String>> result = parser.parse(nonExistentFilePath);
        assertNotNull("Parsing should not return null when IOException occurs", result);
        assertTrue("Parsing result should be empty when IOException occurs", result.isEmpty());
    }
}