package com.candidateevaluationexercise;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataBaseHelperTest {
    private static final String TEST_DB_URL = "jdbc:sqlite:src\\test\\test_files\\db_test_files\\test_file_metadata.db"; // Test database URL
    private DataBaseHelper dbHelper; // Instance of the DataBaseHelper for tests

    @Before
    public void setUp() {
        // Set up the test database URL in the DataBaseHelper.
        try {

            // Use reflection to set the private DB_URL field in DataBaseHelper.
            java.lang.reflect.Field dbUrlField = DataBaseHelper.class.getDeclaredField("DB_URL");

            dbUrlField.setAccessible(true); // Makes the field accessible
            dbUrlField.set(null, TEST_DB_URL); // Sets the field to the test database URL

        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Fail the test if setup fails
            fail("Failed to set up test database URL: " + e.getMessage());
        }

        dbHelper = new DataBaseHelper(); // Initialize the DataBaseHelper for testing
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up the test database after each test.
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS FileMetadata"); // Drop the test table if it exists
        }

        // Attempt to delete the test database file
        File dbFile = new File(TEST_DB_URL.replace("jdbc:sqlite:", ""));

        if (dbFile.exists()) {
            boolean deleted = dbFile.delete(); // Attempt to remove the test database file

            if (!deleted) {
                System.out.println("Failed to delete test database file.");

            } else {
                System.out.println("Test database file deleted successfully.");

            }
        } else {
            System.out.println("Test database file does not exist.");

        }
    }

    @Test
    public void testIsDatabaseEmptyInitially() {
        // Verifies that the database is empty initially.
        assertTrue("The database should be empty initially.", dbHelper.isDatabaseEmpty());
    }

    @Test
    public void testDatabaseNotEmptyAfterInsert() {
        // Saves a file metadata and check that the database is not empty.
        dbHelper.saveFileMetadata("src\\test\\test_files\\", "testFile", "txt");
        assertFalse("The database should not be empty after saving a file.", dbHelper.isDatabaseEmpty());
    }

    @Test
    public void testSaveAndRetrieveFileMetadata() {
        // Saves file metadata and retrieve it to verify correctness.
        dbHelper.saveFileMetadata("src\\test\\test_files\\", "csvTest", "csv");

        List<String> metadata = dbHelper.retrieveFileMetadata();
    
        // Verify that the retrieved metadata matches what was saved.
        assertFalse("Metadata list should not be empty.", metadata.isEmpty());
        assertTrue("The saved file name should match.", metadata.get(0).contains("csvTest"));
        assertTrue("The saved file path should match.", metadata.get(0).contains("src\\test\\test_files\\"));
        assertTrue("The saved file type should match.", metadata.get(0).contains("csv"));
    }

    @Test
    public void testGetFilePathByNonExistentFileName() {
        // Attempts to get the file path for a nonexistent file and expect null.
        String nonExistentFilePath = dbHelper.getFilePathByFileName("nonExistentFile");
        assertNull("The file path for a nonexistent file should be null.", nonExistentFilePath);
    }

    @Test
    public void testDeleteFileMetadata() {
        // Saves a file metadata, delete it, and verify the database is empty.
        dbHelper.saveFileMetadata("src\\test\\test_files\\", "deleteTest", "txt");
        dbHelper.deleteFileMetadata("deleteTest");

        assertTrue("The database should be empty after deletion.", dbHelper.isDatabaseEmpty());
    }
}