package manager;

import backend.ControlledScreen;
import backend.ScreensController;

public class ExpiredController implements ControlledScreen {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
