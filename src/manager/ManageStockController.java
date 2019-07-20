package manager;

import backend.ControlledScreen;
import backend.ManageStock;
import backend.ManageStockDB;
import backend.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.ImageView ;
import javafx.scene.image.Image;
//import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManageStockController implements Initializable, ControlledScreen {

    ScreensController myController;
    //---------------------------Controller for ManageStock Page(variables + onAction methods)--------------------------
    @FXML
    private AnchorPane ManagerMain;
    @FXML
    private PieChart f_pieChart;
    @FXML
    private ImageView a_imageView, f_imageView, u_imageView;
    @FXML
    private ImageView a_profitLogo, u_profitLogo;
    @FXML
    private ImageView f_graphLogo;
    @FXML
    private TextField a_idField, d_idField, f_idField, u_idField;
    @FXML
    private TextField a_nameField, u_nameField;
    @FXML
    private TextField a_priceField, u_priceField;
    @FXML
    private TextField a_volumeField, u_volumeField;
    @FXML
    private TextField a_distributor_idField, u_distributor_idField;
    @FXML
    private TextField a_profitField, u_profitField;
    @FXML
    private TableView<ManageStock> viewTable, findTable, expiredTable;
    @FXML
    private TableColumn<ManageStock, Integer> f_idColumn, v_idColumn, e_idColumn;
    @FXML
    private TableColumn<ManageStock, String> f_nameColumn, v_nameColumn, e_nameColumn;
    @FXML
    private TableColumn<ManageStock, Integer> f_volumeColumn, v_volumeColumn, e_volumeColumn;
    @FXML
    private TableColumn<ManageStock, Double> f_priceColumn, v_priceColumn, e_priceColumn;
    @FXML
    private TableColumn<ManageStock, String> f_addedOn, v_addedOn, e_addedOn;
    @FXML
    private TableColumn<ManageStock, String> f_expiryDate, v_expiryDate, e_expiryDate;
    @FXML
    private TableColumn<ManageStock, String> f_distributorColumn, v_distributorColumn, e_distributorColumn;
    @FXML
    private DatePicker a_addedOn, a_expiryDate, u_addedOn, u_expiryDate;
    @FXML
    private Tab viewTab, expiredTab;

    ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieDefaultList = FXCollections.observableArrayList();


    Tooltip tool = new Tooltip();
    private FileInputStream fio;
    private File file;
    Image defaultImage, toolLogo;     //To hold default image
    int result = 0, result2 = 0;
    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
    Alert alert2 = new Alert(Alert.AlertType.ERROR);


    //This method will run whenever the stage of this gui will be loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Populating table views
        populate(v_idColumn, v_nameColumn, v_priceColumn, v_volumeColumn, v_addedOn, v_expiryDate, v_distributorColumn);
        populate(f_idColumn, f_nameColumn, f_priceColumn, f_volumeColumn, f_addedOn, f_expiryDate, f_distributorColumn);
        populate(e_idColumn, e_nameColumn, e_priceColumn, e_volumeColumn, e_addedOn, e_expiryDate, e_distributorColumn);

        //setting current date in date picker fields
        a_addedOn.setValue(LocalDate.now());
        u_addedOn.setValue(LocalDate.now());

        //Setting place holders
        a_idField.setPromptText("Enter product id");
        a_nameField.setPromptText("Enter product name");
        a_priceField.setPromptText("Enter product price");
        a_volumeField.setPromptText("Enter product volume");
        a_expiryDate.setPromptText("Select Date");
        a_distributor_idField.setPromptText("Enter distributor id");
        a_profitField.setPromptText("Enter profit % for product");

        d_idField.setPromptText("Enter product id");

        f_idField.setPromptText("Enter product id");

        u_idField.setPromptText("Enter product id");
        u_nameField.setPromptText("Enter product name");
        u_priceField.setPromptText("Enter product price");
        u_volumeField.setPromptText("Enter product volume");
        u_expiryDate.setPromptText("Select Date");
        u_distributor_idField.setPromptText("Enter distributor id");
        u_profitField.setPromptText("Enter profit % for product");

        //Default image for image view
        defaultImage = new Image("IMG/no-image.png");
        a_imageView.setImage(defaultImage);
        f_imageView.setImage(defaultImage);
        u_imageView.setImage(defaultImage);

        //profitLogo image set
        toolLogo = new Image("IMG/logo_question.png");
        a_profitLogo.setImage(toolLogo);
        u_profitLogo.setImage(toolLogo);
        f_graphLogo.setImage(toolLogo);


        //ToolTip for profit
//        final Tooltip profit = new Tooltip();
        String text = "For: \n\t0 to 75$ - 2.5% \n\t75 to 150$ - 4% \n\t150 to 500$ - 8% \n\t500 to 1500$ - 13%";
        Tooltip.install(a_profitLogo, new Tooltip(text));
        Tooltip.install(u_profitLogo, new Tooltip(text));

        //ToolTip for profit
        String graphText = "This graph gives graphical representation\nof how many products sold " +
                "vs how many \ntimes complaints received for that\nparticular product.";
        Tooltip.install(f_graphLogo, new Tooltip(graphText));


        //Default PieChart
        int complaintDefault = 1, soldDefault = 1;

        pieDefaultList.add(new PieChart.Data("Sold: " + 0, soldDefault));
        pieDefaultList.add(new PieChart.Data("Complaints: " + 0, complaintDefault));

        f_pieChart.setData(pieDefaultList);
    }

    //used for setting defaults in prompt boxes
    public void setAlert(){
        alert1.setTitle("Stock Records");
        alert1.setHeaderText(null);
        alert2.setTitle("Stock Records");
        alert2.setHeaderText(null);
    }

    //This method will run whenever add button is clicked on add item tab
    public void add(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();

        ManageStock snew = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;

        s.setProduct_id(a_idField.getText());
        s = sdb.fDelete(s);
        if(s != null){
            s.setProduct_id(a_idField.getText());
            s.setVolume(Integer.parseInt(a_volumeField.getText()));
            result = sdb.addToExisting(s);
            if(result != 0){
                alert1.setContentText("Volume for item with id " + a_idField.getText() + " is updated successfully");
                reset(actionEvent);
                alert1.showAndWait();
            }

        }else{
            if(a_idField.getText() == null || a_idField.getText().trim().isEmpty()) {
                alert2.setContentText("Please provide item Id Number");
                alert2.showAndWait();
            } else if(a_nameField.getText() == null || a_nameField.getText().trim().isEmpty()){
                alert2.setContentText("Please provide item Name");
                alert2.showAndWait();
            }
//        else if(isNumeric(a_nameField)){
//            alert2.setContentText("Only characters are allowed for name");
//            alert2.showAndWait();
//        }
            else if(a_priceField.getText() == null || a_priceField.getText().trim().isEmpty()){
                alert2.setContentText("Please provide item price");
                alert2.showAndWait();
            }else if(isInt(a_priceField)){
                alert2.setContentText("Only numbers are allowed for price");
                alert2.showAndWait();
            }else if(a_volumeField.getText() == null || a_volumeField.getText().trim().isEmpty()){
                alert2.setContentText("Please provide item total volume");
                alert2.showAndWait();
            }else if(isInt(a_volumeField)){
                alert2.setContentText("Only numbers are allowed for volume");
                alert2.showAndWait();
            }else if(a_addedOn.getValue() == null){
                alert2.setContentText("Please provide date for the item");
                alert2.showAndWait();
            }else if(a_expiryDate.getValue() == null){
                alert2.setContentText("Please provide expiry date for the item");
                alert2.showAndWait();
            }else if(a_distributor_idField.getText() == null || a_distributor_idField.getText().trim().isEmpty()){
                alert2.setContentText("Please provide distributor name for item");
                alert2.showAndWait();
            }else{
                snew.setProduct_id(a_idField.getText());
                snew.setName(a_nameField.getText());
                snew.setPrice(Double.parseDouble(a_priceField.getText()));
                snew.setVolume(Integer.parseInt(a_volumeField.getText()));
                snew.setDistributor_id(a_distributor_idField.getText());
                snew.setAdded_on(a_addedOn.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
                snew.setExpiry_date(a_expiryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
                snew.setProfit_percent(Double.parseDouble(a_profitField.getText()));
                snew.setFile(file);

                result = sdb.addItem(snew);
                result2 = sdb.addItemIdToProductCounter(snew);      //Adding product id to productCounter table
                if(result != 0 && result2 != 0){
                    alert1.setContentText("Details for item " + a_nameField.getText() + " added successfully");
                    reset(actionEvent);
                    alert1.showAndWait();
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }
        }


    }

    //this method is used to check if added value for price or volume is integer or not
    private boolean isInt(TextField fieldInput){
        try{
            int input = Integer.parseInt(fieldInput.getText());

            return false;
        }catch(NumberFormatException e){

            return true;
        }
    }

//    public static boolean isNumeric(TextField fieldInput) {
//        boolean isNumeric = true;
//        String validateString = fieldInput.getText();
//
//        for(int i = 0; i < validateString.length(); i++){
//            if (validateString.charAt(i). "[0-9]+") {
//                isNumeric = false;
//            }
//        }
//
//        return isNumeric;
//    }

    //This method is being used to reset the fields in respective table views
    public void reset(ActionEvent actionEvent) {
        //add tab
        a_idField.setText("");
        a_nameField.setText("");
        a_priceField.setText("");
        a_volumeField.setText("");
        a_distributor_idField.setText("");
//        a_addedOn.getEditor().clear();
        a_expiryDate.getEditor().clear();
        a_addedOn.setValue(LocalDate.now());
        a_imageView.setImage(defaultImage);
        a_profitLogo.setImage(toolLogo);
//        a_addedOn.setValue(LocalDate.now());


        //update tab
        u_idField.setText("");
        u_nameField.setText("");
        u_priceField.setText("");
        u_volumeField.setText("");
        u_distributor_idField.setText("");
//        u_addedOn.getEditor().clear();
        u_expiryDate.getEditor().clear();
        u_addedOn.setValue(LocalDate.now());
        u_profitLogo.setImage(toolLogo);
        u_imageView.setImage(defaultImage);
//        u_addedOn.setValue(LocalDate.now());

        //find tab
        f_idField.setText("");
        f_pieChart.setData(pieDefaultList);
        f_imageView.setImage(defaultImage);
        findTable.setItems(null);

        //delete tab
        d_idField.setText("");
    }

    //This method will be used to delete records from the database for stocks
    public void delete(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(d_idField.getText() == null || d_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(d_idField.getText());
            s = sdb.fDelete(s);
            if(s != null){
                String name = s.getName();
                result = sdb.delete(s);
                if(result != 0){
                    alert1.setContentText("Record for item " + name + " is deleted successfully");
                    alert1.showAndWait();
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }
            }else{
                alert2.setContentText("There is no Item with Item Id: " + a_idField.getText());
                alert2.showAndWait();
            }
        }
    }

    //This method is being used to search/find stock on the basis of
    // provided product id and also to generate graph
    public void find(ActionEvent actionEvent) throws IOException {
        int soldCounter = 0, complaintCounter = 0;
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        if(f_idField.getText() == null || f_idField.getText().trim().isEmpty()) {
            alert2.setContentText("Please provide item Id Number");
            alert2.showAndWait();
        } else{
            s.setProduct_id(f_idField.getText());
            s = sdb.fDelete(s);
            if(s != null){
                ObservableList<ManageStock> obj = sdb.find(s);
                InputStream is = sdb.findImage(s);
                OutputStream out = new FileOutputStream(new File("productImage.jpg"));
                byte[] data = new byte[1024];
                int size = 0;
                while((size = is.read(data)) != -1){
                    out.write(data, 0, size);
                }
                out.close();


                Image image = new Image("file:productImage.jpg", 100, 150, true,true);
                f_imageView.setImage(image);
                if(obj != null){
                    findTable.setItems(obj);
                }
                else{
                    alert2.setContentText("Something unexpected error occurred");
                    alert2.showAndWait();
                }

                //setting graph
                int[] parameterCounter = sdb.setGrapgh(s);
                soldCounter = parameterCounter[0];
                complaintCounter = parameterCounter[1];

                pieList = FXCollections.observableArrayList();
                pieList.add(new PieChart.Data("Sold: " + soldCounter, soldCounter));
                pieList.add(new PieChart.Data("Complaints: " + complaintCounter, complaintCounter));
                f_pieChart.setData(pieList);

            }else{

                reset(actionEvent);
                alert2.setContentText("There is no Item with Item Id: " + f_idField.getText());
                alert2.showAndWait();
            }
        }
    }

    //This method is being used to update records for stock in the database
    public void update(ActionEvent actionEvent) {
        ManageStock s = new ManageStock();
        ManageStockDB sdb = new ManageStockDB();
        setAlert();
        result = 0;
        s.setProduct_id(u_idField.getText());
        s = sdb.fDelete(s);
        if(s != null) {
            if(u_idField.getText() == null || u_idField.getText().trim().isEmpty()) {
                alert2.setContentText("Please provide item Id Number");
                alert2.showAndWait();
            } else if(u_nameField.getText() == null || u_priceField.getText() == null ||
                    u_volumeField.getText() == null || u_addedOn.getValue() == null ||
                    u_expiryDate.getValue() == null || u_distributor_idField.getText() == null ||
                    u_nameField.getText().trim().isEmpty()){
                alert1.setContentText("Nothing is updated for item with id " + u_idField.getText());
                alert1.showAndWait();
            }else if(u_priceField.getLength() > 0 && isInt(u_priceField)){
                alert2.setContentText("Only numbers are allowed for price");
                alert2.showAndWait();
            }else if(u_volumeField.getLength()> 0 && isInt(u_volumeField)){
                alert2.setContentText("Only numbers are allowed for volume");
                alert2.showAndWait();
            } else{
                s.setProduct_id(u_idField.getText());
                s = sdb.fDelete(s);
                if(s != null){
                    s.setProduct_id(u_idField.getText());
                    s.setName(u_nameField.getText());
                    s.setPrice(Double.parseDouble(u_priceField.getText()));
                    s.setVolume(Integer.parseInt(u_volumeField.getText()));
                    s.setDistributor_id(u_distributor_idField.getText());
                    s.setAdded_on(u_addedOn.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
                    s.setExpiry_date(u_expiryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
                    s.setProfit_percent(Double.parseDouble(u_profitField.getText()));
                    s.setFile(file);
                    result = sdb.update(s);
                    if(result != 0){
                        alert1.setContentText("Record for Item with Item Id: " + u_idField.getText() + " updated successfully");
                        reset(actionEvent);
                        alert1.showAndWait();
                    }
                    else{
                        alert2.setContentText("Something unexpected error occurred");
                        alert2.showAndWait();
                    }
                }

            }

        }
        else{
            alert2.setContentText("There is no Item with Item Id: " + u_idField.getText());
            alert2.showAndWait();
        }

    }


    //This method is being used to list database records for stock on the view stock tab of table view
    @FXML
    public void list(Event ev) {
        setAlert();
        if (viewTab.isSelected()) {
            System.out.println("Tab is Selected");
            ManageStock s = new ManageStock();
            ManageStockDB sdb = new ManageStockDB();
            ObservableList<ManageStock> obj = sdb.list();
            if(obj != null){
                viewTable.setItems(obj);
            }
            else{
                alert2.setContentText("There is no record in the database");
                alert2.showAndWait();
            }
        }
    }

    //This method is being used to list database records for stock that are expired on the
    // expired stock tab of table view
    @FXML
    public void expiredList(Event event) {
        setAlert();
        if (expiredTab.isSelected()) {
            System.out.println("Tab is Selected");
            ManageStock s = new ManageStock();
            ManageStockDB sdb = new ManageStockDB();
            ObservableList<ManageStock> obj = sdb.expiredList();
            if(obj != null){
                expiredTable.setItems(obj);
            }
            else{
                alert2.setContentText("There is no record in the database");
                alert2.showAndWait();
            }
        }
    }


    //This method is being used in initialize method to populate database records
    private void populate(TableColumn<ManageStock, Integer> idColumn
            , TableColumn<ManageStock, String> nameColumn
            , TableColumn<ManageStock, Double> priceColumn
            , TableColumn<ManageStock, Integer> volumeColumn
            , TableColumn<ManageStock, String> addedOn
            , TableColumn<ManageStock, String> expiryDate
            , TableColumn<ManageStock, String> distributorColumn) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        addedOn.setCellValueFactory(new PropertyValueFactory<>("added_on"));
        expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));
        distributorColumn.setCellValueFactory(new PropertyValueFactory<>("distributor_id"));
    }

    //This method is used to handle/choose image for a particular product on add item tab of table view
    public void fileChooser(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose product image");

        Stage stage = (Stage)ManagerMain.getScene().getWindow();

        file = fileChooser.showOpenDialog(stage);


        if(file != null){
            Image image = new Image(file.toURI().toString());
            a_imageView.setImage(image);
        }

    }

    //This method is used to handle/choose image for a particular product on update item tab of table view
    public void fileChooser2(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose product image");

        Stage stage = (Stage)ManagerMain.getScene().getWindow();

        file = fileChooser.showOpenDialog(stage);


        if(file != null){
            Image image = new Image(file.toURI().toString());
            u_imageView.setImage(image);
        }

    }

    //This method is being used to update date
    public void updateDate(Event event) {
        u_addedOn.setValue(LocalDate.now());
    }


    @Override
    public void setScreenParent(ScreensController screenPage) {
        screenPage=myController;
    }
}
