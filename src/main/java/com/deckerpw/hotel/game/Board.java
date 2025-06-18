package com.deckerpw.hotel.game;

import com.google.gson.Gson;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Board {

    public final Building[] buildings;
    public final Field[] fields;
    public final Point[] startPositions;

    public Board(Building[] buildings, Field[] fields, Point[] startPositions) {
        this.buildings = buildings;
        this.fields = fields;
        this.startPositions = startPositions;
        initBoard();
    }

    /**
     * This method loads a Board from a JSON file, using Gson.
     **/
    public static Board loadBoard(URL file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.openStream()))) {
            Gson gson = new Gson();
            Board board = gson.fromJson(reader, Board.class);
            board.initBoard();
            return board;
        } catch (IOException e) {
            throw new RuntimeException("Error loading Board", e);
        }

    }

    public void initBoard() {
        for (int i = 0; i < buildings.length; i++) {
            buildings[i].init(i);
        } // Every Building now knows its own id
        for (int i = 0; i < fields.length; i++) {
            fields[i].setPosition(i);
        } // Every Field now knows its own position on the board
    }

}
