package backend;

public class Cart {
    int product_id;
    double price;
    int volume;
    String name;

      public Cart(int product_id, String name, double price, int volume) {
        this.product_id = product_id;
        this.name=name;
        this.price = price;
        this.volume = volume;
    }

    public Cart(){
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

}