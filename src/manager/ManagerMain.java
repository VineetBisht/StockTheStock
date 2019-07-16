package manager;

import backend.ControlledScreen;
import backend.Files;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMain implements ControlledScreen, Initializable {
    ScreensController myController, mainController;
    static Stage primaryStage;
    Stage stage;
    Scene scene;

    @FXML
    private Label welcom_name;

    @FXML
    void report(ActionEvent event) {

    }

    @FXML
    void complaints(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
    System.exit(0);
    }

    @FXML
    void manageEmployees(ActionEvent event) {
    mainController.setScreen(Files.manageEmp);
    stage.show();
    }

    @FXML
    void manageStock(ActionEvent event) {
    mainController.setScreen(Files.manageStock);
    stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = new Stage();
        ScreensController mainContainer = new ScreensController();
        this.mainController = mainContainer;
        mainContainer.loadScreen(Files.manageStock, Files.manageStockFile);
        mainContainer.loadScreen(Files.manageEmp, Files.manageEmpFile);
        mainContainer.setStage(stage);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        primaryStage=stage;
    }

    public static void resizeScreen() {
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }
}
