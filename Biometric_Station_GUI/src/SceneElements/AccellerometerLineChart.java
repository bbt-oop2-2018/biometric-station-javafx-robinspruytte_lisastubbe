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
public class AccellerometerLineChart extends SensorLineChart {
    
    private final XYChart.Series xChartValues;
    private final XYChart.Series yChartValues;
    private final XYChart.Series zChartValues;
    private final String X_AXIS;
    private final String Y_AXIS;
    private final String Z_AXIS;
    private final int NUMBERS_AFTER_DOUBLE_COMMA;
    
    public AccellerometerLineChart(LineChart lineChart, NumberAxis lineChartTimeAxis, String xAxis, String yAxis, String zAxis, int numbersAfterDoubleComma) {
        super(lineChart, lineChartTimeAxis);
        this.X_AXIS = xAxis;
        this.Y_AXIS = yAxis;
        this.Z_AXIS = zAxis;
        this.NUMBERS_AFTER_DOUBLE_COMMA = numbersAfterDoubleComma;
        this.xChartValues = new XYChart.Series();
        xChartValues.setName(X_AXIS);
        lineChart.getData().add(xChartValues);
        
        this.yChartValues = new XYChart.Series();
        yChartValues.setName(Y_AXIS);
        lineChart.getData().add(yChartValues);
        
        this.zChartValues = new XYChart.Series();
        zChartValues.setName(Z_AXIS);
        lineChart.getData().add(zChartValues);
    }
    
    @Override
    public void clearData () {
        //removes all data from the linechart
        xChartValues.getData().clear();
        yChartValues.getData().clear();
        zChartValues.getData().clear();
        super.setRestartMeasurement(true);
        
    }
    
    private void addData (int xPosition, int yPosition, String axis) {
        if (axis.equals(X_AXIS)) {
            xChartValues.getData().add(new XYChart.Data(super.TimeSinceMeasurementStart(xPosition), yPosition));
        } else if (axis.equals(Y_AXIS)) {
            yChartValues.getData().add(new XYChart.Data(super.TimeSinceMeasurementStart(xPosition), yPosition));
        } else if (axis.equals(Z_AXIS)) {
            zChartValues.getData().add(new XYChart.Data(super.TimeSinceMeasurementStart(xPosition), yPosition));
        }
    }
    
    public void updateChart (Point point, String axis) {
        if (super.getCanUpdate()) {
             if (super.getRestartMeasurement()) {
                setTime(point.getTime());
            }
            addData(point.getTime(),point.getYPosition(), axis);
        }
    }
    
}
