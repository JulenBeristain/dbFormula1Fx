package eus.ehu.dbformula1fx;

import eus.ehu.business_logic.BLFacade;
import eus.ehu.business_logic.BLFacadeImpl;
import eus.ehu.dbformula1fx.controllers.FxController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddPilotController implements FxController {

    @FXML
    private ComboBox<String> newNationalityCB;

    @FXML
    private TextField newPilotNameField;

    @FXML
    private TextField newPilotPointsField;

    @FXML
    private Label output;

    private F1Application mainApp;
    public void setMain(F1Application multiSceneApplication) {
        this.mainApp = multiSceneApplication;
    }

    private BLFacade businessLogic;

    @FXML
    void addNewPilot() {
        String newName = newPilotNameField.getText();
        String newNat = newNationalityCB.getValue();
        if (newName.equals("") | newNat == null){
            output.setText("Set the name and the nationality of the new Pilot");
            return;
        }

        int newPoints;
        try {
            newPoints = Integer.parseInt(newPilotPointsField.getText());
        } catch (NumberFormatException e) {
            output.setText("The number of points must be an integer!");
            return;
        }

        businessLogic.storePilot(newName, newNat, newPoints);
        output.setText("New pilot added to the database successfully");
    }

    @FXML
    void goBackMainMenu() {
        mainApp.changeScene("F1 Main Menu");
    }
    @FXML
    void initialize(){
        newNationalityCB.getItems().addAll(
                "British",
                "Spanish",
                "German",
                "Italian",
                "French",
                "Japanese",
                "Portuguese"
        );

        businessLogic = new BLFacadeImpl();
    }

    public void refresh(){
        newNationalityCB.setValue(null);
        newPilotNameField.setText("");
        newPilotPointsField.setText("");
        output.setText("");
    }
}
