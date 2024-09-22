package com.candidateevaluationexercise;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FileAccessibilityTest {
    
    @Test
    public void testValidFile() {
        // Verifies that the file is accessible.
        String filePath = "src\\test\\test_files\\csvTest.csv";
        assertTrue(FileAccesibility.isFileAccessible(filePath)); 
    }

    @Test
    public void testInvalidFile() {
        // Verifies that the file is not accessible
        String filePath = "src\\test\\test_files\\";
        assertFalse(FileAccesibility.isFileAccessible(filePath));
    }

    @Test 
    public void testNoExtension() {
        // Verifies that the file is not accessible
        String filePath = "src\\test\\test_files\\csvTest2";
        assertFalse(FileAccesibility.isFileAccessible(filePath));
    } 
}
