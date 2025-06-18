package com.deckerpw.hotel.game;

public class Building {

    // Finals
    public final String label;
    /// Price to buy the foundation, the player now owns this building
    public final int basePrice;
    /// How much money the owner gets / another player pays to sell the building.
    public final int sellPrice;
    /// Price to buy an Entrance
    public final int entrancePrice;
    /// This Price determines how much another player has to pay if they land next to an entrance, the cost varies based on the starLevel.
    public final int[] starPrices;
    /// The upgrades the owner can buy for the building
    public final Upgrade[] upgrades;

    // Variables

    private boolean init = false;
    private int id;
    private int starLevel = 0; // Current Star Level
    private int upgradeLevel = 0;
    private int ownerId = -1;

    public Building(int id, String label, int basePrice, int sellPrice, int entrancePrice, int[] starPrices, Upgrade[] upgrades) {
        this.id = id;
        this.label = label;
        this.basePrice = basePrice;
        this.sellPrice = sellPrice;
        this.entrancePrice = entrancePrice;
        this.starPrices = starPrices;
        this.upgrades = upgrades;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void buy() {
        if (ownerId != -1)
            throw new RuntimeException("Building already owned");
        if (Game.getCurrentPlayer().getMoney() < basePrice)
            throw new RuntimeException("Not enough money to buy building");
        Game.getCurrentPlayer().deductMoney(basePrice);
        Game.getCurrentPlayer().buildings.add(id);
        ownerId = Game.getCurrentPlayerIndex();
    }

    public void upgrade() {
        if (upgradeLevel >= upgrades.length)
            throw new RuntimeException("Building has reached max upgrade level");
        if (Game.getCurrentPlayerIndex() != ownerId)
            throw new RuntimeException("Building does not belong to current player");
        if (Game.getCurrentPlayer().getMoney() < upgrades[upgradeLevel].cost)
            throw new RuntimeException("Not enough money to upgrade building");
        Game.getCurrentPlayer().deductMoney(upgrades[upgradeLevel].cost);
        starLevel = upgrades[upgradeLevel].starLevel;
        upgradeLevel++;
    }

    public int getCurrentStarPrice() {
        if (starLevel == 0) {
            return 0;
        }
        return starPrices[starLevel - 1];
    }

    public int getEntrancePrice() {
        return entrancePrice;
    }

    public int getId() {
        return id;
    }

    public void init(int id) {
        if (!init) {
            this.ownerId = -1;
            this.id = id;
            init = true;
        }
    }

    public void sellTo(int playerId) {
        if (ownerId == -1)
            throw new RuntimeException("Building does not belong to any player");
        if (Game.getPlayer(playerId).getMoney() < sellPrice)
            throw new RuntimeException("Not enough money to sell building");
        if (Game.getPlayer(playerId).id == ownerId)
            throw new RuntimeException("Cannot sell building to self");
        Game.getPlayer(ownerId).addMoney(sellPrice);
        Game.getPlayer(ownerId).buildings.remove(Integer.valueOf(id));
        Game.getPlayer(playerId).deductMoney(sellPrice);
        Game.getPlayer(playerId).buildings.add(id);
        ownerId = playerId;

    }
}
