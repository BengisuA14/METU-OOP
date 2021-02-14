public class GotoXY extends State {

    //chooses a random position between between window sizes
    private Position xy = new Position(Common.getRandomGenerator().nextInt(1450), Common.getRandomGenerator().nextInt(520)+60);

    // has a random speed, could not vary it too much, otherwise it becomes really fast
    private int speed = Common.getRandomGenerator().nextInt(3);

    public GotoXY(Agent agent) {
        super(agent);
    }

    // as IA do not know it is state
    // uses this to draw State on screen
    @Override
    public int getStateNo() {
        return 2;
    }

    //
    @Override
    public void move() {
        Position  p = agent.getAgentPosition();
        int x = p.getIntX();
        int y = p.getIntY();

        //move towards the selected position in each step
        if(xy.getIntX() > x) p.setX(x + speed);
        else p.setX(x - speed);

        if(xy.getIntY() > y) p.setY(y + speed);
        else p.setY(y - speed);

    }
}