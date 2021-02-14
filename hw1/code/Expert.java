import java.awt.*;

public class Expert extends AgentDecorator {
    public Expert(double x, double y, Agent decoratedAgent) {
        super(x, y, decoratedAgent);
    }

    //helper methods for draw
    @Override
    public int getMoneyStolen() {
        return decoratedAgent.getMoneyStolen();
    }

    @Override
    public Position getAgentPosition() {
        return decoratedAgent.getAgentPosition();
    }

    @Override
    public Country getAgentCountry() {
        return decoratedAgent.getAgentCountry();
    }

    // adds the expert badge if stolen money exceeds 6000
    // then uses the draw method of decorated agent
    @Override
    public void draw(Graphics2D g2d) {
        if(decoratedAgent.getMoneyStolen() > 6000){
            g2d.setColor(Color.RED);
            g2d.fillRect(getAgentPosition().getIntX() + 50, getAgentPosition().getIntY()-40,20,15);
        }
        decoratedAgent.draw(g2d);
    }
    // step the way basic agent steps
    @Override
    public void step() {
        decoratedAgent.step();
    }
}