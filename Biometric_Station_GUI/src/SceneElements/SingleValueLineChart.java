/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneElements;

import DataHandelers.Point;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author gebruiker
 */
public class SingleValueLineChart extends SensorLineChart {
    private final XYChart.Series chartValues;
    private final String CHART_NAME;
    
    public SingleValueLineChart(LineChart lineChart, NumberAxis lineChartTimeAxis, String chartName) {
        super(lineChart, lineChartTimeAxis);
        this.CHART_NAME = chartName;
        
         this.chartValues = new XYChart.Series();
        chartValues.setName(CHART_NAME);
        lineChart.getData().add(chartValues);
    }
    
    public void addData (int xPosition, int yPosition) {
        chartValues.getData().add(new XYChart.Data(TimeSinceMeasurementStart(xPosition),  yPosition));
    }
    
    public void updateChart (Point point) {
        if (super.getCanUpdate()) {
            if (super.getRestartMeasurement()) {
                setTime(point.getTime());
            }
            addData(point.getTime(),point.getYPosition());
        }
    }
    
    @Override
    public void clearData () {
        //removes all data from the linechart
        chartValues.getData().clear();
        setRestartMeasurement(true);
        
    }
    
}
