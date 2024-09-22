package com.candidateevaluationexercise;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



 //The DataBaseHelper class provides methods to interact with a SQLite database to store, 
 //retrieve, update, and delete file metadata, including file paths, names, types, and timestamps. 
 //It helps manage metadata for files stored on the user's computer or in the database.
public class DataBaseHelper {

    // Defines the database URL for the SQLite database.
    private static String DB_URL = "jdbc:sqlite:src\\test\\db_files\\file_metadata.db";

    private Connection connection;

    // Constructor to initialize the database helper and create the metadata table if it doesn't exist.
    public DataBaseHelper() {
        createTable();
    }

    // Method to create a table for storing file metadata (file path, file name, file type).
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS FileMetadata (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "filePath TEXT, " +
                "fileName TEXT, " +
                "fileType TEXT, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

        // Trys to connect to the database and execute the SQL statement to create the table.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table FileMetadata created successfully.");

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    

    // Method to save file metadata into the database.
    public void saveFileMetadata(String filePath, String fileName, String fileType) {
        String sql = "INSERT INTO FileMetadata(filePath, fileName, fileType) VALUES(?, ?, ?)";

        // Trys to connect to the database and insert the metadata into the table.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Sets the filePath, fileName, and fileType values to insert.
            pstmt.setString(1, filePath);
            pstmt.setString(2, fileName);
            pstmt.setString(3, fileType);
            pstmt.executeUpdate();

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error saving file metadata: " + e.getMessage());
        }
    }

    // Method to retrieve file metadata from the database
    public List<String> retrieveFileMetadata() {
        String sql = "SELECT filePath, fileName, fileType, timestamp FROM FileMetadata";
        List<String> fileMetadataList = new ArrayList<>();

        // Trys to connect to the database and retrieve all records.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loops through the result set and add each file's metadata to the list
            while (rs.next()) {
                String fileInfo = "File: " + rs.getString("fileName") + 
                                  ", Path: " + rs.getString("filePath") + 
                                  ", Type: " + rs.getString("fileType") + 
                                  ", Added on: " + rs.getString("timestamp");
                fileMetadataList.add(fileInfo);
            }

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error retrieving file metadata: " + e.getMessage());
        }

        return fileMetadataList; // Return the list of file metadata
    }

    // Method to check if the database contains any records.
    public boolean isDatabaseEmpty() {
        String sql = "SELECT COUNT(*) AS total FROM FileMetadata";

        // Trys to connect to the database and count the records in the table.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // If the total count is 0, the database is empty.
            if (rs.next()) {
                return rs.getInt("total") == 0;
            }

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error checking if database is empty: " + e.getMessage());
        }

        return true; // Returns true if there's any error (assuming database is empty)
    }

    // Method to retrieve file path by file name
    public String getFilePathByFileName(String fileName) {
        String sql = "SELECT filePath FROM FileMetadata WHERE fileName = ?";

        // Trys to connect to the database and retrieve the file path for the given file name.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fileName); // Sets the first parameter (fileName) in the prepared statement
            try (ResultSet rs = pstmt.executeQuery()) { // Executes the query and obtain the result set

                // Checks if the result set has at least one record.
                if (rs.next()) {
                    return rs.getString("filePath"); // Returns the file path if found
                }
            }

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error retrieving file path: " + e.getMessage());
        }

        return null; // Return null if no file is found with the given name
    }
    
    // Method to delete file metadata from the database by file name.
    public void deleteFileMetadata(String fileName) {
        String sql = "DELETE FROM FileMetadata WHERE fileName = ?";

        // Trys to connect to the database and delete the file metadata.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fileName); // Sets the first parameter (fileName) in the prepared statement for deletion
            int rowsAffected = pstmt.executeUpdate(); // Executes the update and get the number of rows affected

            // Checks if any rows were deleted
            if (rowsAffected > 0) {

                // Displays a message to inform the user of success of deletion.
                System.out.println("File metadata for '" + fileName + "' was deleted successfully.");

            } else {
                
                System.out.println("No file metadata found for '" + fileName + "'.");
            }

        // Catch block to handle SQLException.
        } catch (SQLException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Error deleting file metadata: " + e.getMessage());
        }
    }

    public void closeConnection() {
        // Close the database connection if it is not null and handle any SQL exceptions
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}