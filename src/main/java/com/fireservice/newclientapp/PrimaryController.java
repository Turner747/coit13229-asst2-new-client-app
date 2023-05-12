package com.fireservice.newclientapp;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    void initialize(){
        reportTextArea.setFont(Font.font("Monospace", FontWeight.MEDIUM, 12));
    }

    @FXML
    void onGetFiresButtonClicked(ActionEvent event) {
        FireServiceClient fireApi = new FireServiceClient();
        
        //ArrayList<FireDetails> fires = fireApi.getFires(); // api call
        ArrayList<FireDetails> fires = getTestData(); // replace with api call
        
        printFires(fires);
    }

    @FXML
    void onSendTruckButtonClicked(ActionEvent event) {
        int fireId = Integer.parseInt(fireIdTextField.getText());
        
        FireServiceClient fireApi = new FireServiceClient();
        int status = fireApi.sendFireTruck(fireId);
        
        if (status == 200){
            displayAlert("Success","Firetruck has been sent to fire " + fireId, AlertType.INFORMATION);
        } else {
            displayAlert("Failure","An error has occurred. Error code: " + status, AlertType.ERROR);
        }
    }

    private void printFires(ArrayList<FireDetails> fires){
        final String HEADER_FORMAT = "%s%10s%10s%15s%15s%25s\n";
        final String SEPARATOR = "-----------------------------------------------------------------------------\n";
        final String ROW_FORMAT = "%d%9d%10d%13d%15d%22.2f\n";
        
        StringBuilder output = new StringBuilder();
        
        output.append(String.format(HEADER_FORMAT,
                "ID","X Pos","Y Pos","Drone ID","Severity","Burning Area Radius"));
        output.append(SEPARATOR);
        
        for(FireDetails f : fires){
            output.append(String.format(ROW_FORMAT,
                f.getId(),f.getX_pos(),f.getY_pos(),f.getDroneId(),
                f.getSeverity(),f.getBurningAreaRadius()));
        }
        
        reportTextArea.setText(output.toString());
    }
    
    private ArrayList<FireDetails> getTestData(){
        ArrayList<FireDetails> testFires = new ArrayList<FireDetails>();
        testFires.add(new FireDetails(1, 23, 45, 1, 5, 34.5));
        testFires.add(new FireDetails(2, 35, 77, 2, 1, 11.1));
        testFires.add(new FireDetails(3, 56, 24, 2, 3, 25.4));
        testFires.add(new FireDetails(4, 64, 33, 3, 3, 23.3));
        return testFires;
    }
    
    private void displayAlert(String header, String message, AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("Send Firetruck");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
