package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.AccountType;
import model.Location;
import model.Model;
import model.QualityReport;

public class QualityReportController extends Controller
{
    private Location reportLocation = null;
    
    @FXML
    private ChoiceBox<QualityReport.WaterCondition> conditionTypeChoiceBox;
    @FXML
    private TextField virusPpmField;
    @FXML
    private TextField contaminantPpmField;
    
    public void setReportLocation(Location location) { reportLocation = location; }
    
    @FXML
    private void initialize() {
        conditionTypeChoiceBox.getItems().setAll(QualityReport.WaterCondition.values());
        virusPpmField.setText("");
        contaminantPpmField.setText("");
    }
    
    @FXML
    private void onKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            onSubmitPressed();
        }
    }
    
    @FXML
    private void onSubmitPressed() {
        Model model = Model.getInstance();
        QualityReport.WaterCondition waterCondition = conditionTypeChoiceBox.getValue();
        double virusPpm, contaminantPpm;
        if(ControllerUtils.isEmpty(virusPpmField.getText(), contaminantPpmField.getText())) {
            ControllerUtils.createErrorMessage(stage, "Submit Report Error", "One or more fields are empty");
            return;
        }
        try {
            virusPpm = Double.parseDouble(virusPpmField.getText());
            contaminantPpm = Double.parseDouble(contaminantPpmField.getText());
        } catch (NumberFormatException e) {
            ControllerUtils.createErrorMessage(stage, "Submit Report Error", "Please enter a valid number");
            return;
        }
        
        if (waterCondition == null) {
            ControllerUtils.createErrorMessage(stage, "Submit Report Error", "Please select a Water Condition");
        } else {
            if(Model.CURRENT_USER.getAccountType().isAuthorized(AccountType.Worker)) {
                model.hideQualityReportsNear(reportLocation);
                model.createQualityReport(reportLocation, waterCondition, virusPpm, contaminantPpm);
                Alert message = ControllerUtils.createMessage(stage, "Submit Quality Report", "Success",
                        "Your water quality report has been added", Alert.AlertType.INFORMATION);
                message.setOnCloseRequest(event -> stage.close());
            } else {
                ControllerUtils.createErrorMessage(stage, "Submit Report Error", "Illegal Permissions");
            }
        }
    }
    
    @FXML
    private void onCancelPressed() {
        stage.close();
    }
}