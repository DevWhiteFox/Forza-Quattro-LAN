package gui;

/**
 * The type Turn player.
 */
public class TurnPlayer {

    private int numOfTurn = 0;
    private boolean playerTurn;

    public TurnPlayer(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void resetTurn(){
        numOfTurn = 0;
    }

    public void nextTurn() {
        playerTurn = !playerTurn;
        numOfTurn++;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public int getTurnCounter() {
        return numOfTurn;
    }
}
