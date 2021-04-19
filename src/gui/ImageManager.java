package gui;

import javax.swing.*;

/**
 * The type Image manager.
 */
public class ImageManager {
    /**
     * Create image icon image icon.
     *
     * @param path the path
     * @return the image icon
     */
    public static ImageIcon createImageIcon(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        java.net.URL imgURL = classLoader.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
