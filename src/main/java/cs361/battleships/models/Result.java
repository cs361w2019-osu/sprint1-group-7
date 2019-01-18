package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty private Square square = new Square()
    @JsonProperty private AttackStatus result = new AttackStatus();
    @JsonProperty private Ship ship = new Ship();

    public Result(){
    }

    public Result(int x, char y){
        Square sq = new Square(x,y);
        setLocation(sq);
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
	}

	public Square getLocation() {
		return square;
	}

	public void setLocation(Square square) {
        this.square = square;
        return;
	}
}
