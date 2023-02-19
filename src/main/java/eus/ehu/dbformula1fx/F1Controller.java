package eus.ehu.dbformula1fx;

import eus.ehu.business_logic.BLFacade;
import eus.ehu.business_logic.BLFacadeImpl;
import eus.ehu.dbformula1fx.controllers.FxController;
import eus.ehu.domain.Pilot;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.List;

public class F1Controller implements FxController {

    @FXML
    private ComboBox<String> nationalitiesCB;

    @FXML
    private ListView<Pilot> pilotsListView;
    @FXML
    private Label numPilotsDB;
    private BLFacade businessLogic;

    private F1Application mainApp;
    public void setMain(F1Application multiSceneApplication) {
        this.mainApp = multiSceneApplication;
    }

    @FXML
    void showPilotsByNationality() {
        String selectedNationality = nationalitiesCB.getValue();    //This can't be null -> No exception
        List<Pilot> pilots;
        if (selectedNationality.equals("All"))
            pilots = businessLogic.getAllPilots();
        else
            pilots = businessLogic.getPilotsByNationality(selectedNationality);
        pilotsListView.setItems(FXCollections.observableArrayList(pilots));
    }

    @FXML
    void initialize() {
        nationalitiesCB.getItems().addAll( "All",
                "British",
                "Spanish",
                "German",
                "Italian",
                "French",
                "Japanese",
                "Portuguese"
        );

        businessLogic = new BLFacadeImpl();

        this.refresh();
    }

    public void refresh(){
        nationalitiesCB.setValue("All");
        List<Pilot> allPilots = businessLogic.getAllPilots();
        pilotsListView.setItems(FXCollections.observableArrayList(allPilots));

        String numPilots = Integer.toString(allPilots.size());
        numPilotsDB.setText(numPilots);
    }

    @FXML
    void addNewPilot() {
        mainApp.changeScene("Add Pilot Menu");
    }

    @FXML
    void addPointsToPilot() {
        String pilotName;
        try{
            pilotName = pilotsListView.getSelectionModel().getSelectedItem().getName();
        } catch (NullPointerException e){
            return;
        }
        TextInputDialog testDialog = new TextInputDialog("An integer. For example: 100");
        testDialog.setHeaderText("Number of points to add to " + pilotName);
        testDialog.showAndWait();

        int extraPoints;
        /*while(true) {
            try {
                extraPoints = Integer.parseInt(testDialog.getEditor().getText());
                break;
            } catch (NumberFormatException e) {
                //testDialog.getEditor().setText("An integer. For example: 100");
                testDialog.setContentText("An integer. For example: 100");
            }
        } -> The program breaks*/

        try {
            extraPoints = Integer.parseInt(testDialog.getEditor().getText());
        } catch (NumberFormatException e) {
            return;
        }   //Another option: just return to the menu without changing anything

        businessLogic.addPointsToPilot(extraPoints, pilotName);
        showPilotsByNationality();
    }

    @FXML
    void deletePilot() {
        int pilotId;
        try{
            pilotId = pilotsListView.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e){
            return;
        }
        businessLogic.deletePilotById(pilotId);

        int numberOfPilots = Integer.parseInt(numPilotsDB.getText()) - 1;   //We always have numeric values
        numPilotsDB.setText(Integer.toString(numberOfPilots));

        //Here we are accessing the database once again to obtain the remaining pilots
        //Maybe we can modify deletePilotById, so it returns the list of the remaining pilots in the DB
        //In sake of simplicity, we are going to leave the code this way by now
        showPilotsByNationality();
    }
}
