package manager;

import backend.ControlledScreen;
import backend.ScreensController;

public class ManagerHomeController implements ControlledScreen {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
