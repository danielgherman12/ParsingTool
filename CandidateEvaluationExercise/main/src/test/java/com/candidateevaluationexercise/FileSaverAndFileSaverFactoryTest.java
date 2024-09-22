package com.candidateevaluationexercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FileSaverAndFileSaverFactoryTest {

    @Test
    public void testGetCSVSaver() {
        // Tests that the factory returns a non-null CSV saver.
        FileSaver saver = FileSaverFactory.getSaver("csv");
        assertNotNull("CSV saver should not be null", saver);
        assertTrue("Saver should be instance of MyCSVSaver", saver instanceof MyCSVSaver);
    }

    @Test
    public void testGetJSONSaver() {
        // Tests that the factory returns a non-null JSON saver.
        FileSaver saver = FileSaverFactory.getSaver("json");
        assertNotNull("JSON saver should not be null", saver);
        assertTrue("Saver should be instance of MyJSONSaver", saver instanceof MyJSONSaver);
    }

    @Test
    public void testGetXMLSaver() {
        // Tests that the factory returns a non-null XML saver.
        FileSaver saver = FileSaverFactory.getSaver("xml");
        assertNotNull("XML saver should not be null", saver);
        assertTrue("Saver should be instance of MyXMLSaver", saver instanceof MyXMLSaver);
    }

    @Test
    public void testGetUnsupportedSaver() {
        // Tests that requesting an unsupported format throws an exception.
        try {
            FileSaverFactory.getSaver("txt");
            fail("Expected UnsupportedOperationException for unsupported format");

        } catch (UnsupportedOperationException e) {
            assertEquals("Unsupported output format: txt", e.getMessage());
        }
    }

    @Test
    public void testGetSaverCaseInsensitive() {
        // Tests that the factory returns the correct savers regardless of case.
        FileSaver csvSaver = FileSaverFactory.getSaver("CSV");
        assertNotNull("CSV saver should not be null", csvSaver);
        assertTrue("Saver should be instance of MyCSVSaver", csvSaver instanceof MyCSVSaver);
        
        FileSaver jsonSaver = FileSaverFactory.getSaver("Json");
        assertNotNull("JSON saver should not be null", jsonSaver);
        assertTrue("Saver should be instance of MyJSONSaver", jsonSaver instanceof MyJSONSaver);
        
        FileSaver xmlSaver = FileSaverFactory.getSaver("XML");
        assertNotNull("XML saver should not be null", xmlSaver);
        assertTrue("Saver should be instance of MyXMLSaver", xmlSaver instanceof MyXMLSaver);
    }
}