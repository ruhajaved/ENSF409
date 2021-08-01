/**
 * @author Khaled Elmalawany (Originally,) in collaboration with Ruha Javed, Josh Duha, and Steafen Rivera
 * @version 0.1
 * @since 0.1
 */

package edu.ucalgary.ensf409;

import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter to format the date
import java.util.ArrayList;
import java.time.LocalDate; // Import the LocalDate class to pull current date
import java.io.File;  // Import the File class
import java.io.FileWriter;  // Import the FileWrite class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * This class contains the function to create the order form and outputs it in a text file.
 * This also creates a list of recommendations based on the item desired through the terminal.
 */
public class OutputGen {
    private String facultyName, contactName, date;
    private String fileName;
    private static int fileOrderNumber = 0;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    /**
     * Class constructor. Initializes the date and stores it in the class member variable.
     */
    public OutputGen() {
        LocalDate now = LocalDate.now();
        date = dtf.format(now);
    }

    /**
     * Class constructor. Assigns the date, faculaty name and contact name member variables.
     * @param facultyName String that contains the faculty name
     * @param contactName String that contains the contact name
     */
    public OutputGen(String facultyName, String contactName) {
        // Set the date
        LocalDate now = LocalDate.now();
        date = dtf.format(now);

        // Set the contact name
        this.contactName = contactName;

        // Set the faculty name
        this.facultyName = facultyName;
    }

    /**
     * Creates the order form and outputs it as a text file
     * @param requestItem String for the item requested
     * @param requestAmount int for the amount of the item requested
     * @param itemsID String array for the items ID
     * @param totalPrice int for the total price
     */
    public void generateOrderForm(String requestItem, int requestAmount, String[] itemsID, int totalPrice) {
        // Generate new file name if necessary
        if (fileName == null) {
            generateNewFileName();
        }
        // Check if facultyName and contactName have been set
        if (facultyName == null || contactName == null) {
            System.out.println("Faculty name and/or the contact name have not been set");
            return;
        }
        // Attempt to create a new file
        try {
            File myFile = new File(fileName);
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Attempt to write to file
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write("Furniture Order Form\n\n");
            myWriter.write("Faculty Name: " + facultyName + "\n");
            myWriter.write("Contact: " + contactName + "\n");
            myWriter.write("Date: " + date + "\n\n");
            myWriter.write("Original Request: " + requestItem + ", " + requestAmount + "\n\n");
            myWriter.write("Items Ordered\n");
            for (String id:itemsID) {
                myWriter.write("ID: " + id + "\n");
            }
            myWriter.write("\nTotal Price: $" + totalPrice);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs the recommendation to the terminal window
     * @param requestItem String for the item requested
     * @param recommendations String array for recommendations
     */
    public void outputRecommendations(String requestItem, String[] recommendations) {
        System.out.print("Recommendation for: " + requestItem + "\n");
        for (String recommendation: recommendations) {
            System.out.print("Recommended Item: " + recommendation + "\n");
        }
    }

    public void outputError(String requestItem, int requestAmount, ArrayList<String> manufacturerList) {
        System.out.println("User request: " +  requestItem + ", " + requestAmount);
        String s = "Output: Order cannot be fulfilled based on current inventory. Suggested manufacturers are:\n";
        for(int i = 0; i < manufacturerList.size(); i++) {
            s += manufacturerList.get(i) + "\n";
        }
        System.out.println(s);
    }

    /**
     * Generate a new file name
     */
    public void generateNewFileName() {
        fileName = String.format("orderForm%03d.txt", fileOrderNumber);
        fileOrderNumber++;
    }

    /**
     * Update the date member variable
     */
    public void updateDate() {
        LocalDate now = LocalDate.now();
        date = dtf.format(now);
    }

    // Setter methods
    /**
     * Sets the faculty name member variable
     * @param facultyName String that contains the faculty name
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * Sets the contact name member variable
     * @param contactName String that contains the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    // Getter methods
    /**
     * Gets the faculty name
     * @return String value of faculty name
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * Gets the contact name
     * @return String value of contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the date
     * @return String value of data
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the file name
     * @return String value of file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the file order number
     * @return int value of file name
     */
    public int getFileOrderNumber() {
        return fileOrderNumber;
    }
}
