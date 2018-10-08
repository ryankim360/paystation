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
public class ProgressiveRateStrategy implements RateStrategy{
    
    @Override
    public int calculateProgressiveTime (int insertedSoFar) {
        int returnTime = 0;
        if (insertedSoFar >= 350) {
            returnTime = (insertedSoFar - 350)/5 + 120;
        }
        else if (insertedSoFar >= 150) {
            insertedSoFar -= 150;
            returnTime = (insertedSoFar * 3/10) + 60;
        }
        else {
            returnTime = (insertedSoFar * 2)/5;
        }
        return returnTime;
    }

    @Override
    public int calculateLinearTime(int insertedSoFar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int calulateAlternativeTime(int insertedSoFar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
