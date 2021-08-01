package edu.ucalgary.ensf409;

import java.util.Scanner;
/**
 * @author Steafen Rivera (Originally,) in collaboration with Ruha Javed, Josh Duha, and Khaled Elmalawany
 * @version 1.2
 * @since 0.1
 */

/**
 * Class for interpretting user input.
 */
public class UserInputInterpreter {
    // Class member variables
    private String furnitureCategory; // String to hold furnitureCategory.
    private String furnitureType; // String to hold furnitureType.
    private String facultyName; // String to hold facultyName.
    private String contactName; // String to hold contactName.
    private int numberRequested; // Int to hold numberRequested.
    private boolean continueOrderFlag; // Booleans for newOrderFlag and continueOrderFlag.
    private Scanner input = new Scanner(System.in); // scanner that can be used throughout class.
    private String flag; // Flag to hold y or n value.
    private String stringNumberRequested;   // String to hold amount requested

    /**
     * Main class constructor
     */
    public UserInputInterpreter() {}

    //setter and getter for furniture category 
    /**
     * Method to set the furniture category class member
     * @param furnitureCategory String value of furniture category
     */
    public void setFurnitureCategory(String furnitureCategory){
        this.furnitureCategory = furnitureCategory;
    }
    
    /**
     * Method to return furniture category class member
     * @return String value of furniture category
     */
    public String getFurnitureCategory(){
        return this.furnitureCategory;
    }

    // setter and getter for furniture type
    /**
     * Method to set the furniture type class member
     * @param furnitureType String value of furniture type
     */
    public void setFurnitureType(String furnitureType){
        this.furnitureType = furnitureType;
    }

    /**
     * Method to return furniture type class member
     * @return String value of furniture type
     */
    public String getFurnitureType(){
        return this.furnitureType;
    }

    // setter and getter for number requested
    /**
     * Method to set the Number Requested class member
     * @param furnitureType int value of Number Requested
     */
    public void setNumberRequested(int numberRequested){
        this.numberRequested = numberRequested;
    }

    /**
     * Method to return Number Requested class member
     * @return int value of Number Requested
     */
    public int getNumberRequested(){
        return this.numberRequested;
    }

    //setter for stringNumberRequested
    /**
     * Method to set the stringNumberRequested class member
     * @param stringNumberRequested String value of Number Requested
     */
    public void setStringNumberRequested(String stringNumberRequested){
        this.stringNumberRequested = stringNumberRequested;
    }

    //setter for flag
    /**
     * Method to set the flag class member
     * @param flag String value of flag
     */
    public void setFlag(String flag){
        this.flag = flag;
    }

    // Getters for facultyName, ContactName, newOrderFlag, ContinueOrderFlag
    /**
     * Method to get the current faculty name class member
     * @return String value of faculty name
     */
    public String getFacultyName(){
        return this.facultyName;
    }

    /**
     * Method to get the current contact name class member
     * @return String value of contact name
     */
    public String getContactName(){
        return this.contactName;
    }

    /**
     * Method to get the current continue order flag
     * @return boolean value of continue order flag
     */
    public boolean getContinueOrderFlag(){
        return this.continueOrderFlag;
    }

    /**
     * Checks if the category is valid, prints a message and exits if not.
     * If an invalid input is passed, reprompt user to enter in again.
     */
    public boolean isCategoryValid(){
        if((this.furnitureCategory.equals("chair")) || (this.furnitureCategory.equals("lamp")) || (this.furnitureCategory.equals("desk")) || (this.furnitureCategory.equals("filing"))){ 
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks if the type is valid, and if that type is valid for the chose category, prints a message and exits if not.
     * If an invalid input is passed, reprompt user to enter in again.
     */
    public boolean isTypeValid(){
        boolean isvalid = false;
        //lamp check
        if(this.furnitureType.equals("desk") || this.furnitureType.equals("swing arm") || this.furnitureType.equals("study")){
            if(this.furnitureCategory.equals("lamp")) {
                isvalid = true;
            }
        }
        
        // filing check
        else if(this.furnitureType.equals("small") || this.furnitureType.equals("medium") || this.furnitureType.equals("large")){
            if(this.furnitureCategory.equals("filing")){
                isvalid = true;
            }
        }

        // desk check
        else if(this.furnitureType.equals("standing") || this.furnitureType.equals("adjustable") || this.furnitureType.equals("traditional")){
            if(this.furnitureCategory.equals("desk")){
                isvalid = true;
            }        
        }
        // chair check
        else if(this.furnitureType.equals("task") || this.furnitureType.equals("kneeling") || this.furnitureType.equals("executive") || this.furnitureType.equals("ergonomic") || this.furnitureType.equals("mesh")){
            if(this.furnitureCategory.equals("chair")){
                isvalid = true;
            }   
        }
        // if not invalid type
        else{
            isvalid = false;        
        }
        return isvalid;
    }

    /**
     * Checks if the number passed is valid (greater than 0.) If an invalid input is passed, reprompt user to enter in again.
     */
    public boolean isNumValid(){

        if(isStringNum() == false) {
            return false;
        }

        this.numberRequested = Integer.valueOf(this.stringNumberRequested);

        if(this.numberRequested < 1){
            return false;
        }
        return true;
    }

    /**
     * Checks if the String is an integer.
     * @return True if it is an in int, false if its not.
     */
    public boolean isStringNum(){
        if (this.stringNumberRequested.matches("[0-9]+")){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks if the YN value passsed is valid (y or n). If an invalid input is passed, reprompt user to enter in again.
     * Sets the continue order flag appropriately
     */
    public boolean isYNValidContinue(){
        if(this.flag.equals("y")){
            this.continueOrderFlag = true;
            return true;
        }
        else if(this.flag.equals("n")){
            this.continueOrderFlag = false;
            input.close();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * userInput prompts the user to input a category, type, and number requested and checks if theyre valid to enter in again.
     */
    public void initiateUserInput() {
        String in0, in1, in2, in3, in5;
        
        System.out.println("Enter your faculty name:");
        in0 = input.nextLine();
        this.facultyName = in0.toLowerCase().strip();

        System.out.println("Enter your contact name:");
        in3 = input.nextLine();
        this.contactName = in3.toLowerCase().strip();

        System.out.println("Enter a furniture category:");
        in1 = input.nextLine();
        this.furnitureCategory = in1.toLowerCase().strip();
        while(isCategoryValid()==false){
            System.out.println("Invalid category. Please enter valid category:");
            String in = input.nextLine();
            this.furnitureCategory = in.toLowerCase().strip();
        }

        System.out.println("Enter a furniture type:");
        in2 = input.nextLine();
        this.furnitureType = in2.toLowerCase().strip();
        while(isTypeValid() == false){
            System.out.println("Invalid type. Please enter a valid type:");
            String in = input.nextLine();
            this.furnitureType = in.toLowerCase().strip();
        };
        
        System.out.println("Enter a requested number:");
        in5 = input.nextLine();
        this.stringNumberRequested = in5.toLowerCase().strip();
        while(isNumValid() == false){
            System.out.println("Invalid number. Please enter a valid number:");
            this.stringNumberRequested = input.nextLine();
        }
        
        this.numberRequested = Integer.valueOf(this.stringNumberRequested);
    }

    /**
     * Method to prompt the user about continuing to submit another order
     */
    public void continueOrderUserInput() {
        System.out.println("Would you like to submit another order? (Y/N)");
        String in0 = input.nextLine();
        flag = in0.toLowerCase().strip();
        while(isYNValidContinue() == false){
            System.out.println("Invalid response. Please enter y or n:");
            String in = input.nextLine();
            this.flag = in.toLowerCase().strip();
        }
    }
}