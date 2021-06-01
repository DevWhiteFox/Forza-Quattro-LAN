package html;

import mechanics.FrameDragListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The type Info game.
 */
public class InfoGame extends JDialog{
    private JPanel contentPane;
    private JButton quitButton;
    private JPanel htmlPage;

    private JFrame fqFrame;

    public InfoGame(JFrame fqFrame) {
        this.fqFrame = fqFrame;
        initDialog();
    }

    public void showInfo() {
        createUI();
        pack();
        setVisible(true);
    }

    public void initDialog() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setUndecorated(true);

        getRootPane().setDefaultButton(quitButton);
        setLocationRelativeTo(fqFrame);

        FrameDragListener moveDialog = new FrameDragListener(this);
        addMouseListener(moveDialog);
        addMouseMotionListener(moveDialog);

        quitButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
