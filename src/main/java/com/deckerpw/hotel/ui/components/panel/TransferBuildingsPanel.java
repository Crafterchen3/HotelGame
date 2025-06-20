package com.deckerpw.hotel.ui.components.panel;

import com.deckerpw.hotel.game.Game;
import com.deckerpw.hotel.game.Player;

import java.awt.*;

public class TransferBuildingsPanel extends TransparentPanel {

    public TransferBuildingsPanel() {
        super(new GridLayout(1, Game.getPlayers().length-1));
    }

}
