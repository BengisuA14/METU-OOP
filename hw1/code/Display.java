import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    public Display() { this.setBackground(new Color(180, 180, 180)); }

    @Override
    public Dimension getPreferredSize() { return super.getPreferredSize(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Common.getGoldPrice().draw((Graphics2D) g);
        g.drawLine(0, Common.getUpperLineY(), Common.getWindowWidth(), Common.getUpperLineY());

        // call draw methods for countries
        Common.getChina().draw((Graphics2D) g);
        Common.getUSA().draw((Graphics2D) g);
        Common.getIsrael().draw((Graphics2D) g);
        Common.getRussia().draw((Graphics2D) g);
        Common.getTurkey().draw((Graphics2D) g);

        // call draw methods for IA
        Common.getMSS().draw((Graphics2D) g);
        Common.getCIA().draw((Graphics2D) g);
        Common.getMOSSAD().draw((Graphics2D) g);
        Common.getSVR().draw((Graphics2D) g);
        Common.getMIT().draw((Graphics2D) g);

        // call draw methods for orders
        for(Order order : Common.getListOfOrders()){
            order.draw((Graphics2D) g);
        }



    }
}