package edu.ucalgary.ensf409;

import static org.junit.Assert.assertTrue;
import java.util.*;
import org.junit.*;

/**
 * @author JUnit Testing | Ruha Javed, Josh Duha, Khaled Elmalawany, Steafen Rivera
 * @version 1.0
 * @since 0.1
 */

/**
 * To simplify the unit testing, selct functions have been made public in OptimumCostCalculator.
 */
public class OptimumCostCalculatorTest {
	public static String[][] testData = {
		{"ID1","T","1","M","N","N","Y","Y"},
		{"ID2","T","2","M","N","N","Y","N"},
		{"ID3","T","3","M","Y","N","Y","Y"},
		{"ID4","T","4","M","Y","Y","N","N"},
		{"ID5","T","5","M","Y","N","N","Y"},
		{"ID6","T","6","M","N","Y","N","N"}};
	
	@Test
	/**
	 * Tests fillTable and asserts that it correctly creates the table
	 */
	public void fillTableTest() {
		//How the table is expected to look in the end:
		char[][] expected = {
				{'N','N','Y','Y'},
				{'N','N','Y','N'},
				{'Y','N','Y','Y'},
				{'Y','Y','N','N'},
				{'Y','N','N','Y'}
		};
		//Convert the testData to a LinkedList (This is how DBLinker formats the information):
		LinkedList<String[]> testList = new LinkedList<>();
		for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) {
			testList.add(OptimumCostCalculatorTest.testData[i]);
		}

		char[][] result = new char[5][4];//Make a 2D char array to store the result of the test
		OptimumCostCalculator test = new OptimumCostCalculator(1);//Instanciate the calculator (It needs an int, but this isn't used)
		test.setDBInfo(testList);//Input the test data
		test.fillTable(result);//Convert the test data into a table
		boolean testVal = true;
		for(int i = 0; i<expected.length; i++) {		//If any difference is found between the result table and the expected, then indicate that the
			for(int j = 0; j<expected[0].length;j++) {	//test failed.
				if(expected[i][j] != result[i][j]) {
					testVal = false;
				}
			}
		}
		assertTrue("The method fillTableTest did not properly fill the table", testVal);
	}
	
	@Test
	/**
	 * Tests assignCostToBeat and asserts that it is working properly
	 */
	public void assignCostToBeatTest() {
		int expected = 22;
		LinkedList<String[]> testList = new LinkedList<>();
		for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) {
			testList.add(OptimumCostCalculatorTest.testData[i]);
		}

		OptimumCostCalculator test = new OptimumCostCalculator();
		test.setDBInfo(testList);
		test.assignCostToBeat();
		assertTrue("The method assignCostToBeat did not determine the proper vale", test.getCostToBeat() == expected);
	}
	
	@Test
	/**
	 * Tests findCost and asserts that the ouput is the right cost based on an order list
	 */
	public void calculateCostTest() {
		int expected = 7;//This list is expected to cost 7
		LinkedList<String[]> testList = new LinkedList<>();//Put the test data into DBLinker format
		for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) {
			testList.add(OptimumCostCalculatorTest.testData[i]);
		}

		OptimumCostCalculator test = new OptimumCostCalculator();
		test.setDBInfo(testList);
		ArrayList<Integer> orderList = new ArrayList<Integer>();
		orderList.add(2);
		orderList.add(3);
		int cost = test.findCost(orderList);
		boolean result = cost == expected;
		assertTrue("The calculated cost is incorect", result);
	}

	@Test
    /**
	 * Test the findCombination while setting a target of 1 and assert proper combination given testList
	 */
	public void testFindCombination00() 
    {
        OptimumCostCalculator test = new OptimumCostCalculator(1);
        LinkedList<String[]> testList = new LinkedList<>();
        for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) 
        {
            testList.add(OptimumCostCalculatorTest.testData[i]);
        }
        test.setDBInfo(testList);
		test.assignCostToBeat();
        test.findCombination();
		boolean flag = false;
		if(test.getToBuy().get(0)[0].equals("ID1") && test.getToBuy().get(1)[0].equals("ID4")) {
			flag = true;
		}
		assertTrue("Failed to properly execute findCombination", flag);
    }

	@Test
    /**
	 * Test the findCombination while setting a target of 2 and assert proper combination given testList
	 */
	public void testFindCombination01() {
        OptimumCostCalculator test = new OptimumCostCalculator(2);
        LinkedList<String[]> testList = new LinkedList<>();
        for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) 
        {
            testList.add(OptimumCostCalculatorTest.testData[i]);
        }
        test.setDBInfo(testList);
		test.assignCostToBeat();
        test.findCombination();
		boolean flag = false;
		if(test.getToBuy().get(0)[0].equals("ID1") && test.getToBuy().get(1)[0].equals("ID3") && test.getToBuy().get(2)[0].equals("ID4") && test.getToBuy().get(3)[0].equals("ID6")) {
			flag = true;
		}
		assertTrue("Failed to properly execute findCombination", flag);
    }

	@Test
    /**
	 * Test the findCombination while setting a target of 5 and assert proper combination given testList
	 */
	public void testFindCombination02() {
        OptimumCostCalculator test = new OptimumCostCalculator(5);
        LinkedList<String[]> testList = new LinkedList<>();
        for(int i = 0; i<OptimumCostCalculatorTest.testData.length; i++) 
        {
            testList.add(OptimumCostCalculatorTest.testData[i]);
        }
        test.setDBInfo(testList);
		test.assignCostToBeat();
        test.findCombination();
		boolean flag = false;
		if(test.getToBuy() != null) {
			flag = true;
		}
		assertTrue("Failed to properly execute findCombination", !flag);
    }
}


