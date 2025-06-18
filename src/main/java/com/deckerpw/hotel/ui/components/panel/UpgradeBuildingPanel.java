package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.game.Building;
import com.deckerpw.hotel.game.Field;
import com.deckerpw.hotel.game.Game;
import com.deckerpw.hotel.game.Player;
import com.deckerpw.hotel.ui.components.HotelButton;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpgradeBuildingPanel extends TransparentPanel {

    public UpgradeBuildingPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void update() {
        removeAll();
        Player currentPlayer = Game.getCurrentPlayer();
        Field field = currentPlayer.getCurrentField();
        if (field.getLeftBuilding() != null && field.getLeftBuilding().getOwnerId() == currentPlayer.id && field.getLeftBuilding().getUpgradeLevel() < field.getLeftBuilding().upgrades.length - 1) {
            addBuilding(field.getLeftBuilding());
        }
        if (field.getRightBuilding() != null && field.getRightBuilding().getOwnerId() == currentPlayer.id && field.getRightBuilding().getUpgradeLevel() < field.getRightBuilding().upgrades.length - 1) {
            addBuilding(field.getRightBuilding());
        }
        if ((field.getLeftBuilding() == null || field.getLeftBuilding().getOwnerId() != currentPlayer.id) && (field.getRightBuilding() == null || field.getRightBuilding().getOwnerId() != currentPlayer.id)) {
            JLabel noBuildingLabel = new JLabel("Du besitzt keine Hotels neben dir.");
            noBuildingLabel.setHorizontalAlignment(JLabel.CENTER);
            noBuildingLabel.setForeground(Color.WHITE);
            noBuildingLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
            add(noBuildingLabel);
        }
        revalidate();
        repaint();
    }

    private void addBuilding(Building building) {
        TransparentPanel buildingPanel = new TransparentPanel(new BorderLayout());
        {
            buildingPanel.setBorder(new EmptyBorder(10,0,10,0));
            buildingPanel.setPreferredSize(new Dimension(0, 141));
            buildingPanel.setMaximumSize(new Dimension(10000, 141));
            TransparentPanel labelPanel = new TransparentPanel(new GridLayout(2, 1));
            {
                labelPanel.setPreferredSize(new Dimension(0, 141));
                JLabel buildingLabel = new JLabel(building.label);
                buildingLabel.setHorizontalAlignment(JLabel.LEFT);
                buildingLabel.setForeground(Color.WHITE);
                buildingLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                labelPanel.add(buildingLabel);
                JLabel priceLabel = new JLabel(building.upgrades[building.getUpgradeLevel()].label+ ": " +building.upgrades[building.getUpgradeLevel()].cost+" DM");
                priceLabel.setHorizontalAlignment(JLabel.LEFT);
                priceLabel.setForeground(Color.WHITE);
                priceLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 26));
                labelPanel.add(priceLabel);
            }
            buildingPanel.add(labelPanel,BorderLayout.CENTER);
            HotelButton buyButton = new HotelButton("Kaufen");
            buyButton.setPreferredSize(new Dimension(171, 69));
            buyButton.setEnabled(isEnabled());
            buyButton.setBackground(new Color(0x6abe30));
            buyButton.addActionListener(e -> {
                setEnabled(false);
                update();
                if (Game.getCurrentPlayer().getMoney() >= building.upgrades[building.getUpgradeLevel()].cost) {
                    building.upgrade();
                }
            });
            buildingPanel.add(buyButton, BorderLayout.EAST);
        }
        add(buildingPanel);
    }

}
