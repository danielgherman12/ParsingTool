package com.candidateevaluationexercise;

import java.util.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

// The MyXMLSaver class implements the FileSaver interface, providing functionality 
// to save data as an XML file. This class uses the Java DOM API to create and write 
// XML documents based on the provided list of lists.
public class MyXMLSaver implements FileSaver{

    @Override
    public void save(List<List<String>> data, String filePath, String fileName) {
        try {
            // Creates a DocumentBuilderFactory and DocumentBuilder to construct the XML document.
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // Creates a new XML document using the DocumentBuilder.
            Document doc = docBuilder.newDocument();

            // Creates the root element named "data" and append it to the document.
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);
            
            // Iterates over each list of strings (each row) in the data.
            for (List<String> row : data) {

                // Creates a new XML elemnt name "row" for the current row.
                Element rowElement = doc.createElement("row"); 
                rootElement.appendChild(rowElement); // Appends the row element to the root element
                
                // Iterates over each string in the current row.
                for (int i = 0; i < row.size(); i++) {

                    // Creates a new XML element named "column" followed by the index (e.g., column0, column1).
                    Element field = doc.createElement("column" + i);

                    field.setTextContent(row.get(i)); // Sets the text content of the field element to the corresponding value in the row
                    rowElement.appendChild(field); // Appends the field element to the current row element
                }
            }
            
            // Creates a TransformerFactory and Transformer for converting the DOM object to XML.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Creates a DOMSource to represent the XML document as a source for transformation.
            DOMSource source = new DOMSource(doc);

            // Creates a StreamResult to specify the output file where the XML will be saved.
            StreamResult result = new StreamResult(new File(filePath + fileName));
            
            transformer.transform(source, result); // Transforms the DOM object into the specified file
            
            // Informs the user that the data has been saved successfully.
            System.out.println("Data saved as XML successfully");

        // Catch block to handle both ParserConfigurationException and TransformerException.
        } catch (ParserConfigurationException | TransformerException e) {

            // Displays a message to inform the user of the error.
            System.out.println("An error occurred while saving the data as XML. Please check the file path and try again.");
        }
    }
}
