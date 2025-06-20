package com.deckerpw.hotel.ui.components.panel;

import javax.swing.*;
import java.awt.*;

public class TexturePanel extends JPanel {

    protected TexturePaint texture;

    public TexturePanel() {
    }

    public TexturePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TexturePanel(LayoutManager layout) {
        super(layout);
    }

    public TexturePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (texture != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(texture);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public TexturePaint getTexture() {
        return texture;
    }

    public void setTexture(TexturePaint texture) {
        this.texture = texture;
    }
}
