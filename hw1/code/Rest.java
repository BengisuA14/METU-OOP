public class Rest extends State {

    public Rest(Agent agent) {
        super(agent);
    }

    // called by IA to write State on screen
    @Override
    public int getStateNo() {
        return 0;
    }

    // no movement in Rest State
    @Override
    public void move() {

    }
}