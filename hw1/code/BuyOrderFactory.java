public class BuyOrderFactory extends OrderFactory {
    // Buy order factory creates orders
    // overrides createOrder method of OrderFactory according to its specifications
    public Order createOrder(double x, double y, Country country){
        return new BuyOrder(x, y, country);
    }
}