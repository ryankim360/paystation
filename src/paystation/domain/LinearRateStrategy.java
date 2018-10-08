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
    
    
    @Override
    public int calculateLinearTime (int insertedSoFar) {
        int timeBought;
        timeBought = insertedSoFar / 5 * 2;
        return timeBought;
    }

    @Override
    public int calculateProgressiveTime(int insertedSoFar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int calulateAlternativeTime(int insertedSoFar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
