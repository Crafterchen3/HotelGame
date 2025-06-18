package com.deckerpw.hotel.ui.components;

import com.deckerpw.hotel.ui.style.HotelButtonBorder;
import com.deckerpw.hotel.ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;

public class HotelButton extends JButton {

    public HotelButton(String text, Icon icon) {
        super(text, icon);
        setFocusPainted(false);
        setBorder(new HotelButtonBorder(this));
        setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 18));
    }

    public HotelButton(Action a) {
        super(a);
        setFocusPainted(false);
        setBorder(new HotelButtonBorder(this));
        setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 18));
    }

    public HotelButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorder(new HotelButtonBorder(this));
        setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 18));
    }

    public HotelButton(Icon icon) {
        super(icon);
        setFocusPainted(false);
        setBorder(new HotelButtonBorder(this));
        setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 18));
    }

    public HotelButton() {
        super();
        setFocusPainted(false);
        setBorder(new HotelButtonBorder(this));
        setFont(StyleUtils.MC_FONT.deriveFont(Font.PLAIN, 18));
    }
}
