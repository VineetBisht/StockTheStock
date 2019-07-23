package manager;

import backend.ControlledScreen;
import backend.Datatable;
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
        mainController.setScreen(Files.managerCompl);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        Datatable d = new Datatable();
        d.endshift();
        stage.close();
        myController.setScreen(Files.login);
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
        mainContainer.loadScreen(Files.managerCompl, Files.managerComplFile);
        mainContainer.setStage(stage);

        Datatable d=new Datatable();
        welcom_name.setText(welcom_name.getText()+" "+d.getUserName());

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
