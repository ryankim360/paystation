package paystation.domain;

import java.util.HashMap;
import java.util.Map;
/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int totalEarned;
    Map<Integer, Integer> coinMap;
    
    private int nickelCounter = 0;
    private int dimeCounter = 0;
    private int quarterCounter = 0;
    
    private int nickel = 5;
    private int dime = 10;
    private int quarter = 25;
    
    PayStationImpl() {
        coinMap = new HashMap<>();
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        
        switch (coinValue) {
            case 5:
                nickelCounter++;
                break;
            case 10: 
                dimeCounter++;
                break;
            case 25: 
                quarterCounter++;
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalEarned += insertedSoFar;
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        
        if (nickelCounter != 0) {
            coinMap.put(nickel, nickelCounter);
        }

        
        if (dimeCounter != 0) {
            coinMap.put(dime, dimeCounter);
        }

        if (quarterCounter != 0) {
            coinMap.put(quarter, quarterCounter);
        }
        
        Map<Integer, Integer> temp = new HashMap<>();
        temp.putAll(coinMap);
        reset();
        return temp;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        nickelCounter = 0;
        dimeCounter = 0;
        quarterCounter = 0;
        
        coinMap.remove(nickel);
        coinMap.remove(dime);
        coinMap.remove(quarter);
    }
    
    public int empty() {
        int temp = totalEarned;
        totalEarned = 0;
        return temp;
        
    }
}
