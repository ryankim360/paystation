/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paystation.domain;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryankim
 */
public class PayStationMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String choice;
        int coinInput = 0;
        int rateChoice = 1;
        
        PayStationImpl ps = new PayStationImpl();
        
        System.out.println("Welcome to the Paystation \n");
        
        while (true) {
            System.out.println("Please pick an option:");
            System.out.println("1. Deposit Coins");
            System.out.println("2. Display");
            System.out.println("3. Buy ticket");
            System.out.println("4. Cancel");
            System.out.println("5. Change Rate Strategy \n");
            
            System.out.print(">> ");
            choice = keyboard.next();
            System.out.println("");
            if ("1".equals(choice) || "2".equals(choice) || "3".equals(choice) || "4".equals(choice) || "5".equals(choice)) {
                switch (choice) {
                    case "1": 
                        System.out.print("Please insert coins: ");
                        coinInput = keyboard.nextInt();
                        System.out.println("");
                    try {
                        ps.addPayment(coinInput, rateChoice);
                    } catch (IllegalCoinException ex) {
                        //Logger.getLogger(PayStationMain.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Invalid Coin: " + coinInput + "\n");
                    }
                        break;
                    case "2":
                        System.out.println("Time bought(minutes): " + ps.readDisplay(rateChoice) + "\n");
                        break;
                    case "3":
                        Receipt r = ps.buy();
                        System.out.println("Receipt: " +  r.value() + " minutes.\n");
                        System.exit(0);
                        break;
                    case "4":
                        System.out.println("Coins returned: " + ps.cancel() + "\n");
                        break;
                    case "5":
                        System.out.println("Please pick a rate strategy: ");
                        System.out.println("1. Linear Rate");
                        System.out.println("2. Progressive Rate");
                        System.out.println("3. Alternating Rate\n");
                        System.out.print(">> ");
                        rateChoice = keyboard.nextInt();
                        System.out.println("");

                        switch (rateChoice) {
                            case 1:
                                System.out.println("You have chosen Linear Rate Strategy.\n");
                                break;
                            case 2:
                                System.out.println("You have chosen Progressive Rate Strategy.\n");
                                break;
                            case 3:
                                System.out.println("You have chosen Alternating Rate Strategy.\n");
                                break;
                            default:
                                System.out.println("Invalid choice. \n");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. \n");   
                }
            } else {
                System.out.println("Invalid choice");
                System.out.println("");
            }
            

        }
    }
    
}
