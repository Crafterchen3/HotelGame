package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.ui.components.HotelButton;
import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;

public class DiceButtonCombo extends TransparentPanel {

    private final HotelButton rollButton = new HotelButton("Würfeln");
    private final JLabel diceResultLabel = new JLabel("");
    private final DiceEventListener diceEventListener;

    public DiceButtonCombo(DiceEventListener diceEventListener) {
        super(new BorderLayout());
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
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){

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

    public interface DiceEventListener {
        void onDiceRoll(int randInt);
    }

}
