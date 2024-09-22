package com.candidateevaluationexercise;

import java.util.*;

// The ParsingTool class serves as the main entry point for the application.
// It orchestrates the parsing of different file types (CSV, JSON, XML) and 
// provides functionality to save the parsed data in various formats, either 
// in the database or on the users PC.
public class ParsingTool {
    public static void main( String[] args ) {
        // Creates a Scanner object to read input from the standard input stream (console)
        // and initizalizes the database.
        Scanner scanner = new Scanner(System.in);
        DataBaseHelper dbhelper = new DataBaseHelper();
    
        // Prompts the user to choose between accessing a file from the database or their computer.
        System.out.print("Would you like to access a file from the database or your computer? (db/pc): ");
        String dbChoice = scanner.nextLine().toLowerCase();
        
        String inputfilePath = null; // Initialize variable to store the file path

        // If the user chooses to access a file from the database.
        if (dbChoice.equals("db")) {

            // Checks if the database is empty
            if (dbhelper.isDatabaseEmpty()) {
                System.out.println("No files found in the database. Proceeding with file access from your PC.");

            } else {
                // If there are files in the database, list them.
                System.out.println("Files found in the database:");
                List<String> fileMetadataList = dbhelper.retrieveFileMetadata();

                // Displays the list of files found in the database.
                for (String fileMetadata : fileMetadataList) {
                    System.out.println(fileMetadata);
                }

                // Prompts the user to choose a file by its name
                System.out.print("Choose a file from the above list (enter the file name): ");
                String chosenFileName = scanner.nextLine();

                // Retrieve the file path based on the chosen file name
                inputfilePath = dbhelper.getFilePathByFileName(chosenFileName);
                System.out.println(inputfilePath);

                // If the chosen file is not found, fall back to PC access
                if (inputfilePath == null) {
                    System.out.println("File not found in the database. Proceeding with file access from your PC.");

                } else {
                    // If the file is found in the database, notify the user
                    System.out.println("Retrieving file from database: " + chosenFileName);
                }
            }
        
        } 

        // If the input file path wasn't set from the database, prompt the user to provide it from the computer
        if (inputfilePath == null) {
            // Asks for user to enter path to file
            System.out.print("Enter the input file path (csv/json/xml): ");
            inputfilePath = scanner.nextLine();
            System.out.println(inputfilePath);

            // Runs isFileAccessible method in the FileAccesibility.java class to check if the file exists and can be accessed
            if (!FileAccesibility.isFileAccessible(inputfilePath)) {

                // If the file is not found or is inaccessible, prints an error message to the user
                System.out.println("The specified file could not be found or accessed. Please check the file path and try again.");

                // Closes the scanner object to free resources
                scanner.close();

                // Return from the method, halting further execution since the file is not accessible
                return;
            }
        }
        

        

        // Uses the FileParserFactory to obtain the appropriate parser instance based on the file extension
        // The getParser method determines whether the input file is a CSV, JSON, or XML file and returns the corresponding parser object
        System.out.println(inputfilePath);
        FileParser parser = FileParserFactory.getParser(inputfilePath);
        
        // Checks if the parser is null, indicating that the provided file format is unsupported.
        // If null, it informs the user and terminates the program.
        if (parser == null) {
            System.out.println("Unsupported file format. Please use a valid file format: csv, json, or xml.");
            scanner.close();

            return;
        }

        // Parse the input file and store the result in 'parsedData'.
        // The 'parser' is an instance of a specific file parser (CSV, JSON, XML) created by the FileParserFactory.
        // 'parse' method returns a List of List<String> representing the rows and columns of the parsed data.
        List<List<String>> parsedData = parser.parse(inputfilePath);

        // If 'parsedData' is not null (i.e., file was successfully parsed), print the parsed data.
        if (parsedData != null) {
            System.out.println("Parsed data:");

            // Iterate over each row (List<String>) in 'parsedData' and print it.
            for (List<String> row : parsedData) {
                System.out.println(row);
            }
        } else {
            scanner.close();
            return;
            
        }
            
        // Initialize 'outputFormat' as an empty string to prompt the user to input the output format.
        // The loop will continue until the user inputs a valid file type (csv, json, xml).
        String outputFormat = "";
        while (!isValidFileType(outputFormat)) {

            // Prompt the user for the output format.
            System.out.print("Enter the output format (csv/json/xml): ");
            outputFormat = scanner.nextLine().toLowerCase();

            // If the input is invalid, notify the user and repeat the loop.
            if (!isValidFileType(outputFormat)) {
                System.out.println("Invalid file type entered. Please enter a valid format (csv/json/xml).");
            }
        }

        // Asks the user if they want to save the file to the database or the filesystem.
        System.out.print("Do you want to save the file to the database or your computer? (db/pc): ");
        String saveChoice = scanner.nextLine().toLowerCase();

        // If the user chooses to save to the database
            if (saveChoice.equals("db")) {
                System.out.print("Enter the file name to save in the database: ");
                String fileName = scanner.nextLine();

                // Determines the file type based on the entered file name and output format.
                String fileType = getFileType(fileName + "." + outputFormat);

                String dbLocation = "src\\test\\db_files\\"; // Defines the database location for saved files

                // Creates a FileSaver instance based on the output format and save the parsed data to the database location
                FileSaver saver = FileSaverFactory.getSaver(outputFormat);
                saver.save(parsedData, dbLocation , fileType);

                fileType = outputFormat; // Sets the file type to the current output format

                // Save the parsed data to the database
                dbhelper.saveFileMetadata(inputfilePath, fileName, fileType);

                // Informs user the metadata was saved successfully
                System.out.println("File metadata saved to database successfully.");

            // If the user chooses to save to their computer
            } else if (saveChoice.equals("pc")) {
                System.out.print("Enter the output file path: ");
                String outputFilePath = scanner.nextLine() + '\\'; // Gets the desired output file path

                System.out.print("Enter file name: ");
                String outputFileName = scanner.nextLine() + '.' + outputFormat; // Appends the file format to the entered file name

                // Use the FileSaverFactory to create a specific FileSaver instance
                FileSaver saver = FileSaverFactory.getSaver(outputFormat);
                saver.save(parsedData, outputFilePath, outputFileName);

                // Notify the user that the file was saved successfully
                System.out.println("File saved to computer successfully.");

            } else {
                // Handle invalid choices
                System.out.println("Invalid choice. Please enter 'db' or 'pc'.");
            }

            scanner.close(); // Closes the scanner object to free resources
        }
        
        
    

    // This method checks if the given file type is one of the accepted formats: "csv", "json", or "xml".
    private static boolean isValidFileType(String fileType) {

        // Create a list of valid file types.
        List<String> validFileTypes = Arrays.asList("csv", "json", "xml");

        // Check if the input file type exists in the list of valid file types and return the result.
        return validFileTypes.contains(fileType);
    }

    // A helper method to get the file type from the file name (file extension)s
    private static String getFileType(String fileName) {

        // Find the last occurrence of the '.' character in the file names
        int dotIndex = fileName.lastIndexOf('.');

        // If a valid dot is found and it's not the last character, return the substring as the file type.
        return (dotIndex != -1 && dotIndex < fileName.length() - 1) ? fileName.substring(dotIndex + 1) : "Unknown";
    }
}   
    

