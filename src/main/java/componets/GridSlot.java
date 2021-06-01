package componets;

import java.util.ArrayList;

public class GridSlot {
    private final int columns;

    private final ArrayList<DiscSlot> discSlotList = new ArrayList<>();

    public GridSlot(int columns) {
        this.columns = columns;
    }

    public void addDiscInGrid(DiscSlot disc){
        discSlotList.add(disc);
    }

    public DiscSlot getDiscByIndex(int index){
        return discSlotList.get(index);
    }

    public int getSlotLeftVerticallyBy(int pillar){
        int slotLeft = 0;
        int row = discSlotList.size() / columns;

        for (int i = 0; i < row; i++) {
            if( getDiscByIndex(i * columns + pillar).getWhoPlaced() == null ) {
                slotLeft++;
            }
        }

        return slotLeft;
    }

    public ArrayList<Boolean> getWhoPlacedList(){
        ArrayList<Boolean> whoPlacedList = new ArrayList<>();
        for (DiscSlot discSlot: discSlotList) {
            whoPlacedList.add( discSlot.getWhoPlaced() );
        }
        return whoPlacedList;
    }

    public void resetSlots(){
        for (DiscSlot discSlot: discSlotList) {
            discSlot.setEmptySlot();
        }
    }

    public ArrayList<DiscSlot> getAllSlots() {
        return discSlotList;
    }
}
