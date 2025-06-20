package com.deckerpw.hotel.ui;

import com.deckerpw.hotel.ui.components.panel.MainPanel;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {

    public TestFrame() {
        super("Test Frame");
        setSize(1920, 1080);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        MainPanel mainPanel = new MainPanel();
        mainPanel.setEnabled(false);
        mainPanel.setBounds(0, 0, 1920, 1080);
        add(mainPanel);

        setVisible(true);
    }

}
