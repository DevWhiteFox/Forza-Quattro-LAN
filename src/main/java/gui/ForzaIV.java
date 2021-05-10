package gui;

import mechanics.FrameDragListener;
import mechanics.WinSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Forza iv.
 */
public class ForzaIV {
    private JPanel infoPanel;
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JLabel turnLabel;
    private JLabel tagLabel;
    private JButton resetButton;
    private JButton chiudiButton;
    private JButton infoButton;
    private static ForzaIV instance;

    private int rows;
    private int columns;
    private int minMatch;
    private boolean defaultPlayer;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ForzaIV getInstance() {
        return instance;
    }

    /**
     * Init fiv.
     *
     * @param rows          the rows
     * @param columns       the columns
     * @param minMatch      the min match
     * @param defaultPlayer the default player
     */
    public static void initFIV(int rows, int columns, int minMatch, boolean defaultPlayer) {
        instance = new ForzaIV();
        instance.rows = rows;
        instance.columns = columns;
        instance.minMatch = minMatch;
        instance.defaultPlayer = defaultPlayer;
    }

    /**
     * Start.
     */
    public static void start() {
        JFrame frame = new JFrame();

        frame.setContentPane(instance.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(instance.gamePanel);

        adaptWindow(frame);

        closeWindow(frame);
        instance.chiudiButton.setIcon(ImageManager.createImageIcon("close.png"));
        ButtonManage.simpleStyle(instance.chiudiButton);

        FrameDragListener moveFrame = new FrameDragListener(frame);
        frame.addMouseListener(moveFrame);
        frame.addMouseMotionListener(moveFrame);

        TurnPlayer.setTurn(instance.defaultPlayer);
        TurnPlayer.setTagLabel((byte) 0);

        WinSystem.setMinMatch(instance.minMatch);
        WinSystem.setColumns(instance.columns);

        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void closeWindow(JFrame frame){
        instance.chiudiButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Adapt window.
     *
     * @param frame the frame
     */
    public static void adaptWindow(JFrame frame){
        Dimension supportSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension originalSize = frame.getSize();
        Dimension finalSize = new Dimension(originalSize.width, originalSize.height);
        if (originalSize.height > supportSize.height) {
            finalSize.height = supportSize.height;
        }
        if (originalSize.width > supportSize.width) {
            finalSize.width = supportSize.width;
        }
        frame.setSize(finalSize);
        supportSize = ForzaIV.getInstance().getInfoPanel().getSize();
        finalSize.height = finalSize.height - supportSize.height;
        ForzaIV.getInstance().getGamePanel().setSize(finalSize);
    }

    /**
     * Gets rows.
     *
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets game panel.
     *
     * @return the game panel
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }

    private JPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Gets turn label.
     *
     * @return the turn label
     */
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    /**
     * Gets tag label.
     *
     * @return the tag label
     */
    public JLabel getTagLabel() {
        return tagLabel;
    }

    /**
     * Gets reset button.
     *
     * @return the reset button
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     * Gets info button.
     *
     * @return the info button
     */
    public JButton getInfoButton() {
        return infoButton;
    }
}
