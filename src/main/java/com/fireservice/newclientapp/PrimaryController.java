package com.fireservice.newclientapp;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField fireIdTextField;

    @FXML
    private Button getFiresButton;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private Button sendTruckButton;

    @FXML
    void onGetFiresButtonClicked(ActionEvent event) {
        // call web service api
    }

    @FXML
    void onSendTruckButtonClicked(ActionEvent event) {
        int fireId = Integer.parseInt(fireIdTextField.getText());
        
        // call api
        
    }

    private void printFires(ArrayList fires){
        
        // layout and print fire details to report text area
        
    }
}
