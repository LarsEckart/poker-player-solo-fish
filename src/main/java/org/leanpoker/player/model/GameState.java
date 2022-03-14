package org.leanpoker.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GameState(
    @JsonProperty("tournament_id") String tournamentId
    , @JsonProperty("game_id") String gameId
    , int round
    , @JsonProperty("bet_index") int betIndex
    , @JsonProperty("small_blind") int smallBlind
    , @JsonProperty("current_buy_in") int currentBuyIn
    , int pot
    , @JsonProperty("minimum_raise") int minimumRaise
    , int dealer
    , int orbits
    , @JsonProperty("in_action") int inAction
    , List<PlayerState> players
    , @JsonProperty("community_cards") List<Card> communityCards
) {

  public PlayerState me() {
    return players.get(inAction);
  }

  public int toCall() {
    return this.currentBuyIn - me().bet();
  }

  public int toRaise() {
    return toCall() + minimumRaise;
  }

  public int toRaiseByBlinds(int n) {
    return toRaise() + n * bigBlind();
  }

  private int bigBlind() {
    return this.smallBlind * 2;
  }

  public Map<String, CardCount> suitCounts() {
    Map<String, CardCount> result = new HashMap<>();
    var suits = List.of("spades", "clubs", "hearts", "diamonds");
    for (String suit : suits) {
      long holeCount = me().holeCards().stream().filter(c -> c.suit().equals(suit)).count();
      long communityCount = communityCards.stream().filter(c -> c.suit().equals(suit)).count();
      result.put(suit, new CardCount(communityCount, holeCount, holeCount + communityCount));
    }
    return result;
  }

  public Map<String, CardCount> rankCounts() {
    Map<String, CardCount> result = new HashMap<>();
    var ranks = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
    for (String rank : ranks) {
      long holeCount = me().holeCards().stream().filter(c -> c.rank().equals(rank)).count();
      long communityCount = communityCards.stream().filter(c -> c.rank().equals(rank)).count();
      result.put(rank, new CardCount(communityCount, holeCount, holeCount + communityCount));
    }
    return result;
  }

  public String bettingRound() {
    return Map.of(0, "pre flop", 3, "flop", 4, "turn", 5, "river").get(communityCards.size());
  }

  public List<PlayerState> activePlayersInGame() {
    return this.players().stream().filter(p -> !"out".equals(p.status())).toList();
  }

  public List<PlayerState> activePlayersInHand() {
    return this.players().stream().filter(p -> "active".equals(p.status())).toList();
  }
}
