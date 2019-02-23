package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

//No need for unit tests, just a structure with no functionality.

public class Result {

	private Square location = null;
	private AtackStatus result = AtackStatus.INVALID;
	private Ship ship = null;

	public Result(){}

		public Result(Square location, AtackStatus result, Ship ship){
			this.location = location;
			this.result = result;
			this.ship = ship;
		}

		public AtackStatus getResult() {
			return result;
		}

		public void setResult(AtackStatus result) {
			this.result = result;
			return;
		}

		public Ship getShip() {
			return ship;
		}

		public void setShip(Ship ship) {
			this.ship = ship;
			return;
		}

		public Square getLocation() {
			return location;
		}

		public void setLocation(Square location) {
			this.location = location;
			return;
		}
	}
