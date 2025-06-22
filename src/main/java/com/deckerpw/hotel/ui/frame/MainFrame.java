package com.deckerpw.hotel.ui.frame;

import com.deckerpw.hotel.ui.components.panel.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
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
