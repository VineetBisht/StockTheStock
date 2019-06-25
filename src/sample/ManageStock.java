package sample;

public class ManageStock {
    int product_id;
    String name;
    double price;
    int volume;
    int distributor_id;

    public ManageStock(int product_id, String name, double price, int volume, int distributor_id) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.distributor_id = distributor_id;
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

    public int getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }
}