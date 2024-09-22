package com.candidateevaluationexercise;

import java.util.*;



// Interface for saving data to a file.
// This interface defines a contract for classes that implement file saving functionality.
// This interface is implemented by specific savers (e.g., MyCSVSaver, MyJSONSaver) to handle various file formats
public interface FileSaver {

    // Method to save the provided data to a specified file path with a given file name.
    void save(List<List<String>> data, String filePath, String fileName);
}
