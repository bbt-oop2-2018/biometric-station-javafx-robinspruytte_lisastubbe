/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataHandelers;

/**
 *
 * @author gebruiker
 */
public class pointListGenerator {
    private int intervalTime;
    private int maxTime;
    private int sampleAmmount;
    private final int DEFAULT_VALUE;
    
    public pointListGenerator (){
        this.intervalTime = 0;
        this.maxTime = 0;
        this.sampleAmmount = 0;
        this.DEFAULT_VALUE = 1;
    }
    
    public void updateUserData (String maxTime, String intervalTime) {
        this.maxTime = parseStringToInteger(maxTime);
        this.sampleAmmount = parseStringToInteger(intervalTime);
    }
    
    private int parseStringToInteger (String string) {
        if (isInteger(string)) {
            return Integer.parseInt(string);
        } else {
            return DEFAULT_VALUE;
        }
    }
    
    private boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException exeption) {
            return false;
        }
    }
}
