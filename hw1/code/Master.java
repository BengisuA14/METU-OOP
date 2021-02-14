import java.awt.*;

public class Master extends AgentDecorator {
    public Master(double x, double y, Agent decoratedAgent) {
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

    // adds the master badge if stolen money exceeds 4000
    // then uses the draw method of decorated agent
    @Override
    public void draw(Graphics2D g2d) {
        if(decoratedAgent.getMoneyStolen() > 4000){
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(getAgentPosition().getIntX()+25, getAgentPosition().getIntY()-40,20,15);
        }
        decoratedAgent.draw(g2d);
    }

    // step the way basic agent steps
    @Override
    public void step() {
        decoratedAgent.step();
    }
}