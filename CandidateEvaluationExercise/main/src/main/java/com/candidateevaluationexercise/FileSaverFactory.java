package com.candidateevaluationexercise;


// Factory class for creating FileSaver instances based on the specified format.
// This class provides a method to obtain the appropriate FileSaver implementation 
// based on the requested file format (CSV, JSON, XML).
public class FileSaverFactory {

     // Method to obtain a FileSaver instance based on the specified format
    public static FileSaver getSaver(String format) {
        switch (format.toLowerCase()) {
            case "csv":
                return new MyCSVSaver(); // Returns CSV saver
            case "json":
                return new MyJSONSaver(); // Returns JSON saver
            case "xml":
                return new MyXMLSaver(); // Returns XML saver
            default:
            
                // Throws an exception if the format is unsupported
                throw new UnsupportedOperationException("Unsupported output format: " + format);
        }
    }
}
