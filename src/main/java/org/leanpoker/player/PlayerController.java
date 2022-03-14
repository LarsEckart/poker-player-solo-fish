package org.leanpoker.player;

import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import java.util.List;
import java.util.Map.Entry;
import org.leanpoker.player.model.GameState;
import org.slf4j.Logger;

@Controller()
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
//    public String doPost(@QueryValue String action, @Nullable @QueryValue String game_state)
    public String doPost(HttpRequest<?> request)

        throws JsonProcessingException {
        for (Entry<String, List<String>> header : request.getHeaders()) {
            log.info("header '{}' : {}", header.getKey(), header.getValue());
        }
        log.info("path {} ", request.getPath());
        return "0";
    }

}
