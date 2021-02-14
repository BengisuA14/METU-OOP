import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Country extends Entity {

    private String name;
    private String abbreviation;
    private double cash;
    private int gold;
    private int offsetX = 60;
    private int offsetY = 170;
    private BufferedImage image;
    private OrderFactory sellOrderFactory= new SellOrderFactory();
    private OrderFactory buyOrderFactory = new BuyOrderFactory();

    public Country(double x, double y, String name, String abbreviation, double cash, int gold) {
        super(x, y);
        this.name = name;
        this.abbreviation = abbreviation;
        this.cash = cash;
        this.gold = gold;
        try {
            this.image = ImageIO.read(new File("images/" + name + ".jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() { return name; }

    // for drawing purposes
    public String getAbbreviation() { return abbreviation; }

    // if another country's IA eats the country's buy order
    // called by basicAgent
    public void loseCash(int amount) {
            double currentGoldPrice = Common.getGoldPrice().getCurrentPrice();
            double newCash = cash - (amount * currentGoldPrice);
            if(newCash >= 0) cash = newCash;
    }

    // if country's IA eats a sell order, country gains cash depending on the current gold price
    public void gainCash(int amount) {
        double currentGoldPrice = Common.getGoldPrice().getCurrentPrice();
        cash += (amount * currentGoldPrice);
    }

    // if another country's IA eats the country's sell order
    // called by basicAgent
    public void loseGold(int amount) {
        int newGold = gold - amount;
        if(newGold >= 0) gold = newGold;

    }

    public void gainGold(int amount) {
        gold += amount;
    }

    //calculate current dynamic worth of the country
    public double newDynamicWorth() {
        return cash + gold * Common.getGoldPrice().getCurrentPrice();
    }

    // draw country
    // helper function for draw
    private void drawCountryDetails(Graphics2D g2d){
        // draw country name
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(name, getPosition().getIntX() + offsetX + 5, getPosition().getIntY() + offsetY);

        // draw amount of gold
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(gold + " gold", getPosition().getIntX() + offsetX, getPosition().getIntY() + offsetY+25);

        // draw amount of cash
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(String.format("%.2f cash", cash), getPosition().getIntX() + offsetX, getPosition().getIntY() + offsetY+50);
        // draw dynamic worth
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(String.format("Worth: %.2f $", newDynamicWorth()), getPosition().getIntX() + offsetX, getPosition().getIntY() + offsetY+75);
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, getPosition().getIntX(), getPosition().getIntY(), 200, 150, null);
        drawCountryDetails(g2d);
    }

    // country creates orders from factory randomly
    // it firsts decide if it will generate an order (to slow the simulation down)
    // then creates an order random value to decide if it is a sell or buy order
    // adds it to the order list in Common
    @Override
    public void step() {
        int generateOrder = Common.getRandomGenerator().nextInt(300);
        int sellOrBuy = Common.getRandomGenerator().nextInt(2);
        Order order;
        if(generateOrder == 1){
            if(sellOrBuy == 1){
                order = sellOrderFactory.createOrder(getPosition().getIntX(), getPosition().getIntY(), this);
            }
            else{
                order = buyOrderFactory.createOrder(getPosition().getIntX(), getPosition().getIntY(), this);
            }
            Common.addOrder(order);
        }

    }
}