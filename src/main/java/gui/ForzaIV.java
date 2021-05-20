package gui;

import componets.ButtonStyle;
import mechanics.FrameDragListener;
import mechanics.WinSystem;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.min;

/**
 * The type Forza iv.
 */
public class ForzaIV extends JFrame{
    private JPanel infoPanel;
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JLabel turnLabel;
    private JLabel tagLabel;
    private JButton resetButton;
    private JButton chiudiButton;
    private JButton infoButton;

    private int rows;
    private int columns;
    private int minMatch;
    private boolean defaultPlayer;

    TurnPlayer turnPlayer = new TurnPlayer();
    WinSystem winSystem = new WinSystem();

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
        turnPlayer.setTurn(defaultPlayer);
        turnPlayer.setTagLabel((byte) 0);

        winSystem.setMinMatch(minMatch);
        winSystem.setColumns(columns);
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
}
