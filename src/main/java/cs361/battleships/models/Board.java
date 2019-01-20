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
		System.out.println("BOARD ATTACK");
		Square location = new Square(x, y);
		Ship ship = null;
		
		//Return invalid if x or y are out of boundaries
		if(x < 1 || x > 10 || y < 'A' || y > 'J')
			return new Result(location, AtackStatus.INVALID, ship);
		
		//Return invalid if they've already hit this square
		for(Result curResult : attacks){
			if(curResult.getLocation().equals(location)){
				return new Result(location, AtackStatus.INVALID, ship);
			}
		}
			
		//Track how many ships are remaining to distinguish between SUNK and SURRENDER
		int shipsRemaining = 0;
		for(Ship curShip : ships){
			//Track how many squares this ship has remaining to distinguish HIT vs SUNK
			int squaresRemaining = 0;

			for(Square square : curShip.getOccupiedSquares()){
				boolean squareAlreadyHit = false;
				for(Result result : attacks){
					if(result.getLocation().equals(square)){
						squareAlreadyHit = true;
						break;
					}
				}

				if(!squareAlreadyHit){
					squaresRemaining++;//Count squares that haven't been hit
				}

				//If a ship is found occupying this location and this spot hasn't already been hit, record it
				if(square.equals(location)){
					ship = curShip;
				}
			}

			if(squaresRemaining > 0)
				shipsRemaining++;//Count ships that haven't been sunk

			//If we hit a ship, determine if it's a HIT or SUNK / SURRENDER
			if(ship != null){
				if(squaresRemaining > 1){
					Result result = new Result(location, AtackStatus.HIT, ship);
					attacks.add(result);
					return result;
				}
			}
		}

		if(ship != null){
			//We hit a ship, but the result is not HIT, so it must have only one square remaining, being either a SUNK or SURRENDER
			if(shipsRemaining == 1){
				Result result = new Result(location, AtackStatus.SURRENDER, ship);
				attacks.add(result);
				return result;
			}else{
				Result result = new Result(location, AtackStatus.SUNK, ship);
				attacks.add(result);
				return result;
			}
		}

		//If we make it this far, it simply means they did not select a square with a ship on it, a square they've already hit, or a square out of bounds, so it's a miss
		return new Result(location, AtackStatus.MISS, ship);

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
