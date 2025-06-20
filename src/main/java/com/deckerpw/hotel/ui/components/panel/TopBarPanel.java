package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.game.Game;
import com.deckerpw.hotel.game.Player;
import com.deckerpw.hotel.ui.components.TexturePanel;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TopBarPanel extends TexturePanel {

    private final JLabel[] labels;

    public TopBarPanel() {
        super(new GridLayout(1, Game.getPlayers().length));
        setTexture(StyleUtils.createTexturePaint(Color.DARK_GRAY));
        setPreferredSize(new Dimension(0,30));
        setBorder(new SimpleBorder());
        Player[] players = Game.getPlayers();
        labels = new JLabel[players.length];
        for (Player player : players) {
            JLabel jLabel = new JLabel(player.name+": "+player.getMoney()+" DM");
            jLabel.setHorizontalAlignment(JLabel.CENTER);
            jLabel.setForeground(StyleUtils.getPlayerColor(player.id).brighter().brighter());
            jLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 16));
            add(jLabel);
            labels[player.id] = jLabel;
        }
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
