package org.leanpoker.player;

import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spun.util.logger.SimpleLogger;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.util.Map;
import org.leanpoker.player.model.GameState;
import org.slf4j.Logger;

@Controller("/")
public class PlayerController {

  private static final Logger log = getLogger(PlayerController.class);

  ObjectMapper mapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


  @Get(produces = MediaType.TEXT_PLAIN)
  @Consumes(MediaType.ALL)
  public String doGet() {
    return "Java player is running";
  }

  @Post(produces = MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String doPost(@Body Map<String, String> body)
      throws JsonProcessingException {
    var version = "1.8 - go all-in";
    SimpleLogger.variable("version", version);
    SimpleLogger.variable("body", body);
    var game_state = body.get("game_state");
    var action = body.get("action");

    if (action.equals("bet_request")) {
      GameState gameState = mapper.readValue(game_state, GameState.class);
      return String.valueOf(Player.betRequest(gameState));
    }
    if (action.equals("showdown")) {
      Player.showdown(game_state);
    }
    if (action.equals("version")) {
      return version;
    }
    return "1";
  }

}
