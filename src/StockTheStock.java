
import backend.Files;
import backend.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockTheStock extends Application {

    @Override
    public void start(Stage stage) {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Files.employeeMain, Files.employeeMainFile);
        mainContainer.loadScreen(Files.managerMain, Files.managerMainFile);
        mainContainer.loadScreen(Files.login, Files.loginFile);
        mainContainer.loadScreen(Files.SignUp, Files.SignUpFile);
        mainContainer.setStage(stage);
        mainContainer.setScreen(Files.login);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
