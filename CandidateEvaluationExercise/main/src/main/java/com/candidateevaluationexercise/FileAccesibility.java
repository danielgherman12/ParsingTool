package com.candidateevaluationexercise;

import java.io.File;



// The FileAccessibility class provides utility methods for checking the accessibility of files.
// It checks if a file exists, is readable, and has a valid file extension.
public class FileAccesibility {
    
    // Method to check if a file is accessible (exists, can be read, and has a valid extension).
    public static boolean isFileAccessible(String filePath) {

        // Creates a File object using the given file path
        File file = new File(filePath);

        // Check if the file exists, can be read, and contains an extension (with a period in the path).
        // Also, ensure that the period is not the last character (meaning a valid extension exists).
        try {
            if ((file.exists() && file.canRead()) && filePath.contains(".") && filePath.lastIndexOf('.') != filePath.length() - 1) {
                return true; // File is accessible 

            } else {
                return false; //File does not meet one or more conditions
            }

        // Handle the case where a security exception occurs (e.g., permission issues).
        } catch (SecurityException e) {

            // Displays a message to inform the user of the error.
            System.out.println("Security exception when accessing file: " + e.getMessage());

            return false; // Return false to indicate the file is not accessible
        }
    }
}
