package cs361.battleships.models;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

//No unit tests necessary. Abstract class -- subtypes tested in JUnit tests.

//The following annotations register the sub types associated with
//the ship class so that the Jackson serializer (used by Ninja for POJO/JSON parsing)
//knows to record the type of a ship when it is serialized so that it can later
//be deserialized back into the correct subtype POJO (necessary for polymorphism)
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Minesweeper.class, name = "Minesweeper"),
	@JsonSubTypes.Type(value = Destroyer.class, name = "Destroyer"),
	@JsonSubTypes.Type(value = Battleship.class, name = "Battleship"),
	@JsonSubTypes.Type(value = Submarine.class, name = "Submarine")
})
public abstract class Ship{

	protected List<ShipSquare> occupiedSquares;
	protected String shipType;
	//Should be 0 in minesweeper, 1 in destroyer, and 2 in battleship class. Should be used in function which takes a square as a parameter and determines
	//...if this ship's captains quarters is at that exact location. Returns true if so, false otherwise.
	protected int captainsIdx;
	protected int depth;


	public Ship(){
		occupiedSquares = new ArrayList<ShipSquare>();
	}

	// public abstract Ship clone();
	public abstract Ship opponentCopy();

	public boolean checkCaptainsQuarters(Square location){
		return location.equals(occupiedSquares.get(captainsIdx).getLocation());
	}

	//Remember to override for submarines, as they are oddly shaped
	public List<ShipSquare> genSquares(int row, char col, boolean isV){
		List<ShipSquare> generated = new ArrayList<ShipSquare>();
		int size = calcSize();
		for(int i = 0; i < size; i++){
			generated.add(new ShipSquare(new Square(row, col), i == captainsIdx ? 2 : 1));
			if(isV) {
				row += 1;
			}
			else {
				col += 1;
			}
		}

		return generated;
	}

	public ShipSquare findSquareWithLocation(Square location){
		for(ShipSquare square : occupiedSquares) {
			if(square.getLocation().equals(location))
				return square;
		}

		return null;
	}

	public abstract int calcSize();

	public List<ShipSquare> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<ShipSquare> occupiedSquares){
		this.occupiedSquares = occupiedSquares;
	}

	public String getShipType(){
		return shipType;
	}

	public void setShipType(String shipType){
		this.shipType = shipType;
	}

	public int getCaptainsIdx(){
		return captainsIdx;
	}

	public void setCaptainsIdx(int captainsIdx){
		this.captainsIdx = captainsIdx;
	}

	public boolean canMove(int rowAdd, int colAdd){
		for(ShipSquare square: occupiedSquares){
			if(square.getLocation().getRow() + rowAdd > 10 || square.getLocation().getRow() + rowAdd < 1){return false;}
			if(square.getLocation().getColumn() + colAdd < 'A' || square.getLocation().getColumn() + colAdd > 'J'){return false;}
		}
		return true;
	}

	//Generate squares which the ship desires to occupy
	//Override for submarine, as it can collide differently
	protected boolean collides (Square location, int depth) {
		if (depth != this.depth) {
			return false;
		}

		for(ShipSquare square : occupiedSquares) {
			if(square.getLocation().equals(location)){
				return true;
			}
		}

		return false;
	}

	public int getDepth(){
		return depth;//Most ships by default can only have surface depth
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
