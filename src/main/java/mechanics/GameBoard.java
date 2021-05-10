package mechanics;

import componets.ButtonStyle;
import componets.DiscSlot;
import gui.ForzaIV;
import gui.ImageManager;
import gui.TurnPlayer;
import html.InfoGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * The type Game board.
 */
public class GameBoard {
    private static Vector<DiscSlot> Grid;
    private static Vector<Integer> verticalStacked;
    private static Vector<Boolean> whoPlacedDisk;

    private static Boolean player = false;
    private static boolean gameIsRunning = true;

    private static ForzaIV FIVInstance;

    /**
     * Start.
     */
    public static void start(){
        GameBoard.FIVInstance = ForzaIV.getInstance();
        createGrid();
        initInfoButton();
        initInfoGame();
        initResetButton();
        java.awt.EventQueue.invokeLater(GameBoard::moveListener);
        java.awt.EventQueue.invokeLater(GameBoard::clickListener);
        java.awt.EventQueue.invokeLater(GameBoard::resetButtonListener);
        java.awt.EventQueue.invokeLater(GameBoard::infoButtonListener);
    }

    private static void restart() {
        player = false;
        Grid.clear();
        whoPlacedDisk = null;
        verticalStacked = null;
        Grid = null;
        FIVInstance.getGamePanel().removeAll();
        FIVInstance.getGamePanel().updateUI();
        setGameIsRunning(true);
        TurnPlayer.setTurn(player);
        TurnPlayer.setTagLabel((byte) 0);
        TurnPlayer.resetNumOfTurn();
        createGrid();
    }

    /**
     * Init reset button.
     */
    public static void initResetButton(){
        FIVInstance.getResetButton().setIcon(ImageManager.createImageIcon("reset.png"));
        ButtonStyle.simpleStyle(FIVInstance.getResetButton());
        FIVInstance.getResetButton().setVisible(true);
    }

    /**
     * Init info button.
     */
    public static void initInfoButton(){
        FIVInstance.getInfoButton().setIcon(ImageManager.createImageIcon("info.png"));
        ButtonStyle.simpleStyle(FIVInstance.getInfoButton());
        FIVInstance.getInfoButton().setVisible(true);
    }


    /**
     * Init info game.
     */
    public static void initInfoGame(){
        InfoGame.getInstance().setLocationRelativeTo(ForzaIV.getInstance().getGamePanel());
        InfoGame.getInstance().pack();
    }

    /**
     * Set game is running.
     *
     * @param gir the gir
     */
    public static void setGameIsRunning(boolean gir){
        gameIsRunning = gir;
    }

    private static void createGrid(){
        whoPlacedDisk = new Vector<>();
        verticalStacked = new Vector<>();
        Grid = new Vector<>();
        int rows = FIVInstance.getRows();
        int columns = FIVInstance.getColumns();
        FIVInstance.getGamePanel().setLayout(new GridLayout(rows,columns));
        for (int i = 0; i < rows * columns; i++) {
            DiscSlot slot = new DiscSlot();
            slot.setPlayer(null);
            Grid.add(slot);
            FIVInstance.getGamePanel().add(slot);
            whoPlacedDisk.add(null);
        }
        for (int i = 0; i < columns; i++) {
            verticalStacked.add(rows - 1);
        }
    }

    private static void moveListener() {

        FIVInstance.getGamePanel().addMouseMotionListener(new MouseAdapter() {

            private int lastColumnHovered = 0;

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                int hoverColumn = getColumnsByX(e.getX());
                int columns = FIVInstance.getColumns();

                if (lastColumnHovered != hoverColumn) {
                    if (verticalStacked.get(hoverColumn) != -1) setHoverCell(Grid.get(verticalStacked.get(hoverColumn) * columns + hoverColumn), true);
                    if (verticalStacked.get(lastColumnHovered) != -1) setHoverCell(Grid.get(verticalStacked.get(lastColumnHovered) * columns + lastColumnHovered), false);
                    lastColumnHovered = hoverColumn;
                }
            }
        });
    }

    private static void clickListener(){
        FIVInstance.getGamePanel().addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int clickColumn = getColumnsByX(e.getX());
                if (verticalStacked.get(clickColumn) != -1 && gameIsRunning) {
                    int columns = FIVInstance.getColumns();
                    Integer nInColumn = verticalStacked.get(clickColumn);
                    verticalStacked.set(clickColumn, nInColumn - 1);

                    setPlayerCell(Grid.get(nInColumn * columns + clickColumn));
                    if (verticalStacked.get(clickColumn) != -1) setHoverCell(Grid.get(verticalStacked.get(clickColumn) * columns + clickColumn), true);

                    whoPlacedDisk.set(nInColumn * columns + clickColumn, player);
                    player = !player;

                    TurnPlayer.setTurn(player);
                    TurnPlayer.incNumOfTurn();

                    WinSystem.importBoolMap(whoPlacedDisk);
                    WinSystem.whoWin();
                }
            }
        });
    }

    private static void resetButtonListener() {
        FIVInstance.getResetButton().addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
    }

    private static void infoButtonListener() {
        FIVInstance.getInfoButton().addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoGame.getInstance().setVisible(true);
            }
        });
    }

    private static void setHoverCell(DiscSlot slot, boolean state){
        slot.setIsHovered(state);
        slot.repaint();
    }

    private static void setPlayerCell(DiscSlot slot){
        slot.setPlayer(player);
        slot.repaint();
    }

    private static int getColumnsByX(int x) {
        int j = 0;
        int columns = FIVInstance.getColumns();
        for(int i = FIVInstance.getGamePanel().getWidth()/columns; i < FIVInstance.getGamePanel().getWidth() && i < x; i += FIVInstance.getGamePanel().getWidth()/columns, j++);
        return j;
    }
}
