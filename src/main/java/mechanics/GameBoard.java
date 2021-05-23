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
    private final TurnPlayer turnPlayer;

    private boolean gameIsRunning;
    private int lastColumnHovered = 0;


    public GameBoard(int rows, int columns, int minMatch, boolean defaultPlayer) {
        super(rows,columns,minMatch,defaultPlayer);
        grid = new GridSlot(columns);
        turnPlayer = new TurnPlayer(true);
        winSystem.importPlayerTurn(turnPlayer);

        initGrid();
        winSystem.importBoolMap( grid.getWhoPlacedList() );

        initComponent();
        initEventHandler();

        changePlayerTurnBanner();
        changeWinBanner((byte) 0);

        gameIsRunning = true;
    }

    private void resetBoard() {
        gamePanel.removeAll();
        gamePanel.updateUI();

        turnPlayer.resetTurn();
        grid.resetSlots();
        renderGrid();
    }

    private void initComponent(){
        initInfoButton();
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

    private void initGrid(){
        gamePanel.setLayout(new GridLayout(rows,columns));

        for (int i = 0; i < rows * columns; i++) {
            DiscSlot slot = new DiscSlot();
            grid.addDiscInGrid(slot);
        }
        renderGrid();
    }

    private void renderGrid(){
        for (DiscSlot slot: grid.getAllSlots()) {
            gamePanel.add(slot);

        }
    }

    private void changePlayerTurnBanner(){
        String imageName;

        //player true = yellow, false = red;
        if(turnPlayer.getPlayerTurn()){
            imageName = "turnYellow.png";
        }else{
            imageName = "turnRed.png";
        }

        turnLabel.setIcon(ImageManager.createImageIcon(imageName));
        turnLabel.repaint();
    }

    public void changeWinBanner(byte winLabel) {
        String imageName = null;

        //tagLabel 0 = tagGame, 1 = tagWinYellow, 2 = tagWinRed, 3 = tagWinTie
        if(winLabel == 0){
            imageName = "tagGame.png";
        }else if(winLabel == 1){
            imageName = "tagWinYellow.png";
        }else if(winLabel == 2){
            imageName = "tagWinRed.png";
        }else if(winLabel == 3){
            imageName = "tagWinTie.png";
        }

        tagLabel.setIcon(ImageManager.createImageIcon(imageName));
        tagLabel.repaint();
    }

    private void moveListener() {
        gamePanel.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                int hoverColumn = getColumnsByX(e.getX());
                int enoughVertically;
                if (lastColumnHovered != hoverColumn) {
                    DiscSlot slot;
                    enoughVertically = grid.getSlotLeftVerticallyBy(hoverColumn) - 1;
                    if (enoughVertically > -1) {
                        slot = grid.getDiscByIndex(enoughVertically * columns + hoverColumn);
                        setHoverCell(slot, true);
                    }
                    enoughVertically = grid.getSlotLeftVerticallyBy(lastColumnHovered) - 1;
                    if (enoughVertically > -1){
                        slot = grid.getDiscByIndex(enoughVertically * columns + lastColumnHovered);
                        setHoverCell(slot, false);
                    }
                    lastColumnHovered = hoverColumn;
                }
            }
        });
    }

    private void clickListener(){
        gamePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int clickColumn = getColumnsByX(e.getX());
                int enoughVertically = grid.getSlotLeftVerticallyBy(clickColumn) - 1;
                if (enoughVertically > -1 && gameIsRunning) {
                    DiscSlot slot = grid.getDiscByIndex(enoughVertically * columns + clickColumn);

                    setPlayerCell(slot);
                    setHoverCell(slot, false);

                    turnPlayer.nextTurn();
                    winSystem.whoWin();

                    if(winSystem.getVerdict() != 0){
                        changeWinBanner( winSystem.getVerdict() );
                        gameIsRunning = false;
                    }
                    changePlayerTurnBanner();
                }
            }
        });
    }

    private void resetButtonListener() {
        resetButton.addActionListener(e -> resetBoard());
    }

    private void infoButtonListener() {
        infoButton.addActionListener(e -> {
            infoGame = new InfoGame(this);
            infoGame.showInfo();
        });
    }

    private void setHoverCell(DiscSlot slot, boolean state){
        slot.setIsHovered(state);
        slot.repaint();
    }

    private void setPlayerCell(DiscSlot slot){
        slot.placeDisc( turnPlayer.getPlayerTurn() );
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
