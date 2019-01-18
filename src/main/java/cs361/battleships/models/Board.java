package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {
    @JsonProperty private List<Ship> ships = new List<Ship>; 
    @JsonProperty private List<Result> attacks = new List<Result>;

    /*

	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
        Result ack = new Result(x,y);
		return ack;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
        this.ships = ships;
        return;
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
        this.attacks = attacks;
        return;
	}
}
