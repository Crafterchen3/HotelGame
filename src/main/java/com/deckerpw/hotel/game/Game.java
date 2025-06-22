package com.deckerpw.hotel.game;

import com.deckerpw.hotel.ui.frame.GameCreatorFrame;
import com.deckerpw.hotel.ui.frame.MainFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {

    private static Board board;
    private static Player[] players;
    private static final String boardSource = "/boards/hotel_1986/";
    private static int currentPlayer = 0;

    public static void main(String[] args) {
        new GameCreatorFrame();
    }

    public static void startGame(String[] playerNames){
        board = Board.loadBoard(Game.class.getResource("/boards/hotel_1986/board.json"));
        players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            players[i] = new Player(playerNames[i],i);
        }
        new MainFrame();
    }

    public static BufferedImage getBoardAsset(String assetName) {
        return loadImage(boardSource + "assets/" + assetName);
    }

    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(MainFrame.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    public static Board getBoard() {
        return board;
    }

    public static Player[] getPlayers() {
        return players;
    }

    public static Player getPlayer(int index){
        return players[index];
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayer;
    }

    public static Player getCurrentPlayer() {
        return players[getCurrentPlayerIndex()];
    }

    public static int getNextPlayerIndex() {
        return (currentPlayer + 1) % players.length;
    }

    public static Player getNextPlayer() {
        return players[getNextPlayerIndex()];
    }

    public static void nextTurn() {
        currentPlayer = getNextPlayerIndex();
    }

}
