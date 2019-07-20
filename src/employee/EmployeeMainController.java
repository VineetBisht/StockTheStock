package employee;

import backend.ControlledScreen;
import backend.Datatable;
import backend.Files;
import backend.ScreensController;
import javafx.application.Platform;
import org.apache.commons.lang.time.StopWatch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EmployeeMainController implements ControlledScreen, Initializable {
    ScreensController myController, mainController;
    static Stage primaryStage;
    Stage stage;
    Scene scene;
    StopWatch timer;


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
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logout(ActionEvent event) {
        Datatable d = new Datatable();
        d.endshift();
        stage.close();
        myController.setScreen(Files.login);
    }

    @FXML
    void refund(ActionEvent event) {
        mainController.setScreen(Files.refund);
        stage.show();
    }

    @FXML
    void tables(ActionEvent event) {
        mainController.setScreen(Files.tables);
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
        mainContainer.loadScreen(Files.billing, Files.billingFile);
        mainContainer.loadScreen(Files.refund, Files.refundFile);
        mainContainer.loadScreen(Files.tables, Files.tablesFile);
        mainContainer.loadScreen(Files.complaint, Files.complaintFile);
        mainContainer.setStage(stage);
        primaryStage = stage;

        Datatable d=new Datatable();
        welcom_name.setText(welcom_name.getText()+" "+d.getUserName());

        timer= new StopWatch();
        timer.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        shift.setText(shift.getText().substring(0,13)+" "+LocalTime.ofSecondOfDay((int)timer.getTime()/1000).toString());
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
    }
}