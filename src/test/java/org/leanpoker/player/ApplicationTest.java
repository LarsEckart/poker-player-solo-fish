package org.leanpoker.player;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class ApplicationTest {

    @Inject
    EmbeddedServer server;

    @Test
    void getRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .GET()
                        .uri(server.getURI())
                        .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(httpResponse.statusCode()).isEqualTo(200);
        assertThat(httpResponse.body()).isEqualTo("Java player is running");
    }

    @Test
    void postRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(""))
                        .uri(URI.create(server.getURI().toString() + "/?action=version"))
                        .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(httpResponse.statusCode()).isEqualTo(200);
        assertThat(httpResponse.body()).isEqualTo("Default Java folding player");
    }

    @Test
    void postRequestParseJsonWithPairs_shouldIncreaseBetBy100() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request =
            HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(server.getURI().toString() + "/?action=bet_request&game_state=" + URLEncoder.encode("""
                                {
                                  "tournament_id":"550d1d68cd7bd10003000003",     
                                  "game_id":"550da1cb2d909006e90004b1",           
                                  "round":0,                                      
                                  "bet_index":0,                                  
                                  "small_blind": 10,                              
                                  "current_buy_in": 320,                          
                                  "pot": 400,                                     
                                  "minimum_raise": 240,                           
                                  "dealer": 1,                                    
                                  "orbits": 7,                                    
                                  "in_action": 1,                                 
                                  "players": [                                    
                                      {                                           
                                          "id": 0,                                
                                          "name": "Albert",                       
                                          "status": "active",                     
                                          "version": "Default random player",     
                                          "stack": 1010,                          
                                          "bet": 320                              
                                      },
                                      {
                                          "id": 1,                                
                                          "name": "Bob",
                                          "status": "active",
                                          "version": "Default random player",
                                          "stack": 1590,
                                          "bet": 80,
                                          "hole_cards": [                         
                                              {
                                                  "rank": "K",                    
                                                  "suit": "hearts"                
                                              },
                                              {
                                                  "rank": "K",
                                                  "suit": "spades"
                                              }
                                          ]
                                      },
                                      {
                                          "id": 2,
                                          "name": "Chuck",
                                          "status": "out",
                                          "version": "Default random player",
                                          "stack": 0,
                                          "bet": 0
                                      }
                                  ],
                                  "community_cards": [                           
                                      {
                                          "rank": "4",
                                          "suit": "spades"
                                      },
                                      {
                                          "rank": "A",
                                          "suit": "hearts"
                                      },
                                      {
                                          "rank": "6",
                                          "suit": "clubs"
                                      }
                                  ]
                                }
                                """, StandardCharsets.UTF_8)))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(httpResponse.body()).isEqualTo("500");
    }
}
