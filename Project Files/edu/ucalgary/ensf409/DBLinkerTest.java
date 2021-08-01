/**
 * @author Ruha Javed (originally), in collaboration with Khaled Elmalawany, Josh Duha, and Steafen Rivera
 * @version 1.1
 * @since 1.0
 */

 package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import java.sql.*;

/**
 * @author JUnit Testing | Ruha Javed, Josh Duha, Khaled Elmalawany, Steafen Rivera
 * @version 1.0
 * @since 0.1
 */

/**
 * Testing methods for the DB Linker class
 */
public class DBLinkerTest 
{
    @Test
    /**
     * Test constructor w/ 3 arguments - all correct
     *  and test initializeConnection - successfully
     * connecting to db.
     */
    public void testConstructorWithoutError()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        test.close();
    }

    @Test
    /**
     * Tests constructor, initializeConnection, getManufacturer with chair, and close.
     */ 
    public void testGetManufacturers00()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        ArrayList<String> manufacturers = test.getManufacturers("chair");
        test.close();
        
        ArrayList<String> expectedManufacturers = new ArrayList<String>();
        expectedManufacturers.add("Office Furnishings           587-890-4387");
        expectedManufacturers.add("Chairs R Us                  705-667-9481");
        expectedManufacturers.add("Furniture Goods              306-512-5508");
        expectedManufacturers.add("Fine Office Supplies         403-980-9876");

        assertTrue("manufacturers were not retreived correctly.",manufacturers.equals(expectedManufacturers));
    }

    @Test
    /**
     * Tests constructor, initializeConnection, getManufacturer with desk, and close.
     */
    public void testGetManufacturers01()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        ArrayList<String> manufacturers = test.getManufacturers("desk");
        test.close();
        
        ArrayList<String> expectedManufacturers = new ArrayList<String>();
        expectedManufacturers.add("Academic Desks               236-145-2542");
        expectedManufacturers.add("Office Furnishings           587-890-4387");
        expectedManufacturers.add("Furniture Goods              306-512-5508");
        expectedManufacturers.add("Fine Office Supplies         403-980-9876");

        assertTrue("manufacturers for desk were not retreived correctly.",manufacturers.equals(expectedManufacturers));
    }

    @Test
    /**
     * Tests constructor, initializeConnection, getManufacturer with filing, and close.
     */
    public void testGetManufacturers02()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        ArrayList<String> manufacturers = test.getManufacturers("filing");
        test.close();
        
        ArrayList<String> expectedManufacturers = new ArrayList<String>();
        expectedManufacturers.add("Office Furnishings           587-890-4387");
        expectedManufacturers.add("Furniture Goods              306-512-5508");
        expectedManufacturers.add("Fine Office Supplies         403-980-9876");

        assertTrue("manufacturers for filings were not retreived correctly.",manufacturers.equals(expectedManufacturers));
    }

    @Test
    /**
     * Tests constructor, initializeConnection, getManufacturer with lamp, and close.
     */
    public void testGetManufacturers03()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        ArrayList<String> manufacturers = test.getManufacturers("lamp");
        test.close();
        
        ArrayList<String> expectedManufacturers = new ArrayList<String>();
        expectedManufacturers.add("Office Furnishings           587-890-4387");
        expectedManufacturers.add("Furniture Goods              306-512-5508");
        expectedManufacturers.add("Fine Office Supplies         403-980-9876");

        assertTrue("manufacturers for lamps were not retreived correctly.",manufacturers.equals(expectedManufacturers));
    }

    @Test
    /**
     * Tests searchDB.
     */
    public void testSearchDB()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        LinkedList<String[]> actualResult = test.searchDB("chair", "Task"); // get results
        test.close();

        String[][] temp = {{"C0914","Task","50","002","N","N","Y","Y"},
                           {"C1148","Task","125","003","Y","N","Y","Y"},
                           {"C3405","Task","100","003","Y","Y","N","N"}};

        LinkedList<String[]> expectedResult = new LinkedList<String[]>(); // create expectedResult

        // fill expectedResult
        for(int i = 0; i < temp.length; i++)
        {
            expectedResult.add(temp[i]);
        }

        boolean flag = true;

        //compare expectedResult to actualResult
        for(int i = 0; i < temp.length; i++)
        {
            for(int j = 0; j < temp[i].length; j++)
            {
                String expected = expectedResult.get(i)[j];
                String actual = actualResult.get(i)[j];
                if(!expected.equals(actual)) // if a discrepency is found
                {
                    flag = false;
                    break;
                }
            }
        }
        assertTrue("searchDB was not successful.", flag);
    }

    @Test
    /**
     * Tests deleteRows w/ one argument.
     */
    public void testDeleteRows()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
        test.initializeConnection();
        test.searchDB("chair", "Mesh"); // filler needed to set lastCategory in DBLinker

        String[][] temp = {{"C9890", "Mesh", "50", "003", "N", "Y", "N", "Y"},
                           {"C0942", "Mesh", "100", "005", "Y", "N", "Y", "Y"}};

        ArrayList<String[]> toDelete = new ArrayList<String[]>(); // create list of rows to delete

        // fill toDelete
        for(int i = 0; i < temp.length; i++)
        {
            toDelete.add(temp[i]);
        }

        test.deleteRows(toDelete);

        LinkedList<String[]> actualResult = test.searchDB("chair", "Mesh"); // search for all mesh chairs

        boolean flag = true;

        // check if rows were successfully deleted
        for(int i = 0; i < actualResult.size(); i++)
        {
            for(int j = 0; j < actualResult.get(i).length; j++)
            {
                String actual = actualResult.get(i)[0];
                if(actual.equals("C9890") || actual.equals("C0942")) // if deleted items are found in searched items
                {
                    flag = false;
                    break;
                }
            }
        }
        test.close();
        
        assertTrue("deleteRows was not successful.", flag);
    }

    /**
     * Tests with system.exit(1) expected
     */
    @Rule
    // Handle System.exit() status
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    /**
     * Test constructor with invalid DBURL given.
     */
    public void testConstructorWithError()
    {
        DBLinker test = new DBLinker("jdbc:mysql://localhost/ory", "hyl", "hackathon");
        // We are expecting exit(1), wrong url given
        exit.expectSystemExitWithStatus(1);
        test.initializeConnection();
    }

    /**
     * Post-test processes.
     */
    @AfterClass
    public static void end() {
        insertChair("C9890", "Mesh", "N", "Y", "N", "Y", 50, "003");
        insertChair("C0942", "Mesh", "Y", "N", "Y", "Y", 100, "005");
    }

    /**
     * Utility function to "cleanup" after deleteRows is tested;
     * Adds a chair into the chair table in DB by querying the database
     * using a prepared statement to insert data received as function
     * parameters - this returns the # of table
     * rows effected, which is NOT used by this method.
     */
    public static void insertChair(String ID, String type, String legs, String arms,
                            String seat, String cushion, int price, String manuID)
    {
        try
        {
            // create link to db
            DBLinker db = new DBLinker("jdbc:mysql://localhost/inventory", "hyl", "hackathon");
            db.initializeConnection();

            // form query
            String query = "INSERT INTO chair (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID)";
            query = query + " VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement myStmt = db.getDbConnection().prepareStatement(query); 

            myStmt.setString(1, ID);
            myStmt.setString(2, type);
            myStmt.setString(3, legs);
            myStmt.setString(4, arms);
            myStmt.setString(5, seat);
            myStmt.setString(6, cushion);
            myStmt.setInt(7, price);
            myStmt.setString(8, manuID);

            // execute query
            myStmt.executeUpdate();

            // close any connections open
            myStmt.close();
            db.close();
        }
        catch(SQLException e)
        {
            System.out.println("insertChair failed. Something went wrong.");   
        }
    }
}