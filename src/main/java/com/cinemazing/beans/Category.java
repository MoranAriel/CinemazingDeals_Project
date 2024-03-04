package com.cinemazing.beans;

public enum Category {
  NEW_GEAR(1),
  RENTAL_GEAR(2),
  PRODUCTION(3),
  POST_PRODUCTION(4),
  MOVIE_THEATERS(5),
  STREAMING(6),
  DVD_AND_BLUERAY(7);

  private int id;

  Category(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}