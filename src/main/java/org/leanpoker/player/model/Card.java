package org.leanpoker.player.model;

import java.util.List;

public record Card(String rank, String suit) {

  public int value() {
    if (List.of("J", "Q", "K", "A").contains((this.rank))) {
      return List.of("J", "Q", "K", "A").indexOf(this.rank) + 11;
    } else {
      return Integer.parseInt(this.rank);
    }
  }

  public double score() {
    return switch (rank) {
      case "A" -> 10;
      case "K" -> 8;
      case "Q" -> 7;
      case "J" -> 6;
      default -> Integer.parseInt(rank) / 2.0;
    };
  }
}
