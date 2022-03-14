package org.leanpoker.player;

import static org.slf4j.LoggerFactory.getLogger;

import org.leanpoker.player.model.GameState;
import org.slf4j.Logger;

public class Player {

    private static final Logger log = getLogger(Player.class);

    static final String VERSION = "Default Java folding player";

    public static int betRequest(GameState gameState) {
        if (gameState.me().score() > 8) {
            return 500000;
        }
        return 0;
    }

    public static void showdown(String gameState) {
        log.info("gamestate json: {}", gameState);
    }
}
