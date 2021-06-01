package gui;

import componets.ButtonStyle;
import html.InfoGame;
import mechanics.FrameDragListener;
import mechanics.WinSystem;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.min;

/**
 * The type Forza iv.
 */
public class ForzaIV extends JFrame{
    protected JPanel infoPanel;
    protected JPanel gamePanel;
    protected JPanel mainPanel;
    protected JLabel turnLabel;
    protected JLabel tagLabel;
    protected JButton resetButton;
    protected JButton chiudiButton;
    protected JButton infoButton;

    protected int rows;
    protected int columns;
    protected int minMatch;
    protected boolean defaultPlayer;

    protected WinSystem winSystem;
    protected InfoGame infoGame;

    public ForzaIV(int rows, int columns, int minMatch, boolean defaultPlayer) {
        this.rows = rows;
        this.columns = columns;
        this.minMatch = minMatch;
        this.defaultPlayer = defaultPlayer;

        initFrame();
    }

    public ForzaIV(int rows, int columns) {
        this(rows,columns,4,true);
    }

    private void initFrame() {
        initFrameFormat();
        adaptWindow();
        closeWindowEventButton();
        setStyleCloseButton();
        FrameDragEvent();
        initMatch();

        setVisible(true);
        setResizable(false);
    }

    private void initMatch() {
        winSystem = new WinSystem(minMatch,columns);
    }

    public void initFrameFormat(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(gamePanel);
    }

    private void adaptWindow(){
        Dimension supportSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension originalSize = getSize();
        Dimension finalSize = new Dimension();

        finalSize.height = min(supportSize.height, originalSize.height);
        finalSize.width = min(supportSize.width, originalSize.width);

        setSize(finalSize);
    }

    private void closeWindowEventButton(){
        chiudiButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
    }

    private void setStyleCloseButton(){
        chiudiButton.setIcon(ImageManager.createImageIcon("close.png"));
        ButtonStyle.simpleStyle(chiudiButton);
    }

    private void FrameDragEvent(){
        FrameDragListener moveFrame = new FrameDragListener(this);
        addMouseListener(moveFrame);
        addMouseMotionListener(moveFrame);
    }

    public JButton getInfoButton() {
        return infoButton;
    }
}
