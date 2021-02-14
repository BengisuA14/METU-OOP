import java.util.ArrayList;
import java.util.Iterator;

public class ChaseClosest extends State {
    private double speed = Common.getRandomGenerator().nextDouble()*2.5;
    public ChaseClosest(Agent agent) {
        super(agent);
    }

    // as IA do not know it is state
    // uses this to draw State on screen
    @Override
    public int getStateNo() {
        return 3;
    }

    // finds the closest order by iterating over the order list
    private Order findClosestOrder() {
        Position  p = agent.getAgentPosition();
        double min = Double.MAX_VALUE;
        Order minOrder = null;
        Iterator itr = Common.getListOfOrders().iterator();
        while (itr.hasNext()) {
            Order o = (Order)itr.next();
            double distance = p.distanceTo(o.getPosition().getX(), o.getPosition().getY());
            if(min > distance){
                min = distance;
                minOrder = o;
            }
        }
        return minOrder;
    }

    // finds closest order
    // if an order exists, moves towards there
    // otherwise does not move
    @Override
    public void move() {
        Position  p = agent.getAgentPosition();
        Order o = findClosestOrder();
        double x = p.getX();
        double y = p.getY();

        if(o != null) {
            if(o.getPosition().getX() > x) p.setX(x + speed);
            else p.setX(x - speed);

            if(o.getPosition().getY() > y) p.setY(y + speed);
            else p.setY(y - speed);
        }
    }
}