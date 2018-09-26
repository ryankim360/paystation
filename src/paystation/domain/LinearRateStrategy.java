/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

/**
 *
 * @author ryankim
 */
public class LinearRateStrategy implements RateStrategy{
    
    
    public int calulateTime (int insertedSoFar) {
        int timeBought;
        timeBought = insertedSoFar / 5 * 2;
        return timeBought;
    }
}
