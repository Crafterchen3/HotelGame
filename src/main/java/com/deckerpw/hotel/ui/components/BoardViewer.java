package com.deckerpw.hotel.ui.components;

import com.deckerpw.hotel.game.*;
import com.deckerpw.hotel.ui.components.panel.MainPanel;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class BoardViewer extends JComponent implements MouseListener, MouseMotionListener {

    private final Board board;
    private final BufferedImage mapImage;
    private final BufferedImage entranceMapImage;
    private final BufferedImage[][] buildingImages;
    private final BufferedImage[] playerImages;
    private final MainPanel mainPanel = MainPanel.getInstance();
    private final int scale = 3;
    public EntrancePlaceMode entrancePlaceMode = EntrancePlaceMode.OFF;

    public BoardViewer(Board board, BufferedImage mapImage, BufferedImage buildingsImage, BufferedImage playersImage, BufferedImage entranceMapImage) {
        this.board = board;
        this.mapImage = mapImage;
        this.entranceMapImage = entranceMapImage;

        //Calculate the width and height of the array
        int width = board.buildings.length;
        int height = 0;
        for (Building building : board.buildings) {
            if (building.upgrades.length > height) {
                height = building.upgrades.length + 2;
            }
        }
        buildingImages = new BufferedImage[width][height];
        int imageWidth = buildingsImage.getWidth() / width;
        int imageHeight = buildingsImage.getHeight() / height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                buildingImages[x][y] = buildingsImage.getSubimage(x * imageWidth, y * imageHeight, imageWidth, imageHeight);
            }
        }

        playerImages = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            playerImages[i] = playersImage.getSubimage(6 * i, 0, 6, 10);
        }
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public BoardViewer() {
        this(Game.getBoard(), Game.getBoardAsset("map.png"), Game.getBoardAsset("buildings.png"), Game.getBoardAsset("players.png"), Game.getBoardAsset("entrance-map.png"));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(mapImage.getWidth() * scale, mapImage.getHeight() * scale);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(mapImage, 0, 0, mapImage.getWidth() * scale, mapImage.getHeight() * scale, null);
        for (int i = 0; i < board.buildings.length; i++) {
            g2d.drawImage(buildingImages[i][board.buildings[i].getUpgradeLevel()], 0, 0, mapImage.getWidth() * scale, mapImage.getHeight() * scale, null);
        }
        BufferedImage temp = new BufferedImage(entranceMapImage.getWidth(), entranceMapImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        HashMap<Integer,Color> colors = new HashMap<>();
        for (Field field : board.fields) {
            if (entrancePlaceMode != EntrancePlaceMode.OFF){
                Player currentPlayer = Game.getCurrentPlayer();
                if (currentPlayer.buildings.contains(field.leftBuildingId) && field.getEntranceSide() == 0){
                    if (entrancePlaceMode == EntrancePlaceMode.ON && board.buildings[field.leftBuildingId].entrancePrice <= currentPlayer.getMoney() ||entrancePlaceMode == EntrancePlaceMode.ONE_FREE_PLACE)
                        colors.put(new Color(field.getPosition(), 0, 0).getRGB(),new Color(0xFF00AB01));
                }
                if (currentPlayer.buildings.contains(field.rightBuildingId) && field.getEntranceSide() == 0){
                    if (entrancePlaceMode == EntrancePlaceMode.ON && board.buildings[field.rightBuildingId].entrancePrice <= currentPlayer.getMoney() ||entrancePlaceMode == EntrancePlaceMode.ONE_FREE_PLACE)
                        colors.put(new Color(field.getPosition(), 2, 0).getRGB(),new Color(0xFF00AB01));
                }
            }
            switch (field.getEntranceSide()){
                case 1:
                    colors.put(new Color(field.getPosition(), 0, 0).getRGB(), StyleUtils.getPlayerColor(board.buildings[field.leftBuildingId].getOwnerId()));
                    break;
                case 2:
                    colors.put(new Color(field.getPosition(), 2, 0).getRGB(), StyleUtils.getPlayerColor(board.buildings[field.rightBuildingId].getOwnerId()));
                    break;
            }
        }
        for (int x = 0; x < entranceMapImage.getWidth(); x++) {
            for (int y = 0; y < entranceMapImage.getHeight(); y++) {
                if (colors.containsKey(entranceMapImage.getRGB(x, y))) {
                    temp.setRGB(x, y, colors.get(entranceMapImage.getRGB(x,y)).getRGB());
                }
            }
        }
        g2d.drawImage(temp, 0, 0, temp.getWidth() * scale, temp.getHeight() * scale, null);
        for (Player player : Game.getPlayers()) {
            if (player.position == -1) {
                g2d.drawImage(playerImages[player.id], board.startPositions[player.id].x * scale, board.startPositions[player.id].y * scale, 6 * scale, 10 * scale, null);
            } else {
                g2d.drawImage(playerImages[player.id], board.fields[player.position].x * scale, board.fields[player.position].y * scale, 6 * scale, 10 * scale, null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (entrancePlaceMode != EntrancePlaceMode.OFF) {
            int x = e.getX() / scale;
            int y = e.getY() / scale;
            Color color = new Color(entranceMapImage.getRGB(x,y));
            if (!color.equals(new Color(0, 0, 0, 0))){
                int pos = color.getRed();
                int side = color.getGreen();
                Field field = board.fields[pos];
                if (side == 0 || side == 2){
                    field.buyEntrance( side == 0 ? 1 : 2, entrancePlaceMode == EntrancePlaceMode.ONE_FREE_PLACE);
                    if (entrancePlaceMode == EntrancePlaceMode.ONE_FREE_PLACE)
                        entrancePlaceMode = EntrancePlaceMode.OFF;
                }
                mainPanel.updatePlayerInfo();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (entrancePlaceMode == EntrancePlaceMode.ON) {
            int x = e.getX() / scale;
            int y = e.getY() / scale;
            Color color = new Color(entranceMapImage.getRGB(x,y));
            if (!color.equals(new Color(0, 0, 0, 0))){
                int pos = color.getRed();
                int side = color.getGreen();
                Field field = board.fields[pos];
                if (side == 0 || side == 2){
                    int actualSide = side == 0 ? 1 : 2;
                    if (field.canBuyEntrance(actualSide)){
                        setToolTipText(field.getEntrancePrice(actualSide) + " DM");
                        return;
                    }
                }
            }
        }
        setToolTipText(null);
    }

    public enum EntrancePlaceMode {
        OFF,
        ON,
        ONE_FREE_PLACE;
    }
}
