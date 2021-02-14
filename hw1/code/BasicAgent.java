import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class BasicAgent extends Agent {

    public BasicAgent(double x, double y, int moneyStolen, Country country, String name) {
        super(x, y);
        this.moneyStolen = moneyStolen;
        this.country = country;
        this.name = name;
        this.state = new Rest(this);
        try {
            this.image = ImageIO.read(new File("images/" + name + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMoneyStolen() {
        return moneyStolen;
    }

    @Override
    public Position getAgentPosition() {
        return getPosition();
    }

    @Override
    public Country getAgentCountry() {
        return country;
    }

    // if the IA eats an order, it gains money depending on the current goldprice
    private void gainMoney(int amount){
        double liveGold = Common.getGoldPrice().getCurrentPrice();
        moneyStolen += (amount * liveGold);
    }

    //The following 3 functions are helpers to draw the IA
    private void writeName(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(name.toUpperCase(), getPosition().getIntX() , getPosition().getIntY() - 5);
    }

    private void writeState(Graphics2D g2d){
        int stateOffSet = 120;
        int xOffSet = 10;
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        if(state.getStateNo() == 0){ g2d.drawString("Rest", getPosition().getIntX() + xOffSet , getPosition().getIntY() +stateOffSet); }
        else if(state.getStateNo() == 1){ g2d.drawString("Shake", getPosition().getIntX() + xOffSet , getPosition().getIntY() +stateOffSet); }
        else if(state.getStateNo() == 2){ g2d.drawString("GotoXY", getPosition().getIntX() + xOffSet , getPosition().getIntY() +stateOffSet); }
        else if(state.getStateNo() == 3){ g2d.drawString("ChaseClosest", getPosition().getIntX() + xOffSet , getPosition().getIntY() +stateOffSet); }
    }
    public void writeMoney(Graphics2D g2d){
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("default", Font.BOLD, 20));
        g2d.drawString(Integer.toString(getMoneyStolen()), getPosition().getIntX() , getPosition().getIntY() + 140);
    }

    // checks if there is an order close to eat
    // if yes, add its money to IA's stolen money inventory
    // process it depending on whether it is a buy or a sell order
    private void processOrder(){
        Iterator itr = Common.getListOfOrders().iterator();
        while (itr.hasNext()) {
            Order o = (Order)itr.next();
            double distance = getPosition().distanceTo(o.getPosition().getX(), o.getPosition().getY());
            if(o.getCountry() != getAgentCountry() && distance < 70) {
                Country orderCountry = o.getCountry();
                // eats sell order
                gainMoney(o.getAmount());
                if(o.getType() == 0) {
                    orderCountry.loseGold(o.getAmount());
                    country.gainGold(o.getAmount());
                }
                // eats buys order
                else if(o.getType() == 1) {
                    orderCountry.loseCash(o.getAmount());
                    country.gainCash(o.getAmount());
                }
                itr.remove();
            }
        }
    }

    //draw the agent
    @Override
    public void draw(Graphics2D g2d) {
        writeName(g2d);
        g2d.drawImage(image, getPosition().getIntX() , getPosition().getIntY(), 100, 100, null);
        writeState(g2d);
        writeMoney(g2d);
    }

    // IA changes state at random
    // First, decide if the state will change by selecting random int between 0 and 80
    // If random number is 1, create another random int to decide the state
    private void changeState(){
        // 0 -> REST
        // 1 -> SHAKE
        // 2 -> GO_TO_XY
        // 3 -> CHASE_CLOSEST
        int changeState = Common.getRandomGenerator().nextInt(80);

        if(changeState == 1){
            int randomState = Common.getRandomGenerator().nextInt(5);
            if(randomState == 0) state = new Rest(this);
            else if(randomState == 1) state = new Shake(this);
            else if(randomState == 2) state = new GotoXY(this);
            else if(randomState == 3) state = new ChaseClosest(this);
        }
    }

    // steps to be taken by the agent
    // first eat order close to you
    // change state
    // move depending on the state
    @Override
    public void step() {
        processOrder();
        changeState();
        state.move();
    }
}