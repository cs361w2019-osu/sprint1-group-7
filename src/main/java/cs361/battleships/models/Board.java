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
        int size = ship.getSize();

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
                    if(row == x && col >= y && col <= y + size - 1){
                        return false;//This square is already occupied
                    }
				}
			}
			ship.addFeatures(x, y, isVertical);
            ships.add(ship);
            
			return true;
		}
        
		return false;
	}


	public Result attack(int x, char y) {
		Square location = new Square(x, y);
		Ship ship = null;

		//Return invalid if x or y are out of boundaries
		if(x < 1 || x > 10 || y < 'A' || y > 'J')
			return new Result(location, AtackStatus.INVALID, ship);

		//Return invalid if they've already hit this square
		boolean resultExists = false;
		for(Result curResult : attacks){
			if(curResult.getLocation().equals(location)){
					resultExists = true;
					if (curResult.getResult() != AtackStatus.FOUND && curResult.getResult() != AtackStatus.EMPTY && curResult.getResult() != AtackStatus.OUCH)
							return new Result(location, AtackStatus.INVALID, ship);
			}
		}

		//Track how many ships are remaining to distinguish between SUNK and SURRENDER
		int shipsRemaining = 0;
		for(Ship curShip : ships){
				boolean thisShipHit = false;
				//Track how many squares this ship has remaining to distinguish HIT vs SUNK
				int squaresRemaining = 0;

				for(Square square : curShip.getOccupiedSquares()){
						boolean squareAlreadyHit = false;
						for(Result result : attacks){
								if(result.getLocation().equals(square) && result.getResult() != AtackStatus.FOUND){
										squareAlreadyHit = true;
										break;
								}
						}

						if(!squareAlreadyHit){
								squaresRemaining++;//Count squares that haven't been hit
						}

						//If a ship is found occupying this location and this spot hasn't already been hit, record it
						if(ship == null && square.equals(location)){
								ship = curShip;
								thisShipHit = true;
						}
                }
                

				if(squaresRemaining > 0)
						shipsRemaining++;//Count ships that haven't been sunk

				//If we hit a ship, determine if it's a HIT or SUNK / SURRENDER
				if(thisShipHit){
                        int bomb = 0;
						if(squaresRemaining > 1){
                                Result result;
                                if(ship.checkCaptainsQuarters(location)){

                                    result = new Result(location, AtackStatus.OUCH, ship);
                                }    
                                else{
								    result = new Result(location, AtackStatus.HIT, ship);
                                }
                                if (resultExists) {
										for (Result curResult : attacks) {
												if (curResult.getLocation().equals(location)) {
                                                    if(curResult.getResult() == AtackStatus.OUCH){
                                                        bomb = 1;
                                                    }
                                                    else{
                                                        curResult.setResult(AtackStatus.HIT);
                                                        curResult.setShip(ship);
                                                    }
                                                    break;
												}
										}
                                } 
                                else {
									attacks.add(result);
                                }
                                if(bomb != 1){
                                    return result;
                                }
						}//Otherwise, continue looping to figure out how many ships remain to determine if it's a SUNK or SURRENDER
				}
		}
                     
        if(ship == null) {
            Result res = new Result(location, AtackStatus.MISS, ship);
            attacks.add(res);
            return res;
        }
		else{
            if(shipsRemaining == 1){
                attacks.add(new Result(location, AtackStatus.SURRENDER, ship));
                return new Result(location, AtackStatus.SURRENDER, ship);
            }
       
        
            int ouchie = 0;
            for(Result result : attacks){ 
                if(result.getLocation().equals(location) && result.getResult() == AtackStatus.OUCH){
                    ouchie = 1;   
                }
            }

            if(ouchie == 1){    
                for(Square square : ship.getOccupiedSquares()){
                    int found = 0;
                    for(Result result: attacks){
                        if(result.getLocation().equals(square)){
                            result.setResult(AtackStatus.SUNK);
                            found = 1;
                        }
                       
                    }
                    if(found == 0){
                        attacks.add(new Result(square, AtackStatus.SUNK, ship));
                    }
                }  
                return new Result(location, AtackStatus.SUNK, ship);   
            }
			//We hit a ship, but the result is not HIT, so it must have only one square remaining, being either a SUNK or SURRENDER
				else{
                    for(Square square : ship.getOccupiedSquares()){
                        int found = 0;
                        for(Result result: attacks){
                            if(result.getLocation().equals(square)){
                                result.setResult(AtackStatus.SUNK);
                                found = 1;
                            }
                            
                        }
                        if(found == 0){
                            attacks.add(new Result(square, AtackStatus.SUNK, ship));
                        }
                    }
                    return new Result(location, AtackStatus.SUNK, ship);  
                }
        } 
        
	}

	public boolean sonar (int x, char y) {
			if (x <= 2 || x >= 9 || y <= 'B' || y >= 'I')
					return false;

			for(int row = x - 2; row <= x + 2; row++) {
					for(int col = y - 2 + Math.abs(x - row); col <= y + 2 - Math.abs(x - row); col++) {
							boolean occupied = false;
							boolean captain = false;
							for(Ship ship : ships) {
									for(Square square : ship.getOccupiedSquares()) {
											if(square.getRow() == row && square.getColumn() == col) {
													occupied = true;
													captain = ship.checkCaptainsQuarters(square);
													break;
											}
									}

									if(occupied)
											break;
							}

							boolean marked = false;
							boolean miss = false;
							for(Result result : attacks) {
									if(result.getLocation().getRow() == row && result.getLocation().getColumn() == col) {
											marked = true;
											miss = result.getResult() == AtackStatus.MISS;
											break;
									}
							}

							//If the square has not been marked, or it's marked as a miss but it's a captains quarters, mark it as found / empty accordingly
							if(!marked || (miss && captain)){
									attacks.add(new Result(new Square(row, (char) col), occupied ? AtackStatus.FOUND : AtackStatus.EMPTY, null));
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
}
