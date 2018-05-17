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
public class Point {
    private final int yPosition;
    private final int time;
    
    public Point (int yPosition, int time) {
        this.yPosition = yPosition;
        this.time = time;
    }
    
    public int getYPosition () {
        return yPosition;
    }
    
    public int getTime () {
        return time;
    }
}
