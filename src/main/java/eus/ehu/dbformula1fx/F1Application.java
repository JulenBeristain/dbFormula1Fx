package eus.ehu.dbformula1fx;

import eus.ehu.dbformula1fx.controllers.FxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class F1Application extends Application {

    private Window addPilotWindow;
    private Window f1Window;

    static class Window {
        Parent ui;
        FxController controller;
    }

    private Window load(String fxmlFile) throws IOException {
        Window window = new Window();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        window.ui = fxmlLoader.load();
        window.controller = fxmlLoader.getController();
        window.controller.setMain(this);
        return window;
    }

    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        f1Window = load("F1MainMenu.fxml");
        addPilotWindow = load("AddPilotMenu.fxml");

        scene = new Scene(f1Window.ui);
        stage.setTitle("F1 Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeScene(String sceneName) {
        switch (sceneName){
            case "F1 Main Menu" -> {
                stage.setTitle("F1 Main Menu");
                scene.setRoot(f1Window.ui);
                f1Window.controller.refresh();
            }
            case "Add Pilot Menu" -> {
                stage.setTitle("Add Pilot Menu");
                scene.setRoot(addPilotWindow.ui);
                addPilotWindow.controller.refresh();
            }
        }
    }
}