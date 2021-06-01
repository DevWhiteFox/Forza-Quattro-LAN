package mechanics;

import gui.TurnPlayer;

import java.util.ArrayList;
import java.util.Vector;

/**
 * The type Win system.
 */
public class WinSystem {

    private int minMatch;
    private int columns;
    private byte verdict = 0;
    private ArrayList<Boolean> boolMap;
    private TurnPlayer turnPlayer;

    public WinSystem(int minMatch, int columns) {
        this.minMatch = minMatch;
        this.columns = columns;
    }

    public void importBoolMap(ArrayList<Boolean> whoPlacedDisk){
        boolMap = whoPlacedDisk;
    }

    public void importPlayerTurn(TurnPlayer turnPlayer){
        this.turnPlayer = turnPlayer;
    }

    public void whoWin() {
        if (turnPlayer.getTurnCounter() >= minMatch * 2 - 1) {
            Boolean winner = horizontalSearch();
            if (winner == null) winner = verticalSearch();
            if (winner == null) winner = sdObliqueSearch();
            if (winner == null) winner = dsObliqueSearch();
            if (winner != null) {
                verdict = (byte) (winner ? 1 : 2);
            } else if (turnPlayer.getTurnCounter() == boolMap.size()) {
                verdict = 3;
            }
        }
    }

    private Boolean horizontalSearch(){
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

    private Boolean verticalSearch() {
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
    private Boolean sdObliqueSearch(){
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
    private Boolean dsObliqueSearch(){
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

    public byte getVerdict() {
        return verdict;
    }
}
