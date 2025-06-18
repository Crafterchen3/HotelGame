package com.deckerpw.hotel.ui.components.panel;

import javax.swing.*;
import java.awt.*;

public class TransparentPanel extends JPanel {

    public TransparentPanel() {
        super();
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public TransparentPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public TransparentPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public TransparentPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }
}
