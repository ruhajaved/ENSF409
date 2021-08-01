package edu.ucalgary.ensf409;

import java.util.LinkedList;

/**
 * @author JUnit Testing | Ruha Javed, Josh Duha, Khaled Elmalawany, Steafen Rivera
 * @version 1.0
 * @since 0.1
 */

/**
 * Point of entry class; houses main.
 */
public class SupplyChainManagement {
    /**
     * Main function to run the code.
     * @param args Unused arguments from the console.
     */
    public static void main(String args[])
    {
        // Handle user input
        UserInputInterpreter user = new UserInputInterpreter();

        // Get user input through command line
        user.initiateUserInput();

        // Create the Databse Connector object
        DBLinker db = new DBLinker("jdbc:mysql://localhost/inventory", "scm", "ensf409");//The username and password have been changed to conform with the course recommendation.
        db.initializeConnection();

        // Create the ouput generator object
        OutputGen outputPrinter = new OutputGen(user.getFacultyName(), user.getContactName());

        // Create the Optimum Cost Calculator object
        OptimumCostCalculator optCostCalc = new OptimumCostCalculator(user.getNumberRequested());
        
        // Search in the database using appropriate parameters
        LinkedList<String[]> list = db.searchDB(user.getFurnitureCategory(), user.getFurnitureType());
        if(list != null){
            optCostCalc.setDBInfo(list);
            optCostCalc.assignCostToBeat();
        }                         

        // if an order can be successfully made
        if (optCostCalc.findCombination()) {
            // Extract item ID and total price
            String[] itemsID = new String[optCostCalc.getToBuy().size()];
            int i, price = 0;
            for(i = 0; i < optCostCalc.getToBuy().size(); i++) {
                itemsID[i] = optCostCalc.getToBuy().get(i)[0];
                price +=  Integer.parseInt(optCostCalc.getToBuy().get(i)[2]);
            }

            // create new filename
            outputPrinter.generateNewFileName();

            // Generate order form
            outputPrinter.generateOrderForm(user.getFurnitureType() + " " + user.getFurnitureCategory(), user.getNumberRequested(), itemsID, price);
            
            //delete items ordered from the DB
            db.deleteRows(optCostCalc.getToBuy());
            
        } else {
            // output failure message to terminal
            outputPrinter.outputError(user.getFurnitureType() + " " + user.getFurnitureCategory(), user.getNumberRequested(), db.getManufacturers(user.getFurnitureCategory()));
        }

        user.continueOrderUserInput();

        if (user.getContinueOrderFlag()) {
            // Note: String[0] is unused
            main(new String[0]);
        }

        // close connection to DB
        db.close();
    }
}