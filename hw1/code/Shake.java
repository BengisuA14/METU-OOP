public class Shake extends State {

    //has random speed between 0-3
    private int speed = Common.getRandomGenerator().nextInt(2);

    public Shake(Agent agent) {
        super(agent);
    }

    // as IA do not know it is state
    // uses this to draw State on screen
    @Override
    public int getStateNo() {
        return 1;
    }

    //makes random dispositions with small random speed
    @Override
    public void move() {
        Position  p = agent.getAgentPosition();
        int x = p.getIntX();
        int y = p.getIntY();

        // decides direction randomly
        int direction = Common.getRandomGenerator().nextInt(4);
        if(direction == 0) p.setX(x + speed);
        else if(direction == 1) p.setX(x - speed);
        else if(direction == 2) p.setY(y + speed);
        else if(direction == 3) p.setY(y - speed);
    }
}