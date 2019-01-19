package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	private List<Ship> ships; 
	private List<Result> attacks;

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
		int size = ship.calcSize();

		if(isVertical && x >= 1 && x + size - 1 <= 10){//Vertical and valid placement
			//Check if any of the squares are occupied by existing ships
			for(Ship curShip : ships){
				if(curShip.getKind().equals(ship.getKind())){
					return false;//No two of same ship type
				}
				for(Square square : curShip.getOccupiedSquares()){
					int row = square.getRow();
					char col = square.getColumn();
					if(col == y && row >= x && row <= x + size - 1)
						return false;//This square is already occupied
				}
			}
			ship.addFeatures(x, y, isVertical);
			ships.add(ship);
			return true;
		}
		else if(!isVertical && y >= 'A' && y + size - 1 <= 'J'){//Horizontal and valid placement
			//Check if any of the squares are occupied by existing ships
			for(Ship curShip : ships){
				if(curShip.getKind().equals(ship.getKind())){
					return false;
				}
				for(Square square : curShip.getOccupiedSquares()){
					int row = square.getRow();
					char col = square.getColumn();
					if(row == x && col >= y && col <= y + size - 1)
						return false;//This square is already occupied
				}
			}
			ship.addFeatures(x, y, isVertical);
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
