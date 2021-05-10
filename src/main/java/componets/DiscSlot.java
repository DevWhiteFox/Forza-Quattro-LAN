package componets;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * The type DiscSlot.
 */
public class DiscSlot extends JComponent {

    private int radius;
    private int paddingBorder = 4;
    private int offsetBorder = 4;
    private final double padding;
    private Boolean player;
    private boolean hovered;
    private Ellipse2D circle;
    private Ellipse2D border;

    public DiscSlot() {
        this(50,0.1,false);
    }

    public DiscSlot(int radius, double padding, boolean hovered) {
        this.radius = radius;
        this.padding = padding;
        this.hovered = hovered;
    }

    public void setPlayer(Boolean player) {
        this.player = player;
    }

    public void setIsHovered(boolean state) {
        hovered = state;
    }

    public void paint(Graphics g) {
        super.paint(g);
        buildCellComponent();
        drawCell(g);
    }

    public Dimension getPreferredSize() {
        int size = radius + radius;
        return new Dimension(size, size);
    }

    private void buildCellComponent(){
        Dimension size = getSize();

        radius = (int)( (Math.min(size.width, size.height) / 2)
                -(radius*padding));

        double sizeWidth = (double)size.width / 2.0;
        double sizeHeight = (double)size.height / 2.0;
        double diameter = radius + radius;
        double diameterBorder = (radius + paddingBorder) + (radius + paddingBorder);

        circle = new Ellipse2D.Double(
                sizeWidth - radius,
                sizeHeight - radius,
                diameter, diameter);

        border = new Ellipse2D.Double(
                sizeWidth - radius - offsetBorder,
                sizeHeight - radius - offsetBorder,
                diameterBorder, diameterBorder);
    }

    private void drawCell(Graphics g){
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
            g2.setColor(new Color(189, 189, 189, 255));
        }else{
            g2.setColor(new Color(128, 128, 128, 128));
        }

        g2.fill(circleArea);
        g2.setColor(Color.BLACK);
        g2.draw(circle);
    }
}