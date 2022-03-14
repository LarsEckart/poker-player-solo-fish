package org.leanpoker.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PlayerState(
    int id
    , String name
    , String status
    , String version
    , int stack
    , int bet
    , @JsonProperty("hole_cards") List<Card> holeCards
) {

  public boolean hasPocketPair() {
    return this.holeCards.get(0).rank().equals(this.holeCards.get(1).rank());
  }

  public boolean hasPocketSuited() {
    return this.holeCards.get(0).suit().equals(this.holeCards.get(1).suit());
  }

  public int highestPocketValue() {
    return Math.max(this.holeCards.get(0).value(), this.holeCards.get(1).value());
  }

  private int pocketGap() {
    return Math.abs(this.holeCards.get(0).value() - this.holeCards.get(1).value()) - 1;
  }

  public int score() {
    var score = Math.max(this.holeCards.get(0).score(), this.holeCards.get(1).score());

    if (this.hasPocketPair()) {
      score = Math.max(5, score * 2);
    } else if (this.pocketGap() < 3) {
      score -= this.pocketGap();
    } else if (this.pocketGap() == 3) {
      score -= 4;
    } else {
      score -= 5;
    }

    if (List.of(0, 1).contains(this.pocketGap()) && this.highestPocketValue() < 12) {
      score += 1;
    }

    if (this.hasPocketSuited()) {
      score += 2;
    }

    return (int) Math.round(score);
  }
}
