/**
 * @author Ruha Javed (originally), in collaboration with Khaled Elmalawany, Josh Duha, and Steafen Rivera
 * @version 1.2
 * @since 1.0
 */

 package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Program's connection the the inventory DB;
 * reads or updates the DB.
 * 
 * IMPORTANT NOTES:
 * Program is TERMINATED if connection cannot be established.
 */
public class DBLinker
{
    private final String DBURL; //store the database url information
    private final String USERNAME; //store the user's account username
    private final String PASSWORD; //store the user's account password
    private String lastCategory; // last category searcged up by searchDB()
    private Connection dbConnection; // connection to DB
    private ResultSet results; // carries results from any queries

    /**
     * Constructor.
     */
    public DBLinker(String dburl, String username, String password)
    {
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /**
     * Getter for DBURL.
     * @return returns DBURL.
     */
    public String getDburl()
    {
        return this.DBURL;
    }

    /**
     * Getter for USERNAME.
     * @return returns USERNAME.
     */
    public String getUsername()
    {
        return this.USERNAME;
    }

    /**
     * Getter for PASSWORD.
     * @return returns PASSWORD.
     */
    public String getPassword()
    {
        return this.PASSWORD;
    }

    /**
     * Getter for dbConnection.
     * @return returns dbConnection.
     */
    public Connection getDbConnection()
    {
        return this.dbConnection;
    }

    /**
     * Used to create connection to database.
     */
    public void initializeConnection()
    {
        try
        {
            dbConnection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        }
        catch(SQLException e)
        {
            System.out.println("Failed to create connection to database. Terminating.");
            System.exit(1);
        }
    }

    /**
     * Searches for any items that meet the category and type requirements; uses the database
     * to query for all data in the table specified by category and receives a ResultSet object 
     * after executing the query. The method then manipulates the order of the results and 
     * returns as specified below.
     * @param category table in DB to look at; assuming it is valid.
     * @param type type of items wanted in table category; assuming it is valid.
     * @return returns a LinkedList of string arrays, where each array is a given row in the db,
     broken down and split into strings according to the table's columns; Note that the first 
     * 4 strings in each array are ALWAYS ID, Type, Price, and ManuID.
     */
    public LinkedList<String[]> searchDB(String category, String type)
    {
        // set last category
        this.lastCategory = category;

        // make linked list to store what's returned overall
        LinkedList<String[]> data = new LinkedList<String[]>();

        try
        {
            // select all from the table which meets the type constraint
            Statement myStmt = dbConnection.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + category + " WHERE Type = '" + type + "'");

            // get metadata from results
            ResultSetMetaData resultsMetaData = results.getMetaData();

            // get # of cols in table
            int numberOfColumns = resultsMetaData.getColumnCount();

            // store column labels in the order that we want to store/return the data
            ArrayList<String> columnLabels = new ArrayList<String>();

            // add column labels that we KNOW exist at the beginning
            columnLabels.add("ID");
            columnLabels.add("Type");
            columnLabels.add("Price");
            columnLabels.add("ManuID");

            // fill columnLabels with any remaining column names in table
            for (int i = 3; i <= numberOfColumns - 2; i++)  // Note, column numbering starts as 1, NOT 0
            {
                String columnLabel = resultsMetaData.getColumnName(i);
                columnLabels.add(columnLabel);
            }

            //finally iterate through resultSet object to put together data we want
            while(results.next())
            {
                String[] temp = new String[columnLabels.size()]; // create a string[] to hold parsed row data
                for(int i = 0; i < columnLabels.size(); i++)     // fill string[]
                {
                    temp[i] = results.getString(columnLabels.get(i));
                }
                data.add(temp);                                     // add parsed row to list
            }
           myStmt.close();                                         // close statement regardless
           results.close();                                        // close ResultSet object
        }
        catch(SQLException e)
        {
            System.out.println("searchDb failed. Something went wrong.");
        }
        return data;
    }

    /**
     * Deletes item in table category with ID provided; queries the database
     * using a prepared statement to now DELETE data associated with a particular ID
     * in a particular table, which are passed in as function parameters
     * - this returns the # of table rows effected, which is NOT used by this method.
     * @param category table name to delete from; assuming this is valid.
     * @param itemID ID of item to delete; assuming this is valid.
     */
    private void deleteItem(String category, String itemID)
    {
        try
        {
            String query = "DELETE FROM " + category + " WHERE ID = '" + itemID + "'";
            PreparedStatement myStmt = dbConnection.prepareStatement(query);

            myStmt.executeUpdate();

            myStmt.close();
        }
        catch(SQLException e)
        {
            System.out.println("deleteItem failed. Something went wrong.");  
        }
    }

    /**
     * Added option to automatically choose the category
     * that was last used to read from the database with;
     * otherwise identical behavior to 
     * deleteRows(ArrayList>String[]> orderList, String category).
     * @param orderList The list of items to be removed from the database.
     */
    public void deleteRows(ArrayList<String[]> orderList){
        deleteRows(orderList, this.lastCategory);
    }

    /**
     * This function takes an orderlist provided by the calculator class, 
     * and removes the appropriate rows from the database.
     * @param orderList A list of the items that are being ordered.
     * @param category The category that is to be searched for the items.
     */
    private void deleteRows(ArrayList<String[]> orderList, String category){
        for(int i = 0; i<orderList.size(); i++){    //For each element of the list, try to remove it from the database
            deleteItem(category, orderList.get(i)[0]);
        }
    }

    /**
     * Gets a list of manufacturer names stored in the db; queries the database
     * using a statement to now select data in the manufacturer table, which is
     * then used by the method to extract manufacturer names.
     * @return an ArrayList of manufacturer names, as Strings.
     */
    public ArrayList<String> getManufacturers(String category)
    {
        ArrayList<String> temp = new ArrayList<String>();

        try
        {
            Statement myStmt = dbConnection.createStatement();
            results = myStmt.executeQuery("SELECT * FROM manufacturer"); // create query
            while(results.next())
            {
                temp.add(String.format("%-25s %15s", results.getString("Name"), results.getString("Phone")));
            }
            myStmt.close(); // close open statement
            results.close(); // close ResultSet object
        }
        catch(SQLException e)
        {
            System.out.println("getManufacturers failed. Something went wrong.");
        }
        if (category.equals("chair")) {
            temp.remove("Academic Desks               236-145-2542");
        } else if (category.equals("desk")) {
            temp.remove("Chairs R Us                  705-667-9481");
        } else {
            temp.remove("Academic Desks               236-145-2542");
            temp.remove("Chairs R Us                  705-667-9481");
        }
        return temp; // return what's found, in the format specified
    }

    /**
     * Used to close the connection to the database.
     */
    public void close() 
    {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection.");
        }
    }
}
