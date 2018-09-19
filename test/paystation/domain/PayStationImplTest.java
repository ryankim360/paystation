/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
    
    /**
     * Verify that empty() returns the total earned and clears the total.
     * @throws IllegalCoinException 
     */
    @Test
    public void testEmpty()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        
        ps.buy();
        
        ps.addPayment(25);
        ps.addPayment(25);
        
        ps.buy();
        
        assertEquals("Should return 100 cents and empty the value.", 125, ps.empty());
    }
    
    /** 
     * Canceled entry does not add the amount returned by empty
     */
    @Test
    public void testThatCancelDoesNotAddToEmpty()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
   
        ps.buy();
        
        ps.addPayment(25);
        
        ps.cancel();
        
        assertEquals("Should return 20", 20, ps.empty());
    }
    
    /**
     * Call to empty resets the total to zero
     */
    @Test
    public void resetTotalToZero()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        
        ps.buy();
        
        assertEquals("User bought time using 35 cents", 35, ps.empty());
        
        assertEquals("Total should now be 0 after empty was called.", 0, ps.empty());
    }
    
    /**
     * Call to cancel returns a map containing one coin entered.
     */
    @Test
    public void mapContainsOneCoin() 
            throws IllegalCoinException{
        ps.addPayment(5);
        Map<Integer, Integer> results = ps.cancel();
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(5, 1);
        
        assertEquals("Should return one nickel", expected, results);
    }
    
    /**
     * Call to cancel returns a map containing a mixture of coins entered
     */
    @Test
    public void mapContainsMixture()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        
        Map<Integer, Integer> results = ps.cancel();
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put (5, 2);
        expected.put (10, 3);
        expected.put (25, 1);
        
        assertEquals("Should return 2 nickels, 3 dimes, and 1 quarter.", expected, results);
    }
    
    /**
     * Call to cancel returns a map that does not contain a key for a coin not entered
     */
    @Test
    public void mapDoesNotContainKeyForCoinNotEntered ()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        
        Map<Integer, Integer> results = ps.cancel();
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put (10, 1);
        expected.put (25, 1);
        
        assertEquals("Should return one quarter and one dime, no nickels", expected, results);
    }
    
    /**
     * Call to cancel clears the map
     */
    @Test
    public void cancelClearsMap() 
            throws IllegalCoinException {
        
        ps.addPayment(5);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        
        ps.cancel();
        
        Map<Integer, Integer> results = ps.cancel();
        Map<Integer, Integer> expected = new HashMap<>();
       
        assertEquals("Should return no coins because cancel clears the map", expected, results);
    }
    
    /**
     * Call to buy clears the map
     */
    @Test
    public void buyClearsMap()
            throws IllegalCoinException {
        
        ps.addPayment(5);
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        
        ps.buy();

        Map<Integer, Integer> results = ps.cancel();
        Map<Integer, Integer> expected = new HashMap<>();
        
        assertEquals("Should return no coins because buy clears the map", expected, results);
    }
}
