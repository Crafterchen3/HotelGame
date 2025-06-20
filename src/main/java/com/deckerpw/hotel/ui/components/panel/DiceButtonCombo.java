package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.ui.components.HotelButton;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DiceButtonCombo extends TransparentPanel implements KeyListener {

    private final HotelButton rollButton = new HotelButton("Würfeln");
    private final JLabel diceResultLabel = new JLabel("");
    private final DiceEventListener diceEventListener;

    public DiceButtonCombo(DiceEventListener diceEventListener) {
        super(new BorderLayout());
        this.addKeyListener(this);
        setFocusable(true);
        requestFocus();
        this.diceEventListener = diceEventListener;
        rollButton.addActionListener(e -> {
            rollButton.setEnabled(false);
            new Thread(() -> {
                int randInt = 0;
                for (int i = 0; i < 50; i++) {
                    randInt = (int) (Math.random() * 6) + 1;
                    diceResultLabel.setText(randInt + "");
                    try {
                        Thread.sleep(((int) (i * 2f)) ^ 2);
                    } catch (InterruptedException ex) {

                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {

                }

                diceEventListener.onDiceRoll(randInt);
            }).start();
        });
        rollButton.setPreferredSize(new Dimension(0, 141));
        rollButton.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 55));
        rollButton.setBackground(new Color(0x6abe30));
        add(rollButton, BorderLayout.SOUTH);
        diceResultLabel.setHorizontalAlignment(JLabel.CENTER);
        diceResultLabel.setForeground(Color.WHITE);
        diceResultLabel.setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 200));
        add(diceResultLabel, BorderLayout.CENTER);
    }

    public void reset() {
        rollButton.setEnabled(true);
        diceResultLabel.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case '1':
                diceEventListener.onDiceRoll(1);
                break;
            case '2':
                diceEventListener.onDiceRoll(2);
                break;
            case '3':
                diceEventListener.onDiceRoll(3);
                break;
            case '4':
                diceEventListener.onDiceRoll(4);
                break;
            case '5':
                diceEventListener.onDiceRoll(5);
                break;
            case '6':
                diceEventListener.onDiceRoll(6);
                break;
            case '7':
                diceEventListener.onDiceRoll(7);
                break;
            case '8':
                diceEventListener.onDiceRoll(8);
                break;
            case '9':
                diceEventListener.onDiceRoll(9);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public interface DiceEventListener {
        void onDiceRoll(int randInt);
    }

}
