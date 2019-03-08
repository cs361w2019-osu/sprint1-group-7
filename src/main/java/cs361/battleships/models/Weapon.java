package cs361.battleships.models;

import java.util.List;

public interface Weapon {
	public boolean filterShip(Ship s);

	public default boolean attack(Board b, int x, char y){
		Square location = new Square(x, y);

		//Return invalid if x or y are out of boundaries
		if(x < 1 || x > 10 || y < 'A' || y > 'J'){
			  return false;
        }

		//Find ship we hit, if we hit any. Otherwise, leave ship NULL
		List<Ship> ships = b.findShipsWithSquare(location);

		Result result = null;
        if(ships.size() > 0){
			for(Ship ship : ships){
				//If the ship is filtered by this weapon, as this weapon cannot attack it, skip the ship
				if(filterShip(ship)){
					continue;
				}
            	//reduce square health
	            ShipSquare square = ship.findSquareWithLocation(location);
            	boolean sunk = b.shipSunk(ship);
            	square.takeDamage();
            	if(sunk) {
					int shipsRemaining = b.calcShipsRemaining();
					//Only overwrite it if it's a surrender
					if(shipsRemaining <= 0 && (result == null || result.getResult() != AtackStatus.SURRENDER)){
                		result = new Result(location, AtackStatus.SURRENDER, ship);
					}
					//Only overwrite misses and hits with sunks
					else if(shipsRemaining > 0 && (result == null || (result.getResult() != AtackStatus.SUNK && result.getResult() != AtackStatus.SURRENDER))){
						result = new Result(location, AtackStatus.SUNK, ship);
					}
            	} else if(!ship.checkCaptainsQuarters(location)){
					if(result == null || result.getResult() == AtackStatus.MISS){//only overwrite misses with hits
						result = new Result(location, AtackStatus.HIT, ship);//Not captain's quarters, but valid hit, must be a HIT.
					}
				} else {
					if(square.getHealth() <= 0){//If captain is dead
                    	b.sinkShip(ship, location);//Sink remaining squares / mark as hit for this ship
						int shipsRemaining = b.calcShipsRemaining();
						//Only overwrite it if it's not a surrender already
						if(shipsRemaining <= 0 && (result == null || result.getResult() != AtackStatus.SURRENDER)){
							result = new Result(location, AtackStatus.SURRENDER, ship);
						}
						//Only overwrite hits, misses, and ouches with sunk
						else if(shipsRemaining > 0 && (result == null || (result.getResult() != AtackStatus.SUNK && result.getResult() != AtackStatus.SURRENDER))){
							result = new Result(location, AtackStatus.SUNK, ship);
						}
					}else{
						//Only overwrite hits and misses with ouches
						if(result == null || (result.getResult() != AtackStatus.OUCH && result.getResult() != AtackStatus.SUNK && result.getResult() != AtackStatus.SURRENDER))
						result = new Result(location, AtackStatus.OUCH, ship);//Still has health, mark as an OUCH
					}
				}

			}
		}
		//If there are no ships here, or at least no ships this weapon can hit, mark as a MISS
		if(result == null){
			result = new Result(location, AtackStatus.MISS, null);//Mark as a MISS
		}

        boolean resultExists = false;
		for(Result curResult : b.getAttacks()){
			if(curResult.getLocation().equals(location)){
				resultExists = true;
			}
		}

		//If the attack result already exists (i.e. is FOUND or EMPTY or a captains quarters MISS), update it
		if (resultExists) {
			b.updateResult(result);
		} else {
			b.getAttacks().add(result);//Otherwise, add new result
		}

		return true;
	}
}
