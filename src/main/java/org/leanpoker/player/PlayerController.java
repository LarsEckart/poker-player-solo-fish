package org.leanpoker.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import org.leanpoker.player.model.GameState;

@Controller()
public class PlayerController {

    ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Get(produces = MediaType.TEXT_PLAIN)
    @Consumes(MediaType.ALL)
    public String doGet() {
        return "Java player is running";
    }

    @Post(produces = MediaType.TEXT_PLAIN)
    @Consumes(MediaType.ALL)
    public String doPost(@QueryValue String action, @Nullable @QueryValue String game_state)
        throws JsonProcessingException {
        if (action.equals("bet_request")) {
            GameState gameState = mapper.readValue(game_state, GameState.class);
            return String.valueOf(Player.betRequest(gameState));
        }
        if (action.equals("showdown")) {
            Player.showdown(null);
        }
        if (action.equals("version")) {
            return Player.VERSION;
        }
        return "";
    }

}
