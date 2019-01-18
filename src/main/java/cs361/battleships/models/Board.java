package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    @JsonProperty private List<Ship> ships; 
    @JsonProperty private List<Result> attacks;

    /*

	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
        ships = new ArrayList<Ship>();
        attacks = new ArrayList<Result>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        int size = 2;
        if (ship.getKind().equals("MINESWEEPER")){size = 2;}
        if (ship.getKind().equals("DESTROYER")){size = 3;}
        if (ship.getKind().equals("BATTLESHIP")){size = 4;}

        if(isVertical == true){
            if(x > size){
                ship.addFeatures(x, y, isVertical, size);
                ships.add(ship);
                return true;
            }
        }
        else if(y <= ('J'- (char)size)){
            ship.addFeatures(x, y, isVertical, size);
            ships.add(ship);
            return true;
        }
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
