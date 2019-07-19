package employee;

import backend.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

public class TransactionController implements ControlledScreen, Initializable {
    ScreensController myController;
    EmployeeDB edb;
    ObservableList<Cart> list;

    @FXML
    private TextField txt_product;

    @FXML
    private TextField txt_qty;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_total;

    @FXML
    private TableView<Cart> table;

    @FXML
    private TableColumn<Cart, Integer> id;

    @FXML
    private TableColumn<Cart, String> name;

    @FXML
    private TableColumn<Cart, Double> price;

    @FXML
    private TableColumn<Cart, Integer> qty;

    @FXML
    private Label product_err;

    @FXML
    private Label price_err;

    @FXML
    private Label qty_err;

    @FXML
    void about(ActionEvent event) {
    }

    @FXML
    void add(ActionEvent event) {
        if(txt_qty.getText() == null || txt_product.getText()==null||txt_qty.getText().isEmpty() || txt_product.getText().isEmpty()) {
            showErrors();
            return;
        }
        hideErrors();
        Cart obj = edb.findObject(Integer.parseInt(txt_product.getText()),Integer.parseInt(txt_qty.getText()));
        if (obj == null) {
            txt_price.setText("");
        }else{
            list.add(edb.findObject(Integer.parseInt(txt_product.getText()), Integer.parseInt(txt_qty.getText())));
        }
    }

    @FXML
    void checkout(ActionEvent event) {
        if(list.toArray().length==0){
            new Alert(Alert.AlertType.WARNING,"Cart Empty!").showAndWait();
            return;
        }

        edb.update(list.toArray());
        new Alert(Alert.AlertType.INFORMATION,"Checkout Successful. Bill printed. ").showAndWait();
        try{
            JasperCompileManager.compileReportToFile(Files.jasperResourcePath,Files.jasperPath+"Bill.jasper");
            ArrayList<Cart> arlist=new ArrayList<>(list);
            JRBeanCollectionDataSource beanCollectionDataSource= new JRBeanCollectionDataSource(arlist);
            Map parameters=new HashMap();
            parameters.put("ds",beanCollectionDataSource);
            JasperPrint js = JasperFillManager.fillReport(Files.jasperPath+"Bill.jasper",parameters,beanCollectionDataSource);
            OutputStream os=new FileOutputStream(new File(Files.jasperPath+new Date().getTime()+".pdf"));
            JasperExportManager.exportReportToPdfStream(js,os);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void remove(ActionEvent event) {
        list.remove(table.getSelectionModel().getSelectedItem());
    }

    @FXML
    void update(ActionEvent event) {
        int index= table.getSelectionModel().getSelectedIndex();
        list.remove(table.getSelectionModel().getSelectedItem());
        list.add(index, edb.findObject(Integer.parseInt(txt_product.getText()), Integer.parseInt(txt_qty.getText())));
    }

    @FXML
    void close(ActionEvent event) {
        myController.getStage().hide();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list= FXCollections.observableArrayList();
        id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        qty.setCellValueFactory(new PropertyValueFactory<>("volume"));

        edb = new EmployeeDB();
        list.addListener(new ListChangeListener<Cart>() {
            @Override
            public void onChanged(Change<? extends Cart> change) {
                if (list != null) {
                    table.setItems(list);
                    double total=0;
                    for(Cart i : list){
                        total += (i.getVolume() * i.getPrice());
                    }
                    txt_total.setText( "$"+total );
                }
            }
        });

       table.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               if(table.getSelectionModel().getSelectedItem() != null){
                   txt_product.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getProduct_id()));
                   txt_qty.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getVolume()));
               }
           }
       });

        txt_qty.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty() && !txt_product.getText().isEmpty()) {
                Cart item=edb.findObject(Integer.parseInt(txt_product.getText()),Integer.parseInt(txt_qty.getText()));
                txt_price.setText(String.valueOf(item.getVolume()*item.getPrice()));
            }else {
                txt_price.setText("");
            }
        });

        txt_product.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty() && !txt_qty.getText().isEmpty()) {
                Cart item=edb.findObject(Integer.parseInt(txt_product.getText()),Integer.parseInt(txt_qty.getText()));
                txt_price.setText(String.valueOf(item.getVolume()*item.getPrice()));
            }else {
                txt_price.setText("");
            }
        });

    }

    void showErrors() {
        qty_err.setVisible(true);
        price_err.setVisible(true);
        product_err.setVisible(true);
    }

    void hideErrors() {
        qty_err.setVisible(false);
        price_err.setVisible(false);
        product_err.setVisible(false);
    }

}
