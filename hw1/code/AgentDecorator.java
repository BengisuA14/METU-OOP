public abstract class AgentDecorator extends Agent {
    protected Agent decoratedAgent;

    public AgentDecorator(double x, double y, Agent decoratedAgent) {
        super(x,y);
        this.decoratedAgent = decoratedAgent;
    }


}
