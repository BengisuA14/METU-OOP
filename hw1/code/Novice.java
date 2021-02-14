import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Novice extends AgentDecorator {
    public Novice(double x, double y, Agent decoratedAgent) {
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

    // adds the novice badge if stolen money exceeds 2000
    // then uses the draw method of decorated agent
    @Override
    public void draw(Graphics2D g2d) {
        if(decoratedAgent.getMoneyStolen() > 2000){
            g2d.setColor(Color.WHITE);
            g2d.fillRect(getAgentPosition().getIntX(), getAgentPosition().getIntY()-40,20,15);
        }
        decoratedAgent.draw(g2d);
    }

    // step the way basic agent steps
    @Override
    public void step() {
        //System.out.println("novice" + getPosition().getIntX());
        decoratedAgent.step();
    }
}