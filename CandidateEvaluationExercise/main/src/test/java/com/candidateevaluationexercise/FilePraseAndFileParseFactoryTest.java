package com.candidateevaluationexercise;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FilePraseAndFileParseFactoryTest {
    
    @Test
    public void testGetCSVParser() {
        // Tests that a CSV file returns a non-null MyCSVParser instance.
        FileParser parser = FileParserFactory.getParser("src\\test\\test_files\\csvTest.csv");
        assertNotNull("CSV parser should not be null", parser);
        assertTrue("Parser should be instance of MyCSVParser", parser instanceof MyCSVParser);
    }

    @Test
    public void testGetJSONParser() {
        // Tests that a JSON file returns a non-null MyJSONParser instance.
        FileParser parser = FileParserFactory.getParser("src\\test\\test_files\\jsonTest.json");
        assertNotNull("JSON parser should not be null", parser);
        assertTrue("Parser should be instance of MyJSONParser", parser instanceof MyJSONParser);
    }

    @Test
    public void testGetXMLParser() {
        // Tests that an XML file returns a non-null MyXMLParser instance.
        FileParser parser = FileParserFactory.getParser("src\\test\\test_files\\xmlTest.xml");
        assertNotNull("XML parser should not be null", parser);
        assertTrue("Parser should be instance of MyXMLParser", parser instanceof MyXMLParser);
    }

    @Test
    public void testGetUnsupportedParser() {
        // Tests that an unsupported file type returns null.
        FileParser parser = FileParserFactory.getParser("src\\test\\test_files\\wrongFile.txt");
        assertNull("Parser should be null for unsupported file type", parser);
    }

    @Test
    public void testGetParserWithoutExtension() {
        // Tests that a file without an extension returns null.
        FileParser parser = FileParserFactory.getParser("src\\test\\test_files\\csvTest2");
    
        assertNull("Parser should be null for file without extension", parser);
    }
}