package html;

import gui.ForzaIV;
import mechanics.FrameDragListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * The type Info game.
 */
public class InfoGame extends JDialog{
    private JPanel contentPane;
    private JButton quitButton;
    private JPanel htmlPage;

    private static InfoGame instance;

    /**
     * Get instance info game.
     *
     * @return the info game
     */
    public static InfoGame getInstance(){
        return instance;
    }

    /**
     * Start.
     */
    public static void start() {
        instance = new InfoGame();
        instance.setContentPane(instance.contentPane);
        instance.setModal(true);
        instance.setResizable(false);
        instance.setUndecorated(true);
        instance.getRootPane().setDefaultButton(instance.quitButton);
        instance.createUI();
        instance.setLocationRelativeTo(ForzaIV.getInstance().getInfoButton());

        FrameDragListener moveDialog = new FrameDragListener(instance);
        instance.addMouseListener(moveDialog);
        instance.addMouseMotionListener(moveDialog);

        instance.quitButton.addActionListener(e -> instance.onCancel());

        // call onCancel() when cross is clicked
        instance.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        instance.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                instance.onCancel();
            }
        });

        // call onCancel() on ESCAPE
        instance.contentPane.registerKeyboardAction(e -> instance.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void createUI(){
        LayoutManager layout = new FlowLayout();
        htmlPage.setLayout(layout);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        java.net.URL url = classLoader.getResource("teach.html");

        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540,400));

        htmlPage.add(jScrollPane);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
