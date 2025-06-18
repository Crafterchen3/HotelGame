package com.deckerpw.hotel.ui.style;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class HotelPanelBorder extends AbstractBorder {

    private static final int BORDER_SIZE = 30; // For convenience

    public HotelPanelBorder() {
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(12));
        g2d.setColor(new Color(0x403736));
        g2d.drawRect(x + 6, y + 6, width - 12, height - 12);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(0x262020));
        g2d.drawRect(x + 1, y + 1, width - 3, height - 3);
        g2d.drawRect(x + 12, y + 12, width - 24, height - 24);
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = BORDER_SIZE;
        return insets;
    }

    public Insets getBorderInsets() {
        return new Insets(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
