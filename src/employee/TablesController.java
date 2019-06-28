package employee;

import backend.ControlledScreen;
import backend.ScreensController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class TablesController implements ControlledScreen {
    ScreensController myController;

        @FXML
        private Label welcom_name;

        @FXML
        private Label shift;

        @FXML
        private ComboBox<String> tables_combo;

        @FXML
        private TableView<?> tables;


    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
