package cs361.battleships.models;

public class SpaceLaser implements Weapon {
    @Override
    public boolean attack(Board b, int x, char y){
		Square location = new Square(x, y);
		Ship ship = null;
        Result result;
		//Return invalid if x or y are out of boundaries
		if(x < 1 || x > 10 || y < 'A' || y > 'J'){
		      return false;
        }

		//Find ship we hit, if we hit any. Otherwise, leave ship NULL
		ship = b.findShipWithSquare(location);
     
        
        if(ship != null){
            for(Ship curship: b.getShips()){
                //reduce square health
                ShipSquare square = curship.findSquareWithLocation(location); 
                if(square != null){
                    boolean sunk = b.shipSunk(curship);
                    square.takeDamage();
                    if(sunk) {
                        result = new Result(location, b.calcShipsRemaining() > 0 ? AtackStatus.SUNK : AtackStatus.SURRENDER, curship);
                    } else if(!ship.checkCaptainsQuarters(location)){
                        result = new Result(location, AtackStatus.HIT, curship);//Not captain's quarters, but valid hit, must be a HIT.
                    } else {
                        if(square.getHealth() <= 0){//If captain is dead
                            b.sinkShip(curship, location);//Sink remaining squares / mark as hit for this ship
                            result = new Result(location, b.calcShipsRemaining() == 0 ? AtackStatus.SURRENDER : AtackStatus.SUNK, curship);//Either sink or surrender
                        }else{
                            result = new Result(location, AtackStatus.OUCH, curship);//Still has health, mark as an OUCH
                        }
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
                }
            }
            return true;
        } 
        else {
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