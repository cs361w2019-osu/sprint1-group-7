package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty private Square square;
    @JsonProperty private AtackStatus result;
    @JsonProperty private Ship ship;

    public Result(){
        square = new Square();
        ship = new Ship();
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
        return;
	}

	public Square getLocation() {
		return square;
	}

	public void setLocation(Square square) {
        this.square = square;
        return;
	}
}
