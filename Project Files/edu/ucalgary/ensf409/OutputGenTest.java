package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.*;

/**
 * @author JUnit Testing | Ruha Javed, Josh Duha, Khaled Elmalawany, Steafen Rivera
 * @version 1.0
 * @since 0.1
 */

/**
 * This class tests the various methods implemented in the OutputGen 
 */
public class OutputGenTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();   // Allows storage of texts in System.out.print

    @Test
    /**
     * Test the empty constructor and test by asserting that the getDate class member has been modified
     */
    public void testEmptyConstructor() {
        OutputGen og = new OutputGen();
        boolean testFlag = false;
        if (og.getDate() != null) {
            testFlag = true;
        }
        assertTrue("Failed to initialize OutputGen with Empty Constructor", testFlag);
    }

    @Test
    /**
     * Test the empty constructor and set the Faculty Name as well as the Contact Name
     * Assert testing for correct facultyName and contactName class members
     */
    public void testEmptyConstructor2() {
        OutputGen og = new OutputGen();
        og.setFacultyName("Calgary");
        og.setContactName("Khaled");
        boolean testFlag = false;
        if (og.getFacultyName().equals("Calgary") && og.getContactName().equals("Khaled") && og.getDate() != null) {
            testFlag = true;
        }
        assertTrue("Failed to initialize OutputGen with Empty Constructor 2", testFlag);
    }

    @Test
    /**
     * Test Constructor with faculty name and contact name
     */
    public void testConstructor() {
        OutputGen og = new OutputGen("Calgary", "Khaled");
        boolean testFlag = false;
        if (og.getFacultyName().equals("Calgary") && og.getContactName().equals("Khaled") && og.getDate() != null) {
            testFlag = true;
        }
        assertTrue("Failed to initialize OutputGen with Constructor", testFlag);
    }

    @Test
    /**
     * Test the generation of the order form
     */
    public void testGenerateOrderForm() {
        OutputGen og = new OutputGen("Calgary", "Khaled");
        String[] items = {"C9890", "C0942"};
        String data = "";
        og.generateOrderForm("mesh chair", 1, items, 150);
        try {
            File myObj = new File(og.getFileName());
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
                data += "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String expected = "Furniture Order Form\n\nFaculty Name: Calgary\nContact: Khaled\nDate: " + og.getDate() + "\n\nOriginal Request: mesh chair, 1\n\nItems Ordered\nID: C9890\nID: C0942\n\nTotal Price: $150\n";
        assertTrue("Failed to properly generate order form", data.equals(expected));
    }

    @Test
    /**
     * Test the manufacturer output recommendation method by printing to a local stream
     */
    public void testOutputRecommendation() {
        OutputGen og = new OutputGen();
        String[] recommendations = {"C9890", "C0942"};

        // Set System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStreamCaptor));
        // Print to the outputStreamCaptor (after changing ouput of System.out.println())
        og.outputRecommendations("mesh chair", recommendations);
        // Set System.out back to normal
        System.setOut(standardOut);

        String expected = "Recommendation for: mesh chair\nRecommended Item: C9890\nRecommended Item: C0942\n";
        assertTrue("Failed to properly output the recommendations", outputStreamCaptor.toString().equals(expected));
    }

    @Test
    /**
     * Test the generation of a new file name
     */
    public void testGenerateNewFileName() {
        OutputGen og = new OutputGen();
        og.generateNewFileName();
        og.generateNewFileName();
        boolean flag = false;
        if (og.getFileOrderNumber() > 0) {
            flag = true;
        }
        assertTrue("Failed to properly generate a new file", flag);
    }
}
