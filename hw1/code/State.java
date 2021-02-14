public abstract class State {
    protected Agent agent;
    public State(Agent agent){
        this.agent = agent;
    }

    // to be implemented by each state
    public abstract void move();
    public abstract int getStateNo();

}