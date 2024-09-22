package com.candidateevaluationexercise;


// The FileParserFactory class is responsible for creating instances of file parsers (CSV, JSON, XML) 
// based on the file extension provided. This factory pattern helps in dynamically selecting the right parser 
// for the type of file being processed.
public class FileParserFactory {
    public static FileParser getParser(String filePath) {
        String fileExtension = getFileExtension(filePath); // Extracts file extension

        // Selects the appropriate parser based on the file extension.
        switch (fileExtension) {
            case "csv":
                return new MyCSVParser(); // Returns CSV parser
            case "json":
                return new MyJSONParser(); // Returns JSON parser
            case "xml":
                return new MyXMLParser(); // Returns XML parser
            default:
                return null; // If file format is unsportted, return null
        }
    }

    // This helper method extracts the file extension from the file path.
    // It returns the substring after the last dot (.) and converts it to lowercase.
    private static String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.'); // Finds the position of the last dot in the file name
        if (lastDotIndex > 0) {
            return filePath.substring(lastDotIndex + 1).toLowerCase(); // Extract the file extension
        } 
        return ""; // Return an empty string if no valid extension is found
    }
}
