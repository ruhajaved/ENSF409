/**
 * @author Joshua Duha (Originally,) in collaboration with Ruha Javed, Khaled Elmalawany, and Steafen Rivera
 * @version 1.2
 * @since 0.1
 */

package edu.ucalgary.ensf409;
import java.util.*;

/**
 * This class contains the main functions of this assignment. It uses the informaiton gathered by
 * the input class to instruct the actions of the database class and output class. Based on the output
 * from the database, this class determines the best combination of furnature to purchase (if such a 
 * combination exists.)
 */
public class OptimumCostCalculator {
    //The other significant objects utilized by this class:
    private int costToBeat;//This is the cost of the current best combination of items to fill the order.
    private ArrayList<String[]> toBuy;//This will be a list of the items we want to order. it is in the format:
                                      // [0]:{"ID0","Type0","Price0","ManuID0"}
                                      // [1]:{"ID1","Type1","Price1","ManuID1"}
                                      // ...
    private LinkedList<String[]> dbInfo;//This is a list of the information that DBLinker got from the database. In the format:
                                        // [0]:{"ID0","Type0","Price0","ManuID0"}
                                        // [1]:{"ID1","Type1","Price1","ManuID1"}
                                        // ...
    private int targetNumber;//The number of items we are trying to produce.

    /**
     * Empty constructor (mainly for testing purposes)
     */
    public OptimumCostCalculator() {}

    /**
     * Constructor accepts the target value requested
     * @param target int value of amount of material requested
     */
    public OptimumCostCalculator(int target) {
        this.targetNumber = target;
    }

    /**
     * Setter method for {@code dbInfo} member variable
     * @param list LinkedList<String[]> of the value to store in dbInfo
     */
    public void setDBInfo(LinkedList<String[]> list) {
        this.dbInfo = list;
    }

    /**
     * Getter method for {@code costToBeat} member varable
     * @return int value of the cost to beat
     */
    public int getCostToBeat() {
        return this.costToBeat;
    }

    /**
     * Setter method for {@code toBuy} member variable
     * @return ArrayList<String[]> for the toBuy member
     */
    public ArrayList<String[]> getToBuy() {
        return this.toBuy;
    }

    /**
     * Comes up with the first impossible cost. 
     * Makes the cost to beat a number that is larger than the cost of buying every item in dbInfo.
     */
    public void assignCostToBeat() {
        costToBeat = 0;
        for(int i = 0; i<dbInfo.size(); i++){
            costToBeat += Integer.parseInt(dbInfo.get(i)[2]);
        }
        costToBeat += 1;
    }

    /**
     * Looks for an optimal combination of buys to service the request. If this function succeeds, it will
     * update the orderlist with the best combination of items to buy, and costToBeat will be the cost of that combo.
     * @return true if an acceptable combination was found, false if no such combination exists.
     */
    public boolean findCombination(){
        int failcase = costToBeat; //Should be an impossible cost. This is important because *Any* combination that fills the order will
                                   //be a lower cost than this.
        int[] fields = new int[dbInfo.get(0).length-4];//This is a vector that keeps track of how many of each part we currently have.
                                                       //Since at this point, we have not considered any items, this contains only 0s
        char[][] table = new char[dbInfo.size()][fields.length];//Generate a table to represent what parts each item can provide.
        fillTable(table);//This table contains the information on each item and what parts they can supply. If one item has [Y N N Y] then the 
                         //table will have a row that contains [Y N N Y]
        ArrayList<Integer> orderList = new ArrayList<Integer>();//A list of the indices of the items we want to order. The indices refer to the
                                                                //items' positions in the dbInfo list.
        findCombination(table,fields,orderList);
        if(costToBeat == failcase){//If findCombination was unable to do better than the impossible failcase:
            //This means it coudln't find a combination that could satisfy
            return false;
        }
        return true;
    }

    /**
     * The most important method of this class. Recursively finds each possible combination that fulfils the request and compares the cost
     * to the "current winner." Should the current combo do better than the winner, replace the winner with this combo. It is important to
     * know that this function directly manipulates the input variables (since they are passed by reference.)
     * @param table This is a table of all of the items that may be used to fill the order. Each individual item is represented as a row of 
     * 'Y's and 'N's
     * @param fields An integer array that keeps track of how many of each part we have. Gets updated as we add items to the order list.
     * @param orderList The list of items we are planning on ordering to fulfil the request.
     */
    private void findCombination(char[][] table, int[] fields, ArrayList<Integer> orderList){ 
        if(isFull(fields)){//If we have enough of each part to fill the order:
            int cost = findCost(orderList);//find the cost of this combination
            if(cost < this.costToBeat){//if this cost is lower than the current best: (This is guaranteed to be the case if this is the first viable combination)
                costToBeat = cost;//the cost of this combo is the new cost to beat
                this.toBuy = new ArrayList<String[]>();//Copy down the items used to get this combo, and draft an order list
                for(int i = 0; i<orderList.size(); i++){
                        toBuy.add(dbInfo.get(orderList.get(i)));
                }
            }
            return;//Leave this function
        }//End if-------------------------------------------------------------------------------

        //If we get here, then we do not yet have enough of each part:
        for(int i = 0; i<table.length; i++){//Check if adding any of the remaining items to the order list will improve the situation:
            ArrayList<Integer> copyOrderList = copyOrderList(orderList); //Make a copy of the order list for recursive calls to play with (Don't wan't
                                                                         //them to chenge the original because we are unsure of whether adding those items is beneficial yet.)
            int[] copyFields = Arrays.copyOf(fields, fields.length);//Copy the vector of parts we currently have (Same justification as copyOrderList)
            if(incompleteOverlap(copyFields,table[i]) && checkUniqueRow(copyOrderList, i)){ //If 2 conditions are true: adding this item will decrease the number of parts we still need,
                                                                                            //and we have not already added this item to the list:
                addItem(copyFields,table[i]);//Add this item to the temperary order
                copyOrderList.add(i);
                findCombination(table,copyFields,copyOrderList);//Repeat the process with the new addition.
            }
        }
    }

    /**
     * Finds the cost of a particular orderList
     * @param orderList a list of the indexes of the items we might want to order
     * @return returns the sum of the prices of the items in orderList.
     */
    public int findCost(ArrayList<Integer> orderList){
        int cost = 0;
        for(int i = 0; i<orderList.size(); i++){//For each element of the order list, add its cost to the running total
            if(orderList.get(i) != null){
                cost += Integer.parseInt(this.dbInfo.get(orderList.get(i))[2]);
            }
        }
        return cost;//return the total
    }

    /**
     * Copies an orderlist and outputs the result
     * @param orderList list to be copied
     * @return the copy of the list.
     */
    private ArrayList<Integer> copyOrderList(ArrayList<Integer> orderList){
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i<orderList.size(); i++){//For each element of the orderList, copy it into the new list.
            result.add(orderList.get(i));
        }
        return result;
    }

    /**
     * determines if we have enough parts yet to fill the order
     * @param array a vector of the respective numbers of each part
     * @return true if the array doesn't need any more parts to fill the order.
     */
    private boolean isFull(int[] array){
        for(int i = 0; i<array.length; i++){//go throught the entire array
            if(array[i] < this.targetNumber){//If this element is lower than the desired number
                return false;
            }
        }
        return true;
    }

    /**
     * If there is not complete overlap (Complete overlap is defined as the situation in which, an item will only add to
     * parts that we already have enough of,) this function will add the new element to the field array and return the ammount of overlap.
     * @param fields An array that stores the number of parts we have of each field. eg if we were to add a chair with [Y N Y N] then this would add [1 0 1 0] element wise to the current list of parts
     * @param row The information on the particular item we are going to add. eg [Y N Y N]
     * @return Returns the ammount of surplus that this add caused. Not used in the rest of the code, but not bad information to have for future updates.
     */
    private int addItem(int[] fields, char[] row){
        int surplus = 0;
        if(incompleteOverlap(fields,row)){//If adding this item would result in an increase in the number of satisfied fields
            for(int i = 0; i<row.length; i++){//take the row vector, and add it to the fields vector.
                if(row[i] == 'Y'){//If this row will increase the number of the part in question
                    if(fields[i] != 0){
                        surplus++;
                    }
                    fields[i]++;//Mark down that we have one more of that part.
                }
            }
            return surplus;
        }else{
            return -1;//If adding this item wouldn't help, return -1.
        }
    }

    /**
     * Determines if an item can decrease the number of parts we still need.
     * @param fields the list of fields to be filled
     * @param row the row hoping to be purchased
     * @return returns true if this item does not only add to parts that we already have enough of.
     */
    private boolean incompleteOverlap(int[] fields, char[] row){
        for(int i = 0; i<row.length; i++){//Iterate through the entire array
            if(fields[i] < this.targetNumber && row[i] == 'Y'){//if there is a location in which the row would add something new
                return true;//Return there is not complete overlap.
            }
        }
        return false;//if we got through the array, there is complete overlap.
    }

    /**
     * This function determines if a particular row has already been added to an order list
     * @param orderList The order list to be searched for this item
     * @param index The index in dbInfo of the item being searched for
     * @return true if this item is not currently in the orderlist (ie. is unique,) false if it is
     */
    private boolean checkUniqueRow (ArrayList<Integer> orderList, int index)
    {
        for(int i = 0; i < orderList.size(); i++)//for each element of the orderlist
        {
            if(orderList.get(i) == index)//if this element refers to the same item as index
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes a 2d Array of the Ys and Ns from the database.
     * @param table A blank table to be filled with the appropriate Ys and Ns
     */
    public void fillTable(char[][] table){
        for(int i = 0; i<table.length; i++){
            for(int j = 0; j<table[0].length; j++){
                table[i][j] = dbInfo.get(i)[4+j].charAt(0);//Copy the Y/N section of each db entry into a convenient 2D array
            }
        }
    }      
}
