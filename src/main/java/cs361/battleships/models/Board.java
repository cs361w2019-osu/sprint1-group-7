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

	public int sunkenShips(){
		return (ships.size() - calcShipsRemaining());
	}

	//Check if any square in a list of squares is out of bounds
	private boolean checkOutOfBounds(List<ShipSquare> squares){
		for(ShipSquare square : squares){
			if (square.getLocation().getRow() <= 0 || square.getLocation().getRow() > 10
			    || square.getLocation().getColumn() < 'A' || square.getLocation().getColumn() > 'J') {
				return true;
			}
		}
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	*/
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		List<ShipSquare> desiredSquares = ship.genSquares(x, y, isVertical);
		if(checkOutOfBounds(desiredSquares)){
			return false;
		}

		for(Ship curShip : ships){
			//Cannot have more than one ship of a given type
			if(curShip.getShipType().equals(ship.getShipType())){
				return false;
			}

			//Cannot collide with any other ships
			for(ShipSquare square : desiredSquares) {
				if(curShip.collides(square.getLocation(), ship.getDepth())){
					return false;
				}
			}
		}

		ship.getOccupiedSquares().addAll(desiredSquares);
		ships.add(ship);
		return true;
	}

	//Checks if a square is a captain's quarters. Used for
	//checking if an attack location has already been attacked
	//and resulted in a miss, but it is not a captains quarters.
	//In this case, it is invalid. If it is a captains quarters,
	//it should sink the ship.
	private boolean checkCaptainsQuarters(Square location){
		for(Ship ship : ships){
			for(ShipSquare square : ship.getOccupiedSquares()){
				if(square.getLocation().equals(location)){
					return ship.checkCaptainsQuarters(location);
				}
			}
		}
		return false;
	}

	//This function sinks / marks as SUNK the remaining squares of a ship
	//when the captains quarters is sunk
	protected void sinkShip(Ship ship, Square captainsQuarters){
		for(ShipSquare square : ship.getOccupiedSquares()){
			if(square.getLocation().equals(captainsQuarters)){
				continue;
			}
			square.setHealth(0);
			boolean exists = false;
			for(Result curResult : attacks){
				if(curResult.getLocation().equals(square.getLocation())){
					exists = true;
					curResult.setResult(AtackStatus.SUNK);
					break;
				}
			}
			if(!exists){
				attacks.add(new Result(square.getLocation(), AtackStatus.SUNK, ship));
			}
		}
	}

	//Return how many squares have health remaining
	protected int calcShipsRemaining(){
		int remaining = 0;
		for(Ship ship : ships){
			boolean alive = false;
			for(ShipSquare square : ship.getOccupiedSquares()) {
				if(square.getHealth() > 0){
					alive = true;
				}
			}

			if(alive){
				remaining++;
			}
		}

		return remaining;
	}

	//Find all ships which occupy a given square
	protected List<Ship> findShipsWithSquare(Square location){
		List<Ship> results = new ArrayList<Ship>();
		for(Ship curShip : ships){
			for(ShipSquare square : curShip.getOccupiedSquares()){
				if(square.getLocation().equals(location)){
					results.add(curShip);
					break;
				}
			}
		}

		return results;
	}

	//Update an existing result on the board to have a new AtackStatus
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
		if (x <= 2 || x >= 9 || y <= 'B' || y >= 'I'){//Return false if out of bounds
			return false;
		}

		//Return false if they haven't sunk a ship yet, so they cannot use sonar:
		if(sunkenShips() == 0){
			return false;
		}

		//Go through all five rows of diamond pattern
		for(int row = x - 2; row <= x + 2; row++) {
			for(int col = y - 2 + Math.abs(x - row); col <= y + 2 - Math.abs(x - row); col++) {//Go through correct column amount (1, 2, 3, 2, 1) for diamond pattern
				Square location = new Square(row, (char) col);

				Ship ship = null;
				for(Ship curShip : ships) {
					for(ShipSquare square : curShip.getOccupiedSquares()) {
						if(square.getLocation().equals(location)) {
							ship = curShip;
							break;//Stop checking ship's squares, we already know it's occupied
						}
					}

					if(ship != null){
						break;//Double break
					}
				}

				boolean marked = false;
				AtackStatus status = null;
				for(Result result : attacks) {
					if(result.getLocation().equals(location)) {
						marked = true;//This location is already marked
						status = result.getResult();//This location is marked specifically as a miss
						break;
					}
				}

				//If the square has not been marked, or it's marked as a miss but it's a captains quarters, mark it as found / empty accordingly
				if(!marked){
					attacks.add(new Result(location, ship == null ? AtackStatus.EMPTY : AtackStatus.FOUND, ship));
				}else if(ship != null && (status == AtackStatus.MISS/* || status == AtackStatus.OUCH*/)){
					// Marked as a miss, but the ship is in fact there, due to a previous bomb (rather than space laser) on a submarine.
					// If we decide to change it to the original implementation, where the OUCHes are displayed like MISSes, it would
					// be helpful to override OUCHes to be FOUNDs. To do this, simply uncomment the condition checking for OUCH statuses.

					updateResult(new Result(location, AtackStatus.FOUND, ship));
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
		int sunkShips = sunkenShips();
		if(sunkShips >= 1){
			return new SpaceLaser();
		} else {
			return new Bomb();
		}
	}

	protected boolean shipSunk(Ship ship) {
		for(ShipSquare square : ship.getOccupiedSquares()){
			if(square.getHealth() > 0)
				return false;
		}
		return true;
	}
}
