/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_station_gui;

import DataHandelers.DataParser;
import be.vives.oop.mqtt.chatservice.IMqttMessageHandler;
import be.vives.oop.mqtt.chatservice.MqttChatService;
import DataHandelers.Point;
import SceneElements.AccellerometerLineChart;
import SceneElements.MeasurementButton;
import SceneElements.SingleValueLineChart;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author gebruiker
 */
public class FXMLDocumentController implements Initializable, IMqttMessageHandler {
    
    //developer chosen values
    private final int NUMBERS_AFTER_DOUBLE_COMMA = 2;
    
    private final String X_AXIS = "X-axis";
    private final String Y_AXIS = "Y-axis";
    private final String Z_AXIS = "Z-axis";
    private final String HARTBEAT_CHART_NAME = "Hartbeat chart";
    private final String TEMPERATURE_CHART_NAME = "Temperature chart";
    
    Point[] pointList;
    
    //mqtt
    private MqttChatService chatService;
    private final DataParser parser = new DataParser();
    
    //Buttons
    @FXML
    private Button hartbeatButton;
    @FXML
    private Button accellerometerButton;
    @FXML
    private Button temperatureButton;
    
    private MeasurementButton hartbeatMeasurementButton;
    private MeasurementButton accellerometerMeasurementButton;
    private MeasurementButton temperatureMeasurementButton;
    
    //inputs
    @FXML
    private TextField hartbeatMaxTimeInput;
    @FXML
    private TextField accellerometerMaxTimeInput;
    @FXML
    private TextField temperatureMaxTimeInput;
    
    //LineCharts
    @FXML
    private LineChart hartbeatChart;
    private SingleValueLineChart hartbeatSensorChart;
    
    @FXML
    private LineChart accellerometerChart;
    private AccellerometerLineChart accellerometerSensorChart;
    
    @FXML
    private LineChart temperatureChart;
    private SingleValueLineChart temperatureSensorChart;
    
    //timeAxises
    @FXML
    private NumberAxis xAxisHartBeat;
    
    @FXML
    private NumberAxis xAxisAccellerometer;
    
    @FXML
    private NumberAxis xAxisTemperature;
    
    @FXML
    private void handleHartbeatButtonAction(ActionEvent event) {
        hartbeatSensorChart.ToggleCanUpdate();
        hartbeatMeasurementButton.pressed();
    }
    
    @FXML
    private void handleAccellerometerButtonAction(ActionEvent event) {
        accellerometerSensorChart.ToggleCanUpdate();
        accellerometerMeasurementButton.pressed();
    }
    
    @FXML
    private void handleTemperatureButtonAction(ActionEvent event) {
        temperatureSensorChart.ToggleCanUpdate();
        temperatureMeasurementButton.pressed();
    }
    
    @FXML
    private void handleHartbeatInput(ActionEvent event) {
        hartbeatSensorChart.setMaxTime(hartbeatMaxTimeInput.getText());
    }
    
    @FXML
    private void handleAccellerometerInput(ActionEvent event) {
        accellerometerSensorChart.setMaxTime(accellerometerMaxTimeInput.getText());
    }
    
    @FXML
    private void handleTemperatureInput(ActionEvent event) {
        temperatureSensorChart.setMaxTime(temperatureMaxTimeInput.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        hartbeatMeasurementButton = new MeasurementButton(hartbeatButton);
        accellerometerMeasurementButton = new MeasurementButton(accellerometerButton);
        temperatureMeasurementButton = new MeasurementButton(temperatureButton);
        
        hartbeatSensorChart = new SingleValueLineChart(hartbeatChart, xAxisHartBeat, HARTBEAT_CHART_NAME);
        accellerometerSensorChart = new AccellerometerLineChart(accellerometerChart, xAxisAccellerometer, X_AXIS, Y_AXIS, Z_AXIS, NUMBERS_AFTER_DOUBLE_COMMA);
        temperatureSensorChart = new SingleValueLineChart(temperatureChart, xAxisTemperature, TEMPERATURE_CHART_NAME);
        
        //mqtt
        chatService = new MqttChatService();
        chatService.setMessageHandler(this);
        
        disconnectClientOnClose();
    }

    private void disconnectClientOnClose() {
        // Source: https://stackoverflow.com/a/30910015
        temperatureButton.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).setOnCloseRequest((event) -> {
                            chatService.disconnect();
                        });
                    }
                });
            }
        });
    }    

    @Override
    public void messageArrived(String channel, String message) {
        //System.out.println("message Arrived" + message);
        Platform.runLater(() -> {
            pointList = parser.parse(message, NUMBERS_AFTER_DOUBLE_COMMA);
            if (!(pointList == null)) {
                hartbeatSensorChart.updateChart(pointList[0]);
                accellerometerSensorChart.updateChart(pointList[2],X_AXIS);
                accellerometerSensorChart.updateChart(pointList[3],Y_AXIS);
                accellerometerSensorChart.updateChart(pointList[4],Z_AXIS);
                temperatureSensorChart.updateChart(pointList[1]);
            } else {
                System.out.println("Invalid Data received");
            }
        });
    }
}
