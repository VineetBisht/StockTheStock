import java.io.File;

public class ManageStock {
    int product_id;
    String name;
    double price;
    int volume;

    File file;

    String distributor_id;
    String added_on, expiry_date;

    public ManageStock(int product_id, String name, double price, int volume, String distributor_id, String added_on,
                       String expiry_date) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.added_on = added_on;
        this.expiry_date = expiry_date;
        this.distributor_id = distributor_id;
    }

    public ManageStock(int product_id, String name, double price, int volume, String distributor_id, String added_on,
                       String expiry_date, File file) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.added_on = added_on;
        this.expiry_date = expiry_date;
        this.distributor_id = distributor_id;
        this.file = file;
    }

    public ManageStock(){

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
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
}