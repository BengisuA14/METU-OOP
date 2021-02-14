public class SellOrderFactory extends OrderFactory {
    // Buy order factory creates orders
    // overrides createOrder method of OrderFactory according to its specifications
    public Order createOrder(double x, double y, Country country){
        return new SellOrder(x, y, country);
    }
}