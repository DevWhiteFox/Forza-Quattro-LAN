import gui.ForzaIV;
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
        int rows = 6;
        int columns = 7;
        int minMatch = 4;
        boolean player = true;

        if(args.length == 4) {
            rows = Integer.parseInt(args[0]);
            columns = Integer.parseInt(args[1]);
            minMatch = Integer.parseInt(args[2]);
            player = Boolean.parseBoolean(args[3]);
        }else if(args.length == 2){
            rows = Integer.parseInt(args[0]);
            columns = Integer.parseInt(args[1]);
        }

        int finalRows = rows;
        int finalColumns = columns;
        int finalMinMatch = minMatch;
        boolean finalPlayer = player;
        java.awt.EventQueue.invokeLater(() -> {
            ForzaIV game = new GameBoard(finalRows, finalColumns, finalMinMatch, finalPlayer);
        });
    }
}
