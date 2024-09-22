package com.candidateevaluationexercise;

import java.util.*;

import java.io.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;



// The MyXMLParser class implements the FileParser interface, providing functionality 
// to parse XML files into a list of lists. This class uses the Java DOM parser to 
// read and extract data from the XML structure.
public class MyXMLParser implements FileParser {

    @Override
    public List<List<String>> parse(String filePath) {
        List<List<String>> result = new ArrayList<>(); // Initialize the result list
        File file = new File(filePath); // Create a File object using the provided file path

        // Check if the file is empty
        if (file.length() == 0) {
            return new ArrayList<>(); // Returns an empty list for an empty file
        }
        
        // Trys to automatically close the FileInputStream
        try (FileInputStream fis = new FileInputStream(filePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(fis);

            List<String> currentRow = new ArrayList<>(); // Initialize a list to hold the current row's data

            // Loop through the XML events.
            while (reader.hasNext()) {
                int event = reader.next(); // Move to the next event
                
                // Checks if the event is the start of an XML element.
                if (event == XMLStreamConstants.START_ELEMENT) {
                    currentRow.add(reader.getLocalName());

                // Checks if the event is character data.
                } else if (event == XMLStreamConstants.CHARACTERS) {
                    currentRow.add(reader.getText().trim());

                // Checks if the event is the end of an XML element 
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    result.add(new ArrayList<>(currentRow));
                    currentRow.clear();
                }
            }
        
        // Catch block to handle Exception.
        } catch (Exception e) {

            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while parsing the XML file. Please check the file path and format."); 

            return null; // Returns null to signify that the parsing process failed
        }
        
        return result;
    }
}
