package com.candidateevaluationexercise;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParsingToolTest {
    private DataBaseHelper dbHelper; // Instance of DataBaseHelper for database operations

    @Before
    public void setUp() {
        // Initializes the database helper and save file metadata for test files.
        dbHelper = new DataBaseHelper();
        dbHelper.saveFileMetadata("src\\test\\test_files\\csvSpecialSymbols.csv", "csvSpecialSymbols.csv", "csv");
        dbHelper.saveFileMetadata("src\\test\\test_files\\jsonTest.json", "jsonTest.json", "json");
        dbHelper.saveFileMetadata("src\\test\\test_files\\xmlTest.xml", "xmlTest.xml", "xml");
    }

    @After
    public void tearDown() {
        // Clear the database after each test
        dbHelper.deleteFileMetadata("csvSpecialSymbols.csv");
        dbHelper.deleteFileMetadata("jsonTest.json");
        dbHelper.deleteFileMetadata("xmlTest.xml");
    }

    @Test
    public void testAccessFileFromDatabase() {
        // Test retrieving a file path from the database.
        String chosenFilePath = dbHelper.getFilePathByFileName("csvSpecialSymbols.csv");
        assertNotNull("File path should not be null", chosenFilePath);
    }

    @Test
    public void testFileNotFound() {
        // Test checking accessibility of a nonexistent file.
        String invalidPath = "src\\test\\test_files\\SpecialSymbols.csv";
        
        boolean isAccessible = FileAccesibility.isFileAccessible(invalidPath);
        assertFalse("File should not be accessible", isAccessible);
    }

    @Test
    public void testInvalidFileExtension() {
        // Test getting a parser for a file with an unsupported extension.
        String invalidFilePath = "src\\test\\test_files\\invalidFile.txt"; 

        FileParser parser = FileParserFactory.getParser(invalidFilePath);
        assertNull("Parser should be null for an unsupported file extension", parser);
    }

    @Test
    public void testFileWithoutExtension() {
        // Test getting a parser for a file that has no extension
        String noExtensionFilePath = "src\\test\\test_files\\fileWithoutExtension"; 

        FileParser parser = FileParserFactory.getParser(noExtensionFilePath);
        assertNull("Parser should be null for a file with no extension", parser);
    }
    

    @Test
    public void testEmptyFileParsing() {
        // Test parsing an empty file
        String emptyFilePath = "src\\test\\test_files\\emptyCSV.csv"; 
        FileParser parser = FileParserFactory.getParser(emptyFilePath);

        List<List<String>> parsedData = parser.parse(emptyFilePath);
        assertNotNull("Parsed data should not be null for an empty file", parsedData);
        assertTrue("Parsed data should be empty for an empty file", parsedData.isEmpty());
    }
}