package employee;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignUpController implements ControlledScreen {
    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }
}
