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

//datamodel: { "timestamp": 100, "bpm": 113, "temperature": 23, "accellcx": 0.2255, "accellcy": 0.25533, "accellcz": 0.9 }
public class DataParser {
    
    private  int numbersAfterDoubleComma;
    private final int FAILSAFE_NUMBER = -100;
    private final int INDEX_OF_TIMESTAMP = 0;
    private final int NUMBER_OF_DATA = 6;
    private final char SEPPARATE_TOKEN = ':';
    private final String NEXT_DATA_TOKEN = ",";
    private final String START_OF_DATAFRAME = "{";
    private final String END_OF_DATAFRAME = "}";
    private final String INVALID_DATATYPE = "invalid";
    private final String INT_DATATYPE = "integer";
    private final String DOUBLE_DATATYPE = "double";
    
    public Point[] parse(String dataString, int numbersAfterDoubleComma ) {
        
        this.numbersAfterDoubleComma = numbersAfterDoubleComma;
        
        if (!isValidString(dataString)) {
            return null;
        } else {
            String[] sepparatedData = findData(dataString);
            return createPointsFromValues(sepparatedData);
        }
    }
    
    private boolean isValidString (String dataString) {
        return dataString.contains(START_OF_DATAFRAME) && dataString.contains(END_OF_DATAFRAME) && countSepparateTokens(dataString) == NUMBER_OF_DATA;
    }
    
    private String[] findData (String dataString) {
        dataString = dataString.trim();
        String[] sepparatedData = new String[NUMBER_OF_DATA];
        
        for (int i = 0 ; i < sepparatedData.length; i++) {
            if (!(findToken(dataString,NEXT_DATA_TOKEN) == -1)) { //findtoken returns -1 if the token was not found
                sepparatedData[i] = dataString.substring(findToken(dataString,Character.toString(SEPPARATE_TOKEN)) + 1, findToken(dataString,NEXT_DATA_TOKEN)).trim();
                dataString = dataString.replace(dataString.substring(0, findToken(dataString,NEXT_DATA_TOKEN) + 1), "");
            } else {
                sepparatedData[i] = dataString.substring(findToken(dataString,Character.toString(SEPPARATE_TOKEN)) + 1, findToken(dataString,END_OF_DATAFRAME)).trim();
            }
        }
        
        return sepparatedData;
    }
    
    private Point[] createPointsFromValues (String[] sepparatedData) {
        Point[] pointList = new Point[sepparatedData.length - 1];
        if (isNumber(sepparatedData[INDEX_OF_TIMESTAMP]).equals(INVALID_DATATYPE)) {
          sepparatedData[INDEX_OF_TIMESTAMP] = Integer.toString(FAILSAFE_NUMBER);   
        }
        
        for (int i = 0 ; i < pointList.length; i++) {
            String dataType = isNumber(sepparatedData[i + 1]);
            if (!dataType.equals(INVALID_DATATYPE)) {
                if (dataType.equals(INT_DATATYPE)) {
                    pointList[i] = new Point(Integer.parseInt(sepparatedData[i + 1]), Integer.parseInt(sepparatedData[INDEX_OF_TIMESTAMP]));
                } else {
                    pointList[i] = new Point(( (int) (Double.parseDouble(sepparatedData[i + 1]) * (Math.pow(10, numbersAfterDoubleComma)))), Integer.parseInt(sepparatedData[INDEX_OF_TIMESTAMP]));
                }
            } else {
                pointList[i] = new Point(FAILSAFE_NUMBER, Integer.parseInt(sepparatedData[INDEX_OF_TIMESTAMP]));
            }
        }
        return pointList;
    }
    
    private int countSepparateTokens (String dataString) {
        return (int) dataString.chars().filter(num -> num == SEPPARATE_TOKEN).count();
    }
    
    
    private int findToken (String dataString, String Token) {
        return dataString.indexOf(Token);
    }
    
    private String isNumber(String string) {
        try {
            Integer.valueOf(string);
            return INT_DATATYPE;
        } catch (NumberFormatException exeption) {
            try{
                Double.parseDouble(string);
                return DOUBLE_DATATYPE;
            }catch(NumberFormatException e){
                return INVALID_DATATYPE;
            }
        }
    }
}
