package com.deckerpw.hotel.ui.style;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StyleUtils {

    public static TexturePaint createTexturePaint(Color mainColor) {
        BufferedImage image = new BufferedImage(6, 6, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(mainColor);
        g2d.fillRect(0, 0, 6, 6);
        g2d.setColor(new Color(48, 48, 48, 32));
        g2d.fillRect(3, 0, 3, 3);
        g2d.fillRect(0, 3, 3, 3);
        g2d.dispose();
        return new TexturePaint(image, new Rectangle(0, 0, 6, 6));
    }

    public static Color getPlayerColor(int id) {
        switch (id) {
            case 0:
                return new Color(0xd33e31);
            case 1:
                return new Color(0x274568);
            case 2:
                return new Color(0x89c354);
            case 3:
                return new Color(0xa39600);
            default:
                return Color.BLACK;
        }
    }

    public static final Font MC_FONT;
    static {
        Font font = new Font("Verdana",Font.PLAIN,16);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, StyleUtils.class.getResourceAsStream("/MinecraftRegular.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MC_FONT = font;
    }

}
