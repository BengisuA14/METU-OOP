public abstract class OrderFactory {

    // to be implemented by BuyOrderFactory and SellOrderFactory
    public abstract Order createOrder(double x, double y, Country country);
}