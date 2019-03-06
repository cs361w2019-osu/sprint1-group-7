package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

import java.lang.Math;

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
				if(curShip.getShipType().equals(ship.getShipType())){
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
				if(curShip.getShipType().equals(ship.getShipType())){
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

	//Checks if a square is a captain's quarters. Used for
	//checking if an attack location has already been attacked
	//and resulted in a miss, but it is not a captains quarters.
	//In this case, it is invalid. If it is a captains quarters,
	//it should sink the ship.
	private boolean checkCaptainsQuarters(Square location){
		for(Ship ship : ships){
			for(Square square : ship.getOccupiedSquares()){
				if(square.equals(location)){
					return ship.checkCaptainsQuarters(location);
				}
			}
		}
		return false;
	}

	//This function sinks / marks as SUNK the remaining squares of a ship
	//when the captains quarters is sunk
	protected void sinkShip(Ship ship, Square captainsQuarters){
		for(Square square : ship.getOccupiedSquares()){
			if(square.equals(captainsQuarters)){
				continue;
			}
			boolean exists = false;
			for(Result curResult : attacks){
				if(curResult.getLocation().equals(square)){
					exists = true;
					curResult.setResult(AtackStatus.SUNK);
				}
			}
			if(!exists){
				attacks.add(new Result(square, AtackStatus.SUNK, ship));
			}
		}
	}

	protected int calcShipsRemaining(){
		int remaining = 0;
		for(Ship ship : ships){
			boolean alive = true;
			for(Result result : attacks){
				//A dead ship will have only SUNK and / or SURRENDER squares, so just check the first square
				if(result.getLocation().equals(ship.getOccupiedSquares().get(0)) && (result.getResult() == AtackStatus.SUNK || result.getResult() == AtackStatus.SURRENDER)){
					alive = false;
					break;
				}
			}

			if(alive){
				remaining++;
			}
		}

		return remaining;
	}

	protected Ship findShipWithSquare(Square location){
		Ship ship = null;
		for(Ship curShip : ships){
			for(Square square : curShip.getOccupiedSquares()){
				//If a ship is found occupying this location and this spot hasn't already been hit, record it
				if(ship == null && square.equals(location)){
					ship = curShip;//Record that this ship was hit
					break;
				}
			}
			if(ship != null){
				break;//Stop checking ships, we found the right one already
			}
		}

		return ship;
	}

	protected void updateResult(Result result){
		boolean found = false;
		for(Ship ship : ships){
			for(Result curResult : attacks){
				if(curResult.getLocation().equals(result.getLocation())){
					found = true;//Mark found
					//Update result in board
					curResult.setResult(result.getResult());
					curResult.setShip(result.getShip());
					//No need to update location, it already matches
					break;
				}
			}

			if(found){
				break;//Stop checking ships, we found the one marked
			}
		}
	}

	public boolean sonar (int x, char y) {
		if (x <= 2 || x >= 9 || y <= 'B' || y >= 'I')//Return false if out of bounds
		return false;

		//Return false if they haven't sunk a ship yet, so they cannot use sonar:
		if(calcShipsRemaining() == 3){
			return false;
		}

		//Go through all five rows of diamond pattern
		for(int row = x - 2; row <= x + 2; row++) {
			for(int col = y - 2 + Math.abs(x - row); col <= y + 2 - Math.abs(x - row); col++) {//Go through correct column amount (1, 2, 3, 2, 1) for diamond pattern
				Square location = new Square(row, (char) col);

				Ship ship = null;
				for(Ship curShip : ships) {
					for(Square square : curShip.getOccupiedSquares()) {
						if(square.equals(location)) {
							ship = curShip;
							break;//Stop checking ship's squares, we already know it's occupied
						}
					}

					if(ship != null)
					break;//Double break
				}

				boolean marked = false;
				boolean ouch = false;
				for(Result result : attacks) {
					if(result.getLocation().equals(location)) {
						marked = true;//This location is already marked
						ouch = result.getResult() == AtackStatus.OUCH;//This location is marked specifically as a miss
						break;
					}
				}

				//If the square has not been marked, or it's marked as a miss but it's a captains quarters, mark it as found / empty accordingly
				if(!marked){
					attacks.add(new Result(location, ship == null ? AtackStatus.EMPTY : AtackStatus.FOUND, ship));
				}else if(ouch){
					//Result exists already as an ouch, just update to a FOUND. Only do this if we decide to not display the OUCH classes as red, but rather as blue like a MISS.
					// updateResult(new Result(location, AtackStatus.FOUND, ship));
				}
			}
		}

		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	*/

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

	//Determine which type of weapon to use depending on number of sunk ships
	public Weapon determineWeapon(){
		int sunkShips = ships.size() - calcShipsRemaining();
		// if(sunkShips >= 2){
		// 	return new SpaceLaser();
		// } else {
		return new Bomb();
		//}
	}

	protected boolean shipSunk(Ship ship) {
		for(Result result : attacks) {
			if(result.getLocation().equals(ship.getOccupiedSquares().get(0))) {
				return result.getResult() == AtackStatus.SUNK || result.getResult() == AtackStatus.SURRENDER;
			}
		}
		return false;
	}
}
