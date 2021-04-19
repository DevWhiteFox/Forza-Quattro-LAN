package gui;

/**
 * The type Turn player.
 */
public class TurnPlayer {

    private static int numOfTurn = 0;

    /**
     * Sets turn.
     *
     * @param player the player
     */
    public static void setTurn(boolean player) {
        String imageName = null;

        //player true = yellow, false = red;
        if(player){
            imageName = "resource/turnYellow.png";
        }else{
            imageName = "resource/turnRed.png";
        }
        ForzaIV.getInstance().getTurnLabel().setIcon(ImageManager.createImageIcon(imageName));
        ForzaIV.getInstance().getTurnLabel().repaint();
    }

    /**
     * Sets tag label.
     *
     * @param tagLabel the tag label
     */
    public static void setTagLabel(byte tagLabel) {
        String imageName = null;

        //tagLabel 0 = tagGame, 1 = tagWinYellow, 2 = tagWinRed, 3 = tagWinTie
        if(tagLabel == 0){
            imageName = "resource/tagGame.png";
        }else if(tagLabel == 1){
            imageName = "resource/tagWinYellow.png";
        }else if(tagLabel == 2){
            imageName = "resource/tagWinRed.png";
        }else if(tagLabel == 3){
            imageName = "resource/tagWinTie.png";
        }

        ForzaIV.getInstance().getTagLabel().setIcon(ImageManager.createImageIcon(imageName));
        ForzaIV.getInstance().getTagLabel().repaint();
    }

    /**
     * Inc num of turn.
     */
    public static void incNumOfTurn() {
        TurnPlayer.numOfTurn += 1;
    }

    /**
     * Gets num of turn.
     *
     * @return the num of turn
     */
    public static int getNumOfTurn() {
        return TurnPlayer.numOfTurn;
    }

    /**
     * Reset num of turn.
     */
    public static void resetNumOfTurn() {
        TurnPlayer.numOfTurn = 0;
    }
}
