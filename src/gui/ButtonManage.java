package gui;

import javax.swing.*;

/**
 * The type Button manage.
 */
public class ButtonManage {
    /**
     * Simple style.
     *
     * @param button the button
     */
    public static void simpleStyle(JButton button){
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }
}
