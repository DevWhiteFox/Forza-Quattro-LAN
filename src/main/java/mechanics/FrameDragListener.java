package mechanics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Frame drag listener.
 */
public class FrameDragListener extends MouseAdapter {
    private final Window window;
    private Point mouseDownCompCoords = null;

    /**
     * Instantiates a new Frame drag listener.
     *
     * @param window the window
     */
    public FrameDragListener(Window window) {
        this.window = window;
    }

    public void mouseReleased(MouseEvent e) {
        mouseDownCompCoords = null;
    }

    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        Point currCords = e.getLocationOnScreen();
        window.setLocation(currCords.x - mouseDownCompCoords.x, currCords.y - mouseDownCompCoords.y);
    }
}
