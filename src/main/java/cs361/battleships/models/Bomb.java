package cs361.battleships.models;

public class Bomb implements Weapon {
    @Override
    public boolean attack(Board b, int x, char y){
		Square location = new Square(x, y);
		Ship ship = null;

		//Return invalid if x or y are out of boundaries
		if(x < 1 || x > 10 || y < 'A' || y > 'J'){
		      return false;
        }

		//To determine between SUNK vs SURRENDER
		int shipsRemaining = b.calcShipsRemaining();

		//Find ship we hit, if we hit any. Otherwise, leave ship NULL
		ship = b.findShipWithSquare(location);

		Result result;
        if((ship != null && !b.shipSunk(ship)) || ship == null){
            if(ship != null){
    			if(!ship.checkCaptainsQuarters(location)){
    				result = new Result(location, AtackStatus.HIT, ship);//Not captain's quarters, but valid hit, must be a HIT.
    			}else{
    				ship.takeCaptainDamage();//Reduce captains quarters health.
    				if(ship.getCaptainsHealth() <= 0){//If captain is dead
    					result = new Result(location, shipsRemaining == 1 ? AtackStatus.SURRENDER : AtackStatus.SUNK, ship);//Either sink or surrender
    					b.sinkShip(ship, location);//Sink remaining squares / mark as hit for this ship
    				}else{
    					result = new Result(location, AtackStatus.OUCH, ship);//Still has health, mark as a MISS
    				}
    			}
    		} else {
    			result = new Result(location, AtackStatus.MISS, ship);//Mark as a MISS
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

		return true;
	}
}
