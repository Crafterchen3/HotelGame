package com.deckerpw.hotel.ui.style;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class HotelButtonBorder extends AbstractBorder {

    private static final int BORDER_SIZE = 9; // For convenience
    private final JButton root;

    public HotelButtonBorder(JButton root) {
        this.root = root;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(9));
        g2d.setColor(root.getBackground());
        g2d.drawRect(x + 4, y + 4, width - 8, height - 8);
        g2d.setStroke(new BasicStroke(6));
        g2d.setColor(root.getBackground().brighter());
        g2d.drawRect(x + 6, y + 6, width - 12, height - 12);
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
