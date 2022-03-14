package org.leanpoker.player;

import org.leanpoker.player.model.GameState;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState gameState) {
        if (gameState.me().score() > 8) {
            return 500;
        }
        return 0;
    }

    public static void showdown(GameState gameState) {
    }
}
