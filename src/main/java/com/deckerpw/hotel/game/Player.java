package com.deckerpw.hotel.game;

import com.deckerpw.hotel.ui.components.panel.MainPanel;

import java.util.ArrayList;

public class Player {

    public final String name;
    public final int id;
    public int position = -1; // Field outside the board, before the start field.
    public ArrayList<Integer> buildings = new ArrayList<>(); // Owned Buildings
    private int money = 12000;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void move(int steps) {
        if (position != -1) {
            Game.getBoard().fields[position].hasPlayer = false;
        }
        position = (position + steps) % Game.getBoard().fields.length;
        while (Game.getBoard().fields[position].hasPlayer) {
            position = (position + 1) % Game.getBoard().fields.length;
        }
        Game.getBoard().fields[position].hasPlayer = true;
    }

    public Field getCurrentField() {
        if (position == -1) {
            return null;
        }
        return Game.getBoard().fields[position];
    }

    public void deductMoney(int amount) {
        new Thread(() -> {
            for (int i = 0; i < amount / 25; i++) {
                this.money -= 25;
                MainPanel.getInstance().updatePlayerInfo();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {

                }
            }
        }).start();
    }

    public void addMoney(int amount) {
        new Thread(() -> {
            for (int i = 0; i < amount / 25; i++) {
                this.money += 25;
                MainPanel.getInstance().updatePlayerInfo();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }


}
