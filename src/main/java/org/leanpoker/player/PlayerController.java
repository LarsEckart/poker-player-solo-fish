package org.leanpoker.player;

import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spun.util.JsonUtils;
import com.spun.util.logger.SimpleLogger;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
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
  @Consumes(MediaType.ALL)
  public String doPost(HttpRequest<?> request)
      throws JsonProcessingException {
    SimpleLogger.variable("lars", 1.3);
    SimpleLogger.variable("request", request.getParameters().asMap());
//        log.info("action {}", action);
//        if (action.equals("bet_request")) {
//            log.info("action game_state {}", game_state);
//            GameState gameState = mapper.readValue(game_state, GameState.class);
//            return String.valueOf(Player.betRequest(gameState));
//        }
//        if (action.equals("showdown")) {
//            Player.showdown(null);
//        }
//        if (action.equals("version")) {
//            return Player.VERSION;
//        }
    return "500";
  }

}
