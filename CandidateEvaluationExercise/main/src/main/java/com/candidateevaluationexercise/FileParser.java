package com.candidateevaluationexercise;

import java.util.*;




// The FileParser interface defines a contract for parsing files of different formats (CSV, JSON, XML, etc.).
// It declares a method 'parse' which accepts a file path and returns a List of Lists containing the parsed data.
// This interface is implemented by specific parsers (e.g., MyCSVParser, MyJSONParser) to handle various file formats
public interface FileParser {

    // Method to parse a file at the specified path and returns the data as a list of lists.
    List<List<String>> parse(String filePath);

}
