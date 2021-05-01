package mechanics;

import gui.TurnPlayer;

import java.util.Vector;

/**
 * The type Win system.
 */
public class WinSystem {

    private static int minMatch;
    private static int columns;
    private static Boolean winner;
    private static Vector<Boolean> boolMap;

    /**
     * Set min match.
     *
     * @param mM the m m
     */
    public static void setMinMatch(int mM){
        minMatch = mM;
    }

    /**
     * Set columns.
     *
     * @param columns the columns
     */
    public static void setColumns(int columns){
        WinSystem.columns = columns;
    }

    /**
     * Import bool map.
     *
     * @param whoPlacedDisk the who placed disk
     */
    public static void importBoolMap(Vector<Boolean> whoPlacedDisk){
        boolMap = whoPlacedDisk;
    }

    /**
     * Who win.
     */
    public static void whoWin() {
        if((int) TurnPlayer.getNumOfTurn() >= minMatch*2-1) {
            winner = horizontalSearch();
            if (winner == null) winner = verticalSearch();
            if (winner == null) winner = sdObliqueSearch();
            if (winner == null) winner = dsObliqueSearch();
            if (winner != null) {
                TurnPlayer.setTagLabel((byte) (winner ? 1 : 2));
                TurnPlayer.setTurn(winner);
                GameBoard.setGameIsRunning(false);
            }else if(TurnPlayer.getNumOfTurn() == boolMap.size()){
                TurnPlayer.setTagLabel((byte) 3);
                GameBoard.setGameIsRunning(false);
            }
        }
    }

    private static Boolean horizontalSearch(){
        Boolean commonValue = null;
        for (int i = 0; i < boolMap.size()-minMatch+1 && commonValue == null;) {
            commonValue = boolMap.get(i);
            for (int j = 1; j < minMatch; j++) {
                if (commonValue != boolMap.get(i + j)) {
                    commonValue = null;
                    break;
                }
            }

            i = (i+minMatch)%columns == 0 ? minMatch + i : i+1;
        }
        return commonValue;
    }

    private static Boolean verticalSearch() {
        Boolean commonValue = null;
        //i, k = offset
        for (int i = 0; i < columns && commonValue == null; i++) {
            for (int j = 0; j < boolMap.size() / columns - minMatch + 1  && commonValue == null; j++){
                commonValue = boolMap.get(i+j*columns);
                for (int k = 1; k < minMatch; k++) {
                    if (commonValue != boolMap.get(i+(j+k)*columns)) {
                        commonValue = null;
                        break;
                    }
                }
            }
        }

        return commonValue;
    }

    // s = SX, d = DX
    private static Boolean sdObliqueSearch(){
        Boolean commonValue = null;
        for (int i = 0; i < columns - minMatch + 1 && commonValue == null; i++) {
            for (int j = 0; j < boolMap.size() / columns  - minMatch + 1 && commonValue == null; j++) {
                commonValue = boolMap.get(i+j*columns);
                for (int k = 1; k < minMatch; k++) {
                    if (commonValue != boolMap.get(i+k+(k+j)*columns)) {
                        commonValue = null;
                        break;
                    }
                }
            }
        }
        return commonValue;
    }
    private static Boolean dsObliqueSearch(){
        Boolean commonValue = null;
        for (int i = 0; i < columns - minMatch + 1 && commonValue == null; i++) {
            for (int j = 0; j < boolMap.size() / columns  - minMatch + 1 && commonValue == null; j++) {
                commonValue = boolMap.get(i+minMatch-1+j*columns);
                for (int k = 1; k < minMatch; k++) {
                    if (commonValue != boolMap.get(i+(minMatch-1-k)+(j+k)*columns)) {
                        commonValue = null;
                        break;
                    }
                }
            }
        }
        return commonValue; }
}
