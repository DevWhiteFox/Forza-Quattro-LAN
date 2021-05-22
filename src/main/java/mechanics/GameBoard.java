package mechanics;

import componets.ButtonStyle;
import componets.DiscSlot;
import componets.GridSlot;
import gui.ForzaIV;
import gui.ImageManager;
import gui.TurnPlayer;
import html.InfoGame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Game board.
 */
public class GameBoard extends ForzaIV{
    private final GridSlot grid;
    private final boolean gameIsRunning;
    private Boolean player = false;

    public GameBoard(int rows, int columns, int minMatch, boolean defaultPlayer) {
        super(rows,columns,minMatch,defaultPlayer);
        grid = new GridSlot(columns);

        initBoard();
        initComponent();
        initEventHandler();

        gameIsRunning = true;
    }

    private void initBoard() {
        gamePanel.removeAll();
        gamePanel.updateUI();

        TurnPlayer.setTurn(player);
        TurnPlayer.setTagLabel((byte) 0);
        TurnPlayer.resetNumOfTurn();

        initGrid();
    }

    private void initComponent(){
        initInfoButton();
        initInfoGame();
        initResetButton();
    }

    private void initEventHandler(){
        java.awt.EventQueue.invokeLater(this::moveListener);
        java.awt.EventQueue.invokeLater(this::clickListener);
        java.awt.EventQueue.invokeLater(this::resetButtonListener);
        java.awt.EventQueue.invokeLater(this::infoButtonListener);
    }

    public void initResetButton(){
        resetButton.setIcon(ImageManager.createImageIcon("reset.png"));
        ButtonStyle.simpleStyle(resetButton);
        resetButton.setVisible(true);
    }

    public void initInfoButton(){
        infoButton.setIcon(ImageManager.createImageIcon("info.png"));
        ButtonStyle.simpleStyle(infoButton);
        infoButton.setVisible(true);
    }

    public void initInfoGame(){
        infoGame.setLocationRelativeTo(this);
        infoGame.pack();
    }

    private void initGrid(){
        gamePanel.setLayout(new GridLayout(rows,columns));

        for (int i = 0; i < rows * columns; i++) {
            DiscSlot slot = new DiscSlot();
            grid.addDiscInGrid(slot);
            gamePanel.add(slot);
        }
    }

    private void moveListener() {

        gamePanel.addMouseMotionListener(new MouseAdapter() {

            private int lastColumnHovered = 0;

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                int hoverColumn = getColumnsByX(e.getX());

                if (lastColumnHovered != hoverColumn) {
                    DiscSlot slot;
                    if (grid.getSlotLeftVerticallyBy(hoverColumn) != 0) {
                        slot = grid.getDiscByIndex(grid.getSlotLeftVerticallyBy(hoverColumn) * columns + hoverColumn);
                        setHoverCell(slot, true);
                    }
                    if (grid.getSlotLeftVerticallyBy(lastColumnHovered) != 0){
                        slot = grid.getDiscByIndex(grid.getSlotLeftVerticallyBy(lastColumnHovered) * columns + lastColumnHovered);
                        setHoverCell(slot, false);
                    }
                    lastColumnHovered = hoverColumn;
                }
            }
        });
    }

    private void clickListener(){
        gamePanel.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int clickColumn = getColumnsByX(e.getX());
                int nInColumn = grid.getSlotLeftVerticallyBy(clickColumn);
                if (nInColumn != 0 && gameIsRunning) {

                    setPlayerCell(grid.getDiscByIndex(nInColumn * columns + clickColumn));
                    if (nInColumn != -1) {
                        setHoverCell(grid.getDiscByIndex(nInColumn * columns + clickColumn), true);
                    }

                    grid.getDiscByIndex(nInColumn * columns + clickColumn).placeDisc(player);
                    player = !player;

                    TurnPlayer.setTurn(player);
                    TurnPlayer.incNumOfTurn();

                    winSystem.importBoolMap( grid.getWhoPlacedList() );
                    winSystem.whoWin();
                }
            }
        });
    }

    private void resetButtonListener() {
        resetButton.addActionListener(e -> initBoard());
    }

    private void infoButtonListener() {
        infoButton.addActionListener(e -> InfoGame.getInstance().setVisible(true));
    }

    private void setHoverCell(DiscSlot slot, boolean state){
        slot.setIsHovered(state);
        slot.repaint();
    }

    private void setPlayerCell(DiscSlot slot){
        slot.placeDisc(player);
        slot.repaint();
    }

    private int getColumnsByX(int xCoord) {
        int gameWidth = gamePanel.getWidth();
        int xLambda = gameWidth/columns;

        int nSection = 0;
        for(int i = xLambda; i < xCoord; i += xLambda){
            if(i < gameWidth){
                nSection++;
            }
        }
        return nSection;
    }
}
