/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneElements;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

/**
 *
 * @author gebruiker
 */
public class SensorLineChart {
    private final LineChart lineChart;
    private final NumberAxis lineChartTimeAxis;
    private boolean canUpdate;
    private boolean restartMeasurement;
    private int startTime;
    
    public SensorLineChart (LineChart lineChart, NumberAxis lineChartTimeAxis) {
        this.lineChart = lineChart;
        this.lineChartTimeAxis = lineChartTimeAxis;
        this.startTime = 0;
        this.canUpdate = false;
        this.restartMeasurement = true;
        
        this.lineChartTimeAxis.setAutoRanging(false);
    }
    
    public void ToggleCanUpdate () {
        this.canUpdate = !canUpdate;
        
        if (canUpdate) {
            clearData();
        }
    }
    
    public void setMaxTime (String maxTime) {
        try {
            lineChartTimeAxis.setUpperBound(Math.abs(Integer.valueOf(maxTime)));
        } catch (NumberFormatException exeption) {
            System.out.println("Maximum time input is invalid");
        }
    }
    
    public void clearData () {
        setRestartMeasurement(true);
    }
    
    int TimeSinceMeasurementStart (int time) {
        return time - startTime;
    }
    
    public void setRestartMeasurement (boolean value) {
        restartMeasurement = value;
    }
    
    public void setTime (int time) {
        this.startTime = time;
        setRestartMeasurement(false);
    }
    
    public boolean getCanUpdate () {
        return canUpdate;
    }
    
    public boolean getRestartMeasurement () {
        return restartMeasurement;
    } 
}
