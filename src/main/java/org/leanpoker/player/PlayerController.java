package org.leanpoker.player;

import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import org.slf4j.Logger;

@Controller()
public class PlayerController {

    private static final Logger log = getLogger(PlayerController.class);

    ObjectMapper mapper = new ObjectMapper();

    @Get(produces = MediaType.TEXT_PLAIN)
    public String doGet() {
        return "Java player is running";
    }

    @Post(produces = MediaType.TEXT_PLAIN)
    public String doPost(@QueryValue String action, @Nullable @QueryValue String game_state) throws JsonProcessingException {
        if (action.equals("bet_request")) {
            String gameState = game_state;

            log.info("gamestate {}", gameState);

            return String.valueOf(Player.betRequest(mapper.readTree(gameState)));
        }
        if (action.equals("showdown")) {
            String gameState = game_state;

            Player.showdown(mapper.readTree(gameState));
        }
        if (action.equals("version")) {
            return Player.VERSION;
        }
        return "";
    }

}
