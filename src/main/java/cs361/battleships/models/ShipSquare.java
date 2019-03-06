package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ShipSquare {
    private Square location;
    private int health;

    public ShipSquare() {}

    public ShipSquare(Square location, int health) {
        this.location = location;
        this.health = health;
    }

    protected void takeDamage() {
        health--;
    }

    public Square getLocation() {
        return location;
    }

    public int getHealth() {
        return health;
    }

    public void setLocation(Square location) {
        this.location = location;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
