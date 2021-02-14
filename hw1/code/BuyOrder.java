import java.awt.*;

public class BuyOrder extends Order {
    public BuyOrder(double x, double y, Country country) {
        super(x, y, country, 1);
    }

    //draw for buy orders
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("default", Font.BOLD, 15));
        g2d.fillOval(getPosition().getIntX(), getPosition().getIntY(), 20, 20);
        g2d.drawString(getCountry().getAbbreviation(), getPosition().getIntX(), getPosition().getIntY()-5);
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(getAmount()), getPosition().getIntX()+6, getPosition().getIntY()+15);
    }

    //move using the inherited move() method of Order
    @Override
    public void step() {
        move();
    }
}