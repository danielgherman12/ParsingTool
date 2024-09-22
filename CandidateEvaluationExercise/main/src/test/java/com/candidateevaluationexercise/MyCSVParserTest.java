package com.candidateevaluationexercise;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;



public class MyCSVParserTest {
    private MyCSVParser parser; // Instance of the MyCSVParser for testing

    @Before
    public void setUp() {
        // Initializes the MyCSVParser instance before each test.
        parser = new MyCSVParser(); 
    }

    @Test
    public void testValidCSVParsing() throws IOException {
        // Tests parsing of a valid CSV file with special symbols.
        String validFilePath = "src\\test\\test_files\\csvSpecialSymbols.csv";

        List<List<String>> result = parser.parse(validFilePath);

        assertNotNull("Parsed data should not be null for a valid CSV", result);
        assertTrue("There should be at least 1 record", result.size() >= 1);
        assertTrue("Each record should have at least 1 column", result.get(0).size() >= 1);
    }


    @Test
    public void testInvalidCSV() throws IOException {
        // Tests parsing of a CSV file with inconsistent column counts.
        String invalidFilePath = "src\\test\\test_files\\invalidCSV.csv";

        List<List<String>> result = parser.parse(invalidFilePath);

        assertNull("Parsing should return null for invalid CSV with wrong column count", result);
    }

    @Test
    public void testEmptyCSVFile() throws IOException {
        // Tests parsing of an empty CSV file.
        String emptyFilePath = "src\\test\\test_files\\emptyJson.json";
    
        List<List<String>> result = parser.parse(emptyFilePath);
    
        assertNotNull("Parsed data should not be null for an empty JSON", result);
        assertTrue("Parsed data should be empty for an empty CSV", result.isEmpty());
    }
    

    @Test
    public void testParseCSVWithSpecialCharacters() {
        // Tests parsing of a CSV file that contains special characters.
        List<List<String>> data = parser.parse("src\\test\\test_files\\csvSpecialSymbols.csv");
        assertNotNull("Parsed data should not be null for CSV with special characters", data);
        assertTrue("Parsed data should contain rows", data.size() > 0);
    }

    @Test
    public void testIOExceptionHandling() {
        // Tests handling of an invalid file path to trigger IOException
        String invalidFilePath = "src\\test\\test_files\\nonExistent.csv";

        List<List<String>> result = parser.parse(invalidFilePath);

        assertNull("Parsing should return null when IOException occurs", result);
    }
}