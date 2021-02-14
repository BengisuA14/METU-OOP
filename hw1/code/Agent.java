import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Agent extends Entity {

    protected int moneyStolen;
    protected Country country;
    protected String name;
    protected State state;
    protected int offsetX = 60;
    protected int offsetY = 10;
    protected BufferedImage image;

    public Agent(double x, double y) {
        super(x, y);
    }

    public abstract int getMoneyStolen();
    public abstract Position getAgentPosition();
    public abstract Country getAgentCountry();



}