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
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainController implements ControlledScreen, Initializable {
    ScreensController myController,mainController;
    Stage stage;

    @FXML
    private Label welcom_name;

    @FXML
    private Label shift;

    @FXML
    void billing(ActionEvent event) {
        mainController.setScreen(Files.billing);
        stage.show();
    }

    @FXML
    void complaints(ActionEvent event) {
        mainController.setScreen(Files.complaint);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void refund(ActionEvent event) {
        mainController.setScreen(Files.refund);
        stage.show();
    }

    @FXML
    void tables(ActionEvent event) {
        myController.setScreen(Files.tables);
        stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stage=new Stage();
        ScreensController mainContainer = new ScreensController();
        this.mainController=mainContainer;
        mainContainer.loadScreen(Files.login, Files.loginFile);
        mainContainer.loadScreen(Files.complaint, Files.complaintFile);
        mainContainer.loadScreen(Files.SignUp, Files.SignUpFile);
        mainContainer.loadScreen(Files.tables, Files.tablesFile);
        mainContainer.loadScreen(Files.billing, Files.billingFile);
        mainContainer.setStage(stage);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //  stage.setAlwaysOnTop(true);
        stage.setResizable(false);
    }
}
