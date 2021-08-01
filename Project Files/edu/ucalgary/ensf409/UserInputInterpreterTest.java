package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author JUnit Testing | Ruha Javed, Josh Duha, Khaled Elmalawany, Steafen Rivera
 * @version 1.0
 * @since 0.1
 */

/**
 * Testing methods for the DB Linker class
 */
public class UserInputInterpreterTest {   
    @Test
    /**
     * Testing empty constructor and set the Furniture Category
     * Determine if Furniture Category setter method is functional with Empty Constructor
     */
    public void testEmptyConstructorAndFurnitureCategory() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("desk");
        assertTrue("Failed to set UserInputInterpreter with Empty Constructor and Furniture Category setter method", uii.getFurnitureCategory().equals("desk"));
    }

    @Test
    /**
     * Testing empty constructor and set the Furniture Type
     * Determine if Furniture Type setter method is functional with Empty Constructor
     */
    public void testEmptyConstructorAndFurnitureType() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureType("desk");
        assertTrue("Failed to set UserInputInterpreter with Empty Constructor and Furniture Type setter method", uii.getFurnitureType().equals("desk"));
    }

    @Test
    /**
     * Testing empty constructor and set the Number Requested
     * Determine if Number Requested setter method is functional with Empty Constructor
     */
    public void testEmptyConstructorAndNumberRequested() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setNumberRequested(1);
        assertTrue("Failed to set UserInputInterpreter with Empty Constructor and Furniture Type setter method", uii.getNumberRequested() == 1);
    }

    @Test
    /**
     * Testing isCategoryValid with an invalid input
     */
    public void testIsCategoryValid00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("random");
        boolean flag = uii.isCategoryValid();
        assertTrue("Failed to properly determine if Category is Valid", !flag);
    }

    @Test
    /**
     * Testing isCategoryValid with a valid input
     */
    public void testIsCategoryValid01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("lamp");
        boolean flag = uii.isCategoryValid();
        assertTrue("Failed to properly determine if Category is Valid", flag);
    }

    @Test
    /**
     * Testing isTypeValid with an invalid Category input and an invalid Type inputs
     */
    public void testIsTypeValid00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("random");
        uii.setFurnitureType("random");
        boolean flag = uii.isTypeValid();
        assertTrue("Failed to properly determine if Category is Valid", !flag);
    }

    @Test
    /**
     * Testing isTypeValid with a valid Category input and an invalid Type inputs
     */
    public void testIsTypeValid01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("filing");
        uii.setFurnitureType("random");
        boolean flag = uii.isTypeValid();
        assertTrue("Failed to properly determine if Category is Valid", !flag);
    }

    @Test
    /**
     * Testing isTypeValid with an invalid Category input and a valid Type inputs
     */
    public void testIsTypeValid02() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("random");
        uii.setFurnitureType("small");
        boolean flag = uii.isTypeValid();
        assertTrue("Failed to properly determine if Type is Valid", !flag);
    }

    @Test
    /**
     * Testing isTypeValid with a valid Category input and a valid Type inputs
     */
    public void testIsTypeValid03() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFurnitureCategory("filing");
        uii.setFurnitureType("small");
        boolean flag = uii.isTypeValid();
        assertTrue("Failed to properly determine if Type is Valid", flag);
    }

    @Test
    /**
     * Testing isStringNum with an invalid input
     */
    public void testIsStringNum00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("random");
        boolean flag = uii.isStringNum();
        assertTrue("Failed to properly determine if String is a Digit", !flag);
    }

    @Test
    /**
     * Testing isStringNum with a valid input
     */
    public void testIsStringNum01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("7");
        boolean flag = uii.isStringNum();
        assertTrue("Failed to properly determine if String is a Digit", flag);
    }

    @Test
    /**
     * Testing isNumValid with an invalid input of 0
     */
    public void testIsNumValid00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("0");
        boolean flag = uii.isNumValid();
        assertTrue("Failed to properly determine if Num is Valid", !flag);
    }

    @Test
    /**
     * Testing isNumValid with an invalid input of a negative value
     */
    public void testIsNumValid01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("-1");
        boolean flag = uii.isNumValid();
        assertTrue("Failed to properly determine if Num is Valid", !flag);
    }

    @Test
    /**
     * Testing isNumValid with a valid digit
     */
    public void testIsNumValid02() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("1");
        boolean flag = uii.isNumValid();
        assertTrue("Failed to properly determine if Num is Valid", flag);
    }

    @Test
    /**
     * Testing isNumValid with a valid number of two or more digits
     */
    public void testIsNumValid03() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setStringNumberRequested("100");
        boolean flag = uii.isNumValid();
        assertTrue("Failed to properly determine if Num is Valid", flag);
    }

    @Test
    /**
     * Testing isYNValidContinue with an invalid input
     */
    public void testIsYNValidContinue() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFlag("random");
        boolean flag = uii.isYNValidContinue();
        assertTrue("Failed to properly determine if Y/N input is Valid", !flag);
    }

    @Test
    /**
     * Testing isYNValidContinue with a valid 'y' input
     */
    public void testIsYNValidContinue00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFlag("y");
        boolean flag = uii.isYNValidContinue();
        assertTrue("Failed to properly determine if Y/N lowercase input is Valid", flag);
    }

    @Test
    /**
     * Testing isYNValidContinue with a valid 'n' input
     */
    public void testIsYNValidContinue01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFlag("n");
        boolean flag = uii.isYNValidContinue();
        assertTrue("Failed to properly determine if Y/N lowercase input is Valid", flag);
    }

    @Test
    /**
     * Testing isYNValidContinue to set continueOrderFlag appropriately with a 'y' input
     */
    public void testIsYNValidSetContinueOrderFlag00() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFlag("y");
        uii.isYNValidContinue();
        assertTrue("Failed to properly set newOrderFlag with Y/N input", uii.getContinueOrderFlag());
    }

    @Test
    /**
     * Testing isYNValidContinue to set continueOrderFlag appropriately with a 'n' input
     */
    public void testIsYNValidSetContinueOrderFlag01() {
        UserInputInterpreter uii = new UserInputInterpreter();
        uii.setFlag("n");
        uii.isYNValidContinue();
        assertTrue("Failed to properly set newOrderFlag with Y/N input", !uii.getContinueOrderFlag());
    }
}