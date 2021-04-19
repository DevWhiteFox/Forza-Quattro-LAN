package gui;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * The type Grid.
 */
public class Grid extends JComponent {

    private int radius;
    private final double padding;
    private Boolean player;
    private boolean hovered;
    private Ellipse2D circle;
    private Ellipse2D border;

    /**
     * Instantiates a new Grid.
     */
    public Grid() {
        this.radius = 50;
        this.padding = 0.1;
        hovered = false;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Boolean player) {
        this.player = player;
    }

    /**
     * Sets is hovered.
     *
     * @param state the state
     */
    public void setIsHovered(boolean state) {
        hovered = state;
    }

    public void paint(Graphics g) {
        super.paint(g);
        init();
        drawDiskette(g);
    }

    public Dimension getPreferredSize() {
        int size = 2 * (radius);
        return new Dimension(size, size);
    }

    private void init(){
        Dimension size = getSize();

        radius = (int)(
                (Math.min(size.width, size.height) / 2)
                        -(radius*padding));

        circle = new Ellipse2D.Double(
                (size.width / 2) - radius,
                (size.height / 2) - radius,
                radius * 2, radius * 2);

        border = new Ellipse2D.Double(
                (size.width / 2) - radius - 4,
                (size.height / 2) - radius - 4,
                (radius+4) * 2, (radius+4) * 2);
    }

    private void drawDiskette(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Area circleArea = new Area(circle);
        Area borderArea = new Area(border);

        g2.setColor(new Color(0,60,200));
        borderArea.subtract(circleArea);
        g2.fill(borderArea);

        if(player != null) {
            if (player) g2.setColor(Color.YELLOW);
            else g2.setColor(Color.RED);
        }else if(hovered){
            g2.setColor(Color.LIGHT_GRAY);
        }else{
            g2.setColor(Color.DARK_GRAY);
        }

        g2.fill(circleArea);
        g2.setColor(Color.BLACK);
        g2.draw(circle);
    }
}