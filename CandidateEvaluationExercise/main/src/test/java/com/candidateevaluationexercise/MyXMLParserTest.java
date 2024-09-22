package com.candidateevaluationexercise;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

public class MyXMLParserTest {
    private MyXMLParser parser; // Instance of MyXMLParser for testing

    @Before
    public void setUp() {
        // Initialize the XML parser instance
        parser = new MyXMLParser();
    }

    @Test
    public void testValidXMLParsing() throws IOException {
        // Test parsing a valid XML file
        String validFilePath = "src\\test\\test_files\\xmlTest2.xml";

        List<List<String>> result = parser.parse(validFilePath);
        assertNotNull("Parsed data should not be null for a valid XML", result);
        assertTrue("There should be at least 1 record", result.size() >= 1);
        assertTrue("Each record should have at least 1 value", result.get(0).size() >= 1);  
    }

    @Test
    public void testInvalidXMLParsing() throws IOException {
        // Test parsing an invalid XML file (broken XML format)
        String invalidFilePath = "src\\test\\test_files\\invalidXml.xml";

        List<List<String>> result = parser.parse(invalidFilePath);
        assertNull("Parsing should return null for invalid XML", result);
    }
    
    @Test
    public void testEmptyXMLFile() throws IOException {
        // Test parsing an empty XML file
        String emptyFilePath = "src\\test\\test_files\\emptyXml.xml";

        List<List<String>> result = parser.parse(emptyFilePath);
        assertNotNull("Parsed data should not be null for an empty JSON", result);
        assertTrue("Parsed data should be empty for an empty JSON", result.isEmpty());
    }

    @Test
    public void testIOExceptionHandling() {
        // Test handling IOException for a non-existent XML file
        String nonExistentFilePath = "src\\test\\test_files\\nonExistent.xml";

        List<List<String>> result = parser.parse(nonExistentFilePath);
        assertNotNull("Parsing should not return null when IOException occurs", result);
        assertTrue("Parsing result should be empty when IOException occurs", result.isEmpty());
    }
}