package com.deckerpw.hotel.game;

public class Field {

    // Finals

    /// Controls what the player can do on the field
    public final Action action;
    /// ID of the building on the left side
    public final int leftBuildingId;
    /// ID of the building on the right side
    public final int rightBuildingId;
    /// X for the player sprite
    public final int x;
    /// Y for the player sprite
    public final int y;

    // Variables
    /// if a player is on the field, there can only be one player at a time
    public boolean hasPlayer = false;
    private int position;
    private boolean init = false;
    /// 0: no entrance 1: entrance on the left 2: entrance on the right
    private int entranceSide = 0;

    public Field(Action action, int leftBuildingId, int rightBuildingId, int x, int y) {
        this.action = action;
        this.leftBuildingId = leftBuildingId;
        this.rightBuildingId = rightBuildingId;
        this.x = x;
        this.y = y;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (!init) {
            this.position = position;
            init = true;
        }
    } // If the field hasn't been "initialized" yet, the position can be set manually; this is necessary as this info isn't in the JSON file.

    public int getEntranceSide() {
        return entranceSide;
    }

    public boolean canBuyEntrance(int side){
        if (entranceSide == 0) {
            if (side == 1 && leftBuildingId != -1) {
                Building leftBuilding = getLeftBuilding();
                return Game.getCurrentPlayer().buildings.contains(leftBuildingId) && leftBuilding.getEntrancePrice() <= Game.getCurrentPlayer().getMoney();
            }
            if (side == 2 && rightBuildingId != -1) {
                Building rightBuilding = getRightBuilding();
                return Game.getCurrentPlayer().buildings.contains(rightBuildingId) && rightBuilding.getEntrancePrice() <= Game.getCurrentPlayer().getMoney();
            }
        }
        return false;
    }

    public int getEntrancePrice(int side){
        if (side == 1){
            Building leftBuilding = Game.getBoard().buildings[leftBuildingId];
            return leftBuilding.getEntrancePrice();
        }
        if (side == 2){
            Building rightBuilding = Game.getBoard().buildings[rightBuildingId];
            return rightBuilding.getEntrancePrice();
        }
        return 0;
    }

    public Building getLeftBuilding(){
        if (leftBuildingId == -1) {
            return null;
        }
        return Game.getBoard().buildings[leftBuildingId];
    }

    public Building getRightBuilding() {
        if (rightBuildingId == -1) {
            return null;
        }
        return Game.getBoard().buildings[rightBuildingId];
    }

    public Building getBuilding(int side){
        if (side == 1){
            return getLeftBuilding();
        }
        if (side == 2){
            return getRightBuilding();
        }
        return null;
    }

    public void buyEntrance(int side, boolean free){
        Player currentPlayer = Game.getCurrentPlayer();
        if (canBuyEntrance(side)) {
            Building building = getBuilding(side);
            if (!free)
                currentPlayer.deductMoney(building.getEntrancePrice());
            entranceSide = side;
        }
    }
}
