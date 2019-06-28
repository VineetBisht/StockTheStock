package manager;

import backend.ControlledScreen;
import backend.ScreensController;

public class EmployeeControlController implements ControlledScreen {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
    myController=screenPage;
    }
}
