/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneElements;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author gebruiker
 */
public class MeasurementButton {
    @FXML
    private final Button button;
    private final String STOP_MESSAGE = "Stop measurement";
    private final String START_MESSAGE = "Start measurement";
    
    public MeasurementButton (Button button) {
        this.button = button;
    }
    
    public void pressed () {
        if (button.getText().equals(STOP_MESSAGE)) {
            button.setText(START_MESSAGE);
        } else {
            button.setText(STOP_MESSAGE);
        }
    }
}
