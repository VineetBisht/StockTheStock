package manager;

import backend.ControlledScreen;
import backend.ScreensController;

public class MonthlyReportController implements ControlledScreen {

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }
}
