package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.game.Game;
import com.deckerpw.hotel.game.Player;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class TopBarPanel extends TexturePanel {

    private final JLabel[] labels;

    public TopBarPanel() {
        super(new BorderLayout());
        setTexture(StyleUtils.createTexturePaint(Color.DARK_GRAY));
        setPreferredSize(new Dimension(0,30));
        setBorder(new SimpleBorder());
        TransparentPanel panel = new TransparentPanel(new GridLayout(1, Game.getPlayers().length));
        {
            Player[] players = Game.getPlayers();
            labels = new JLabel[players.length];
            for (Player player : players) {
                JLabel jLabel = new JLabel(player.name + ": " + player.getMoney() + " DM");
                jLabel.setHorizontalAlignment(JLabel.CENTER);
                jLabel.setForeground(StyleUtils.getPlayerColor(player.id).brighter().brighter());
                jLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 16));
                panel.add(jLabel);
                labels[player.id] = jLabel;
            }
        }
        add(panel,  BorderLayout.CENTER);
        JButton closeButton = new JButton("");
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(Color.RED.darker());
        closeButton.setBorder(new HotelButtonBorder(closeButton));
        closeButton.setPreferredSize(new Dimension(27, 27));
        add(closeButton, BorderLayout.EAST);
    }

    public void update() {
        Player[] players = Game.getPlayers();
        for (Player player : players) {
            JLabel jLabel = labels[player.id];
            jLabel.setText(player.name+": "+player.getMoney()+" DM");
        }
    }

    private static class SimpleBorder extends AbstractBorder {

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(x, y + height-2,x + width, y + height-2);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.bottom = 3;
            return insets;
        }

        public Insets getBorderInsets() {
            return new Insets(0,0,3,0);
        }
    }

}
