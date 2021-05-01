import gui.ForzaIV;
import html.InfoGame;
import mechanics.GameBoard;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        int rows;
        int columns;
        int minMatch;
        boolean player;

        if(args.length == 4) {
            rows = Integer.parseInt(args[0]);
            columns = Integer.parseInt(args[1]);
            minMatch = Integer.parseInt(args[2]);
            player = Boolean.parseBoolean(args[3]);
        }else{
            rows = 6;
            columns = 7;
            minMatch = 4;
            player = false;
        }

        java.awt.EventQueue.invokeLater(() -> {
            ForzaIV.initFIV(rows,columns,minMatch,player);
            ForzaIV.start();
            InfoGame.start();
            GameBoard.start();
        });
    }
}
