package componets;

import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;

public class ButtonStyle {

    public static void simpleStyle(@NotNull JButton button){
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }
}
