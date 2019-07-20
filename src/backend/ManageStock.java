package backend;

import java.io.File;

public class ManageStock {
    String product_id;
    String name;
    double price;
    int volume;
    double profit_percent;

    File file;

    String distributor_id;
    String added_on, expiry_date;

    public ManageStock(String product_id, String name, double price, int volume, String distributor_id, String added_on,
                       String expiry_date, double profit_percent) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.added_on = added_on;
        this.expiry_date = expiry_date;
        this.distributor_id = distributor_id;
        this.profit_percent = profit_percent;
    }

    public ManageStock(String product_id, String name, double price, int volume, String distributor_id, String added_on,
                       String expiry_date, File file, double profit_percent) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.added_on = added_on;
        this.expiry_date = expiry_date;
        this.distributor_id = distributor_id;
        this.file = file;
        this.profit_percent = profit_percent;
    }

    public ManageStock(){

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getDistributor_id() {
        return distributor_id;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setDistributor_id(String distributor_id) {
        this.distributor_id = distributor_id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public double getProfit_percent() {
        return profit_percent;
    }

    public void setProfit_percent(double profit_percent) {
        this.profit_percent = profit_percent;
    }
}