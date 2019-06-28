package employee;

import backend.ControlledScreen;
import backend.ScreensController;

public class SignUpController implements ControlledScreen {
    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
