package com.deckerpw.hotel.ui;

import com.deckerpw.hotel.game.Game;
import com.deckerpw.hotel.game.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameCreatorFrame extends JFrame {

    JTextField[] playerFields = new JTextField[4];

    public GameCreatorFrame() {
        super("Hotel 1986");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,400);
        JPanel playerPanel = new JPanel();
        {
            playerPanel.setBorder(new EmptyBorder(10,10,10,10));
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            playerPanel.add(new JLabel("Man kann Felder auch lehr lassen"));
            for (int i = 0; i < 4; i++) {
                JLabel label = new JLabel("Spieler "+(i+1));
                playerPanel.add(label);
                JTextField textField = new JTextField();
                textField.setMaximumSize(new Dimension(10000,textField.getMinimumSize().height));
                playerFields[i] = textField;
                playerPanel.add(textField);
            }
        }
        add(playerPanel,BorderLayout.CENTER);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            ArrayList<String> playerNames = new ArrayList<>();
            for (int i = 0; i < playerFields.length; i++) {
                JTextField field = playerFields[i];
                if (!field.getText().isBlank()){
                    playerNames.add(field.getText());
                }
            }
            Game.startGame(playerNames.toArray(new String[0]));
            dispose();
        });
        add(startButton,BorderLayout.SOUTH);
        setVisible(true);
    }

}
