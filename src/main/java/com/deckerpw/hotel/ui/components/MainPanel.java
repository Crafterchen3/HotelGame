package com.deckerpw.hotel.ui.components;

import com.deckerpw.hotel.game.*;
import com.deckerpw.hotel.ui.components.panel.*;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.HotelPanelBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPanel extends TexturePanel {

    private static MainPanel instance;
    private final JLabel playerNumLabel = new JLabel("Spieler ...");
    private final JLabel playerNameLabel = new JLabel("Paul");
    private final JLabel moneyLabel = new JLabel("Geld : ... DM");
    private final JLabel hotelLabel = new JLabel("Hotels : ...");
    private final JLabel nextPlayerLabel = new JLabel("Naechster Spieler : Nathan");
    private final Board board = Game.getBoard();
    private final TexturePanel topPanel;
    private final TexturePanel middlePanel;
    private final CardLayout mainCardLayout;
    private final DiceButtonCombo diceButtonCombo1;
    private final DiceButtonCombo diceButtonCombo2;
    private final JTextArea payTextArea;
    private final BuyBuildingPanel buyBuildingPanel;
    private final UpgradeBuildingPanel upgradeBuildingPanel;
    private final TopBarPanel topBarPanel;
    private HotelButton continueButton;
    private BoardViewer boardViewer;

    public MainPanel() {
        instance = this;
        setTexture(new TexturePaint(Game.getBoardAsset("floor-tile.png"), new Rectangle(0, 0, 124 * 2, 128 * 2)));
        // UI Code
        setLayout(new BorderLayout());

        TexturePanel sidePanel = new TexturePanel(new BorderLayout(0, 20));
        {
            sidePanel.setPreferredSize(new Dimension(708, 0));
            sidePanel.setBorder(new EmptyBorder(20, 15, 20, 15));
            sidePanel.setOpaque(false);
            sidePanel.setBackground(new Color(0, 0, 0, 0));
            topPanel = new TexturePanel(new GridLayout(1, 2));
            {
                topPanel.setTexture(StyleUtils.createTexturePaint(StyleUtils.getPlayerColor(Game.getCurrentPlayerIndex())));
                topPanel.setPreferredSize(new Dimension(0, 141));
                topPanel.setBorder(new HotelPanelBorder());
                TransparentPanel leftPanel = new TransparentPanel(new BorderLayout());
                {
                    playerNumLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 24));
                    playerNumLabel.setForeground(Color.WHITE);
                    leftPanel.add(playerNumLabel, BorderLayout.NORTH);
                    playerNameLabel.setVerticalAlignment(JLabel.BOTTOM);
                    playerNameLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 51));
                    playerNameLabel.setForeground(Color.WHITE);
                    leftPanel.add(playerNameLabel, BorderLayout.CENTER);
                }
                topPanel.add(leftPanel);
                TransparentPanel rightPanel = new TransparentPanel(new GridLayout(2, 1, 0, 10));
                {
                    moneyLabel.setHorizontalAlignment(JLabel.RIGHT);
                    moneyLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    moneyLabel.setForeground(Color.WHITE);
                    rightPanel.add(moneyLabel);
                    hotelLabel.setHorizontalAlignment(JLabel.RIGHT);
                    hotelLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    hotelLabel.setForeground(Color.WHITE);
                    rightPanel.add(hotelLabel);
                }
                topPanel.add(rightPanel);
            }
            sidePanel.add(topPanel, BorderLayout.NORTH);
            mainCardLayout = new CardLayout();
            middlePanel = new TexturePanel(mainCardLayout);
            {
                middlePanel.setTexture(StyleUtils.createTexturePaint(StyleUtils.getPlayerColor(Game.getCurrentPlayerIndex())));
                middlePanel.setBorder(new HotelPanelBorder());

                TransparentPanel rollPanel = new TransparentPanel(new BorderLayout(0, 10));
                {
                    diceButtonCombo1 = new DiceButtonCombo(randInt -> {
                        Player currentPlayer = Game.getCurrentPlayer();
                        int prevPos = currentPlayer.position;
                        int actualMoves = 0;
                        for (int i = 0; i < randInt; i++) {
                            if (board.fields[i].hasPlayer)
                                actualMoves += 2;
                            else
                                actualMoves++;
                        }
                        for (int i = 0; i < randInt; i++) {
                            currentPlayer.move(1);
                            boardViewer.repaint();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                            }
                        }
                        Field field = currentPlayer.getCurrentField();
                        if (prevPos <= 6 && prevPos + actualMoves > 6)
                            currentPlayer.addMoney(2000);
                        else if (prevPos <= 25 && prevPos + actualMoves > 25) {
                            boardViewer.entrancePlaceMode = BoardViewer.EntrancePlaceMode.ON;
                            boardViewer.repaint();
                        }
                        if (field.getEntranceSide() != 0 && field.getBuilding(field.getEntranceSide()).getOwnerId() != currentPlayer.id) {
                            showPayPanel();
                        } else {
                            handleAction(field);
                        }
                    });
                    rollPanel.add(diceButtonCombo1, BorderLayout.CENTER);
                }
                middlePanel.add(rollPanel, "roll");

                TransparentPanel payPanel = new TransparentPanel(new BorderLayout(0, 10));
                {
                    payTextArea = new JTextArea("Du bist an einem gegenerischem Eingang\ngelandet, nun musst du die Anzahl\nübernachtungen Würfeln.\nPreis pro Nacht: ... DM");
                    payTextArea.setEditable(false);
                    payTextArea.setOpaque(false);
                    payTextArea.setBackground(new Color(0, 0, 0, 0));
                    payTextArea.setForeground(Color.WHITE);
                    payTextArea.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    payTextArea.setLineWrap(true);
                    payPanel.add(payTextArea, BorderLayout.NORTH);
                    diceButtonCombo2 = new DiceButtonCombo(randInt -> {
                        Player currentPlayer = Game.getCurrentPlayer();
                        Field field = currentPlayer.getCurrentField();
                        Building building = field.getBuilding(field.getEntranceSide());
                        Player owner = Game.getPlayer(building.getOwnerId());
                        int entrancePrice = building.getEntrancePrice();
                        int endPrice = entrancePrice * randInt;
                        payTextArea.setText(payTextArea.getText() + "\nDu musst " + endPrice + " DM an " + owner.name + " zahlen!");
                        currentPlayer.deductMoney(endPrice);
                        owner.addMoney(endPrice);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {

                        }
                        handleAction(field);
                    });
                    payPanel.add(diceButtonCombo2, BorderLayout.CENTER);

                }
                middlePanel.add(payPanel, "pay");

                TransparentPanel buyPanel = new TransparentPanel(new BorderLayout(0, 10));
                {
                    JLabel buyPanelTitle = new JLabel("Kauf-Feld");
                    buyPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    buyPanelTitle.setForeground(Color.WHITE);
                    buyPanelTitle.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    buyPanel.add(buyPanelTitle, BorderLayout.NORTH);
                    buyBuildingPanel = new BuyBuildingPanel();
                    buyPanel.add(buyBuildingPanel, BorderLayout.CENTER);
                }
                middlePanel.add(buyPanel, "buy");

                TransparentPanel upgradePanel = new TransparentPanel(new BorderLayout(0, 10));
                {
                    JLabel upgradePanelTitle = new JLabel("Bau-Feld");
                    upgradePanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    upgradePanelTitle.setForeground(Color.WHITE);
                    upgradePanelTitle.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    upgradePanel.add(upgradePanelTitle, BorderLayout.NORTH);
                    upgradeBuildingPanel = new UpgradeBuildingPanel();
                    upgradePanel.add(upgradeBuildingPanel, BorderLayout.CENTER);
                }
                middlePanel.add(upgradePanel, "upgrade");

                TransparentPanel entrancePlacePanel = new TransparentPanel();
                {
                    entrancePlacePanel.setLayout(new BorderLayout());
                    JLabel titleLabel = new JLabel("1 Kostenloser Eingang");
                    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    titleLabel.setForeground(Color.WHITE);
                    titleLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    entrancePlacePanel.add(titleLabel, BorderLayout.NORTH);
                    JTextArea textArea = new JTextArea("Du darfst nun einen Kostenloser Eingang\nplatzieren.");
                    textArea.setEditable(false);
                    textArea.setOpaque(false);
                    textArea.setBackground(new Color(0, 0, 0, 0));
                    textArea.setForeground(Color.WHITE);
                    textArea.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                    textArea.setLineWrap(true);
                    entrancePlacePanel.add(textArea, BorderLayout.CENTER);
                }
                middlePanel.add(entrancePlacePanel, "entrance");

                TransparentPanel endPanel = new TransparentPanel(new BorderLayout(0, 10));
                {

                }
                middlePanel.add(endPanel, "end");

                TransparentPanel debugPanel = new TransparentPanel(new FlowLayout(FlowLayout.LEFT));
                {

                    HotelButton startButton = new HotelButton("Toggle Entrance Mode");
                    startButton.addActionListener(e -> {
                        boardViewer.entrancePlaceMode = boardViewer.entrancePlaceMode == BoardViewer.EntrancePlaceMode.OFF ? BoardViewer.EntrancePlaceMode.ON : BoardViewer.EntrancePlaceMode.OFF;
                        boardViewer.repaint();
                    });
                    debugPanel.add(startButton);
                    HotelButton start2Button = new HotelButton("One Free Entrance");
                    start2Button.addActionListener(e -> {
                        boardViewer.entrancePlaceMode = BoardViewer.EntrancePlaceMode.ONE_FREE_PLACE;
                        boardViewer.repaint();
                    });
                    debugPanel.add(start2Button);

                    for (int i = 0; i < 8; i++) {
                        HotelButton button = new HotelButton("Buy Hotel " + board.buildings[i].label);
                        int finalI = i;
                        button.addActionListener(e -> {
                            board.buildings[finalI].buy();
                            updatePlayerInfo();
                        });
                        debugPanel.add(button);
                    }

                    for (int i = 0; i < 8; i++) {
                        HotelButton button = new HotelButton("Upgrade Hotel " + board.buildings[i].label);
                        int finalI = i;
                        button.addActionListener(e -> {
                            board.buildings[finalI].upgrade();
                            updatePlayerInfo();
                        });
                        debugPanel.add(button);
                    }
                }
                middlePanel.add(debugPanel, "debug");
            }
            sidePanel.add(middlePanel, BorderLayout.CENTER);
            TexturePanel bottomPanel = new TexturePanel(new BorderLayout());
            {
                bottomPanel.setTexture(StyleUtils.createTexturePaint(new Color(0xc42e2e)));
                bottomPanel.setPreferredSize(new Dimension(0, 141));
                bottomPanel.setBorder(new HotelPanelBorder());
                nextPlayerLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                nextPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
                nextPlayerLabel.setForeground(Color.WHITE);
                bottomPanel.add(nextPlayerLabel, BorderLayout.CENTER);
                continueButton = new HotelButton("Zug Beenden");
                continueButton.setPreferredSize(new Dimension(171, 69));
                continueButton.setEnabled(false);
                continueButton.setBackground(new Color(0x6abe30));
                continueButton.addActionListener(e -> {
                    Game.nextTurn();
                    continueButton.setEnabled(false);
                    boardViewer.entrancePlaceMode = BoardViewer.EntrancePlaceMode.OFF;
                    updatePlayerInfo();
                    diceButtonCombo1.reset();
                    diceButtonCombo2.reset();
                    mainCardLayout.first(middlePanel);
                });
                bottomPanel.add(continueButton, BorderLayout.EAST);
            }
            sidePanel.add(bottomPanel, BorderLayout.SOUTH);
        }
        add(sidePanel, BorderLayout.WEST);
        topBarPanel = new TopBarPanel();
        add(topBarPanel, BorderLayout.NORTH);
        TransparentPanel boardPanel = new TransparentPanel(new GridBagLayout());
        {
            boardViewer = new BoardViewer();
            boardPanel.add(boardViewer);
        }
        add(boardPanel, BorderLayout.CENTER);

        mainCardLayout.first(middlePanel);
        updatePlayerInfo();
    }

    public static MainPanel getInstance() {
        return instance;
    }

    private void handleAction(Field field) {
        continueButton.setEnabled(true);
        switch (field.action) {
            case BUY:
                showBuyPanel();
                break;
            case BUILD:
                showUpgradePanel();
                break;
            case ONE_FREE_ENTRANCE:
                showEntrancePlacePanel();
                break;
            default:
                showEndPanel();
                break;
        }
    }

    public void updatePlayerInfo() {
        playerNumLabel.setText("Spieler " + (Game.getCurrentPlayerIndex() + 1));
        Player currentPlayer = Game.getCurrentPlayer();
        playerNameLabel.setText(currentPlayer.name);
        moneyLabel.setText(currentPlayer.getMoney() + " DM");
        hotelLabel.setText("Hotels : " + currentPlayer.buildings.size() + " / " + board.buildings.length);
        nextPlayerLabel.setText("Nächster Spieler : " + Game.getNextPlayer().name);
        topPanel.setTexture(StyleUtils.createTexturePaint(StyleUtils.getPlayerColor(Game.getCurrentPlayerIndex())));
        topPanel.repaint();
        middlePanel.setTexture(StyleUtils.createTexturePaint(StyleUtils.getPlayerColor(Game.getCurrentPlayerIndex())));
        middlePanel.repaint();
        topBarPanel.update();
        boardViewer.repaint();
    }

    private void showBuyPanel() {
        buyBuildingPanel.setEnabled(true);
        buyBuildingPanel.update();
        mainCardLayout.show(middlePanel, "buy");
    }

    private void showUpgradePanel() {
        upgradeBuildingPanel.setEnabled(true);
        upgradeBuildingPanel.update();
        mainCardLayout.show(middlePanel, "upgrade");
    }

    private void showPayPanel() {
        Player currentPlayer = Game.getCurrentPlayer();
        Field field = currentPlayer.getCurrentField();
        Building building = field.getBuilding(field.getEntranceSide());
        payTextArea.setText("Du bist an einem gegenerischem Eingang\ngelandet, nun musst du die Anzahl\nübernachtungen Würfeln.\nPreis pro Nacht: " + building.getEntrancePrice() + " DM");
        mainCardLayout.show(middlePanel, "pay");
    }

    private void showEntrancePlacePanel() {
        boardViewer.entrancePlaceMode = BoardViewer.EntrancePlaceMode.ONE_FREE_PLACE;
        boardViewer.repaint();
        mainCardLayout.show(middlePanel, "entrance");
    }

    private void showEndPanel() {
        mainCardLayout.show(middlePanel, "end");
    }

}
