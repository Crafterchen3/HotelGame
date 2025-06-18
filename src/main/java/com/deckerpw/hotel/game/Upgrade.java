package com.deckerpw.hotel.game;

public class Upgrade {

    public final String label;
    public final int cost;
    public final int starLevel; // Once the Upgrade is bought, the Building will acquire this starLevel.

    public Upgrade(String label, int cost, int starLevel) {
        this.label = label;
        this.cost = cost;
        this.starLevel = starLevel;
    }

}
