package com.candidateevaluationexercise;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyXMLSaverTest {
    private MyXMLSaver xmlSaver;
    private String testDir;

    @Before
    public void setUp() {
        xmlSaver = new MyXMLSaver();
        testDir = "src\\test\\test_files"; // Specify your test directory
        new File(testDir).mkdirs(); // Create the test directory if it doesn't exist
    }

    @Test
    public void testValidXMLSaving() throws IOException {
        String fileName = "testOutput.xml";
        List<List<String>> data = Arrays.asList(
                Arrays.asList("John Doe", "30", "john.doe@example.com"),
                Arrays.asList("Jane Smith", "25", "jane.smith@example.com")
        );

        // Save the data to an XML file
        xmlSaver.save(data, testDir + "\\", fileName);

        // Verify that the file was created
        assertTrue("XML file should be created", Files.exists(Paths.get(testDir + "\\" + fileName)));

        // Read the contents of the file to verify correctness
        String content = new String(Files.readAllBytes(Paths.get(testDir + "\\" + fileName)));

        // Expected XML content (formatted for easier reading)
        String expectedXml = "<data>"
                + "<row><column0>John Doe</column0><column1>30</column1><column2>john.doe@example.com</column2></row>"
                + "<row><column0>Jane Smith</column0><column1>25</column1><column2>jane.smith@example.com</column2></row>"
                + "</data>";

        // Check if the actual XML content contains the expected values
        assertTrue("XML content should contain expected values",
                content.contains("John Doe") &&
                content.contains("30") &&
                content.contains("john.doe@example.com") &&
                content.contains("Jane Smith") &&
                content.contains("25") &&
                content.contains("jane.smith@example.com"));

        // Clean up
        Files.delete(Paths.get(testDir + "\\" + fileName));
    }
}