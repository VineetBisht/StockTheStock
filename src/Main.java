import backend.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static String login="login";
    public static String loginFile="/employee/Login.fxml";
    public static String complaint="complaint";
    public static String complaintFile="/employee/Conplaint.fxml";
    public static String employeeMain="employeeMain";
    public static String employeeMainFile="/employee/EmployeeMain.fxml";
    public static String SignUp="signup";
    public static String SignUpFile="/employee/SignUp.fxml";
    public static String tables="tables";
    public static String tablesFile="/employee/Tables.fxml";
    public static String employeeControl="employeeControl";
    public static String employeeControlFile="/manager/EmployeeControl.fxml";
    public static String expired="expired";
    public static String expiredFile="/manager/Expired.fxml";
    public static String managerHome="managerHome";
    public static String managerHomeFile="/manager/ManagerHome.fxml";
    public static String manageStock="manageStock";
    public static String manageStockFile="/manager/ManageStock.fxml";
    public static String monthlyReport="monthlyReport";
    public static String monthlyReportFile="/manager/MonthlyReport.fxml";
    public static String weeklyNet="weeklyNet";
    public static String weeklyNetFile="/manager/WeeklyNet.fxml";

    @Override
    public void start(Stage stage) throws Exception{
        ScreensController mainContainer=new ScreensController();
        mainContainer.loadScreen(Main.login,Main.loginFile);
        mainContainer.loadScreen(Main.complaint,Main.complaintFile);
        mainContainer.loadScreen(Main.employeeControl,Main.employeeControlFile);
        mainContainer.loadScreen(Main.employeeMain,Main.employeeMainFile);
        mainContainer.loadScreen(Main.SignUp,Main.SignUpFile);
        mainContainer.loadScreen(Main.tables,Main.tablesFile);
        mainContainer.loadScreen(Main.expired,Main.expiredFile);
        mainContainer.loadScreen(Main.managerHome,Main.managerHomeFile);
        mainContainer.loadScreen(Main.manageStock,Main.manageStockFile);
        mainContainer.loadScreen(Main.monthlyReport,Main.monthlyReportFile);
        mainContainer.loadScreen(Main.weeklyNet,Main.weeklyNetFile);
        mainContainer.setScreen(Main.login);
        mainContainer.setStage(stage);

        Group root=new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
